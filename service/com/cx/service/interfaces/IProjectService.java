package com.cx.service.interfaces;
import java.security.NoSuchAlgorithmException;

import com.cx.dao.interfaces.IProjectDao;
import com.cx.model.models.Project;
import com.infrastructure.project.base.service.interfaces.IEnableEntityService;
import com.infrastructure.project.common.exception.EntityOperateException;
import com.infrastructure.project.common.exception.ValidatException;
import com.infrastructure.project.common.utilities.PageList;

public interface IProjectService extends IEnableEntityService<Integer, Project, IProjectDao> {
//public interface IProjectService extends IEnableEntityService<Integer, Project, IProjectDao> {
	
	public PageList<Project> listPage(String name, Boolean enable, int pageNo, int pageSize);
	public boolean accountExist(String name);
	public void saveProject(Project project) throws NoSuchAlgorithmException, EntityOperateException, ValidatException;
}
