package com.cx.service.interfaces;

import java.security.NoSuchAlgorithmException;

import com.cx.dao.interfaces.IPaperDao;
import com.cx.model.models.Paper;
import com.cx.web.models.PaperSearchModel;
import com.infrastructure.project.base.service.interfaces.IEnableEntityService;
import com.infrastructure.project.common.exception.EntityOperateException;
import com.infrastructure.project.common.exception.ValidatException;
import com.infrastructure.project.common.utilities.PageList;

public interface IPaperService extends IEnableEntityService<Integer, Paper, IPaperDao>{
	public PageList<Paper> listPage(String name, Boolean enable, int pageNo, int pageSize);
	public PageList<Paper> listPage(PaperSearchModel model,int pageNo,int pageSize) throws Exception;
	public boolean exist(String name);
	public void saveObject(Paper object) throws NoSuchAlgorithmException, EntityOperateException, ValidatException;
}
