package com.cx.service.interfaces;

import java.util.List;

import com.cx.dao.interfaces.IRoleDao;
import com.cx.model.models.Role;
import com.cx.service.models.RoleSearch;
import com.infrastructure.project.base.service.interfaces.IEnableEntityService;
import com.infrastructure.project.common.exception.EntityOperateException;
import com.infrastructure.project.common.exception.ValidatException;
import com.infrastructure.project.common.utilities.PageList;

public interface IRoleService extends IEnableEntityService<Integer, Role, IRoleDao> {

	public PageList<Role> listPage(RoleSearch search, int pageNo, int pageSize);
	public void saveAuthorize(Integer roleId, Integer[] authorityIds) throws ValidatException, EntityOperateException;
	public List<Role> getList();
	public Boolean updateName(Role role)throws Exception;

}