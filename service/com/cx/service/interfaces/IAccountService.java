package com.cx.service.interfaces;

import java.security.NoSuchAlgorithmException;

import com.cx.dao.interfaces.IAccountDao;
import com.cx.model.models.Account;
import com.infrastructure.project.base.service.interfaces.IEnableEntityService;
//import com.cx.service.models.AccountSearch;
import com.infrastructure.project.common.exception.EntityOperateException;
import com.infrastructure.project.common.exception.ValidatException;
import com.infrastructure.project.common.extension.StringHelper;
import com.infrastructure.project.common.utilities.PageList;

public interface IAccountService extends IEnableEntityService<Integer, Account, IAccountDao> {

	/*public Page<Account> listPage(AccountSearch search, int pageNo, int pageSize);
	public void saveAuthorize(Integer AccountId, Integer[] authorityIds) throws ValidatException, EntityOperateException;*/
	public PageList<Account> listPage(String name, String username, int pageNo, int pageSize);
	public boolean accountExist(String username);
	public Account login(String username, String password) throws NoSuchAlgorithmException;
	public void saveRegister(Account account) throws NoSuchAlgorithmException, EntityOperateException, ValidatException;
	public void updateBind(Integer id, Integer roleId, Integer organizationId) throws ValidatException, EntityOperateException;
	/**
	* @Title: updateAccount
	* @Description: 更新账户信息
	* @param @param account
	* @param @throws ValidatException
	* @param @throws EntityOperateException    设定文件
	* @return void    返回类型
	* @throws
	 */
	public void updateAccount(Account account) throws ValidatException,EntityOperateException,NoSuchAlgorithmException;
}