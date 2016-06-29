package com.cx.service.interfaces;

import java.security.NoSuchAlgorithmException;

import com.cx.model.models.Paper;
import com.infrastructure.project.common.exception.EntityOperateException;
import com.infrastructure.project.common.exception.ValidatException;
import com.infrastructure.project.common.utilities.PageList;

public interface IPaperService {
	public PageList<Paper> listPage(String name, Boolean enable, int pageNo, int pageSize);
	public boolean exist(String name);
	public void saveObject(Paper object) throws NoSuchAlgorithmException, EntityOperateException, ValidatException;
}
