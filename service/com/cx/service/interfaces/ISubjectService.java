package com.cx.service.interfaces;

import java.security.NoSuchAlgorithmException;

import com.cx.dao.interfaces.ISubjectDao;
import com.cx.model.models.Subject;
import com.infrastructure.project.base.service.interfaces.IEnableEntityService;
import com.infrastructure.project.common.exception.EntityOperateException;
import com.infrastructure.project.common.exception.ValidatException;
import com.infrastructure.project.common.utilities.PageList;

/**
* @ClassName: ISubjectService
* @Description: 科目Service接口
* @author BEE 
* @date 2016-1-12 上午10:33:40
 */
public interface ISubjectService extends IEnableEntityService<Integer, Subject, ISubjectDao> {
	public PageList<Subject> listPage(String name, Boolean enable, int pageNo, int pageSize);
	public boolean exist(String name);
	public void saveObject(Subject subject) throws NoSuchAlgorithmException, EntityOperateException, ValidatException;
}