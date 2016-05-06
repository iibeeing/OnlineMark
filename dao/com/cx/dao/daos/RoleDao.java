package com.cx.dao.daos;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.cx.dao.interfaces.IRoleDao;
import com.cx.model.models.Role;
import com.infrastructure.project.base.dao.daos.EnableEntityDao;

@Repository("RoleDao")
public class RoleDao extends EnableEntityDao<Integer, Role> implements IRoleDao {

	@Override
	public List<Role> getRoleList() {
		// TODO Auto-generated method stub
		Session session = this.sessionFactory.getCurrentSession();
		List<Role> list =  null;
		if(session != null){
			Query query = session.createQuery(" from Role where enable=0");
			list = query.list();
		}
		if(list != null && list.size()>0){
			for(int i=0;i<list.size();i++){
				Role role = list.get(i);
				System.out.println("当前在 " + i + "行，数据是 " + role.getId() + ", " + role.getName() + ", " + role.getAuthorities());
			}
		}
		return list;
	}

	@Override
	public Boolean updateName(Role role) throws Exception {
		// TODO Auto-generated method stub
		Boolean result = false;
		if(role != null && role.getName() != null && role.getId() != null){
			Session session = this.sessionFactory.getCurrentSession();
			Query query = session.createQuery("update Role t set t.name=?, t.version=? where t.id=?");
			query.setParameter(0, role.getName());
			int version = role.getVersion() + 1;
			query.setParameter(1, version);
			query.setParameter(2, role.getId());
			int row = query.executeUpdate();
			if(row == 1){
				result =  true;
			}
		}
		return result;
	}


}
