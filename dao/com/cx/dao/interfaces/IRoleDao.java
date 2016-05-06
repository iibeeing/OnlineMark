package com.cx.dao.interfaces;

import java.util.List;

import com.cx.model.models.Role;
import com.infrastructure.project.base.dao.interfaces.IEnableEntityDao;

public interface IRoleDao extends IEnableEntityDao<Integer, Role> {
	List<Role> getRoleList();
	
	Boolean updateName(Role role)throws Exception;
}
