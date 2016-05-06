package com.cx.service.interfaces;

import java.security.NoSuchAlgorithmException;

import com.cx.dao.interfaces.IQuestionTypeDao;
import com.cx.model.models.QuestionType;
import com.infrastructure.project.base.service.interfaces.IEnableEntityService;
import com.infrastructure.project.common.exception.EntityOperateException;
import com.infrastructure.project.common.exception.ValidatException;
import com.infrastructure.project.common.utilities.PageList;

public interface IQuestionTypeService  extends IEnableEntityService<Integer, QuestionType, IQuestionTypeDao> {
	public PageList<QuestionType> listPage(String name, Boolean enable, int pageNo, int pageSize);
	public boolean accountExist(String name);
	public void saveProject(QuestionType questionType) throws NoSuchAlgorithmException, EntityOperateException, ValidatException;
}
