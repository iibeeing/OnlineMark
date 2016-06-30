package com.cx.dao.interfaces;

import java.util.List;

import com.cx.model.models.Subject;
import com.cx.utils.ParamInteger;
import com.infrastructure.project.base.dao.interfaces.IEnableEntityDao;
import com.infrastructure.project.common.utilities.PageList;

public interface ISubjectDao  extends IEnableEntityDao<Integer, Subject> {
	public List<Subject> listNoPage(Subject subject, int pageNo, int pageSize, ParamInteger count);
}
