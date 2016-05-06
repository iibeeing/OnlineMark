package com.cx.service.services;

import java.security.NoSuchAlgorithmException;
import java.util.Calendar;
import java.util.List;

import javax.print.attribute.standard.Destination;

import org.hibernate.Criteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.cx.dao.interfaces.IAccountDao;
import com.cx.model.models.Account;
import com.cx.model.models.Organization;
import com.cx.model.models.Role;
import com.cx.service.interfaces.IAccountService;
import com.cx.service.interfaces.IOrganizationService;
import com.cx.service.interfaces.IRoleService;
import com.infrastructure.project.base.service.services.EnableEntityService;
import com.infrastructure.project.common.exception.EntityOperateException;
import com.infrastructure.project.common.exception.ValidatException;
import com.infrastructure.project.common.extension.DESUtils;
import com.infrastructure.project.common.utilities.PageList;
import com.infrastructure.project.common.utilities.PageListUtil;
import com.infrastructure.project.common.utilities.StringUtil;

@Service("AccountService")
public class AccountService extends EnableEntityService<Integer, Account, IAccountDao> implements IAccountService {
	
	/*@Autowired
    @Qualifier("AuthorityService")
	protected IAuthorityService authorityService;*/
	
	@Autowired
    @Qualifier("RoleService")
	protected IRoleService roleService;
	
	@Autowired
    @Qualifier("OrganizationService")
	protected IOrganizationService organizationService;

	@Autowired
	public AccountService(@Qualifier("AccountDao")IAccountDao accountDao){	
		super(accountDao);
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public PageList<Account> listPage(String name, String username, int pageNo, int pageSize) {		
		Criteria countCriteria = entityDao.getCriteria();	
		Criteria listCriteria = entityDao.getCriteria();
		
		if(name!=null && !name.isEmpty()){
			countCriteria.add(Restrictions.eq("name", name)); 
    		listCriteria.add(Restrictions.eq("name", name)); 
		}
		if(username!=null && !username.isEmpty()){
			countCriteria.add(Restrictions.eq("username", username)); 
    		listCriteria.add(Restrictions.eq("username", username)); 
		}

        listCriteria.setFirstResult((pageNo-1)*pageSize);  
        listCriteria.setMaxResults(pageSize);
        List<Account> items = listCriteria.list();
        countCriteria.setProjection(Projections.rowCount());
        Integer count=Integer.parseInt(countCriteria.uniqueResult().toString());
        
        return PageListUtil.getPageList(count, pageNo, items, pageSize);
    }
	
	@Override
	public boolean accountExist(String username){
		Criteria criteria = entityDao.getCriteria();
		criteria.add(Restrictions.eq("username", username));
    	criteria.setProjection(Projections.rowCount());
    	Integer count = Integer.parseInt(criteria.uniqueResult().toString());
    	if(count!=null && count>0)
    		return true;
    	else
    		return false;
	}
	
	@Override
	public void saveRegister(Account account) throws EntityOperateException, ValidatException, NoSuchAlgorithmException{
		//account.setPassword(StringHelper.md5(account.getUsername()+account.getPassword()));
		account.setPassword(DESUtils.encryptString(account.getPassword()));
		account.setRegisterTime(Calendar.getInstance());
		super.save(account);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Account login(String username, String password) throws NoSuchAlgorithmException{
		Criteria criteria = entityDao.getCriteria();
		criteria.add(Restrictions.eq("username", username));
		String encrypt = DESUtils.encryptString(password);
		//字符串做比较时，要先去除空格、换行等
		//encrypt = StringUtil.replaceBlank(encrypt);
		criteria.add(Restrictions.eq("password", encrypt));	
		List<Account> accounts=criteria.list();
		Account ret=null;
		if(accounts!=null && !accounts.isEmpty())
		{
			ret=accounts.get(0);
		}
		return ret;
	}
	
	@Override
	public void updateBind(Integer id, Integer roleId, Integer organizationId) throws ValidatException, EntityOperateException{
		Account dbAccount=super.get(id);
		if(roleId!=null && roleId>0){
			Role dbRole=roleService.get(roleId);
			dbAccount.setRole(dbRole);
		}
		else
			dbAccount.setRole(null);
		if(organizationId!=null && organizationId>0){
			Organization dbOrganization=organizationService.get(organizationId);
			dbAccount.setOrganization(dbOrganization);
		}
		else
			dbAccount.setOrganization(null);
		super.update(dbAccount);
	}

	@Override
	public void updateAccount(Account model) throws ValidatException,EntityOperateException, NoSuchAlgorithmException {
		model.setRegisterTime(Calendar.getInstance());
		super.update(model);
	}
}
