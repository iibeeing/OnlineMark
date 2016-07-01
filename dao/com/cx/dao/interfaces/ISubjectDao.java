package com.cx.dao.interfaces;

import java.util.List;

import com.cx.model.models.Subject;
import com.cx.utils.ParamReference;
import com.cx.web.models.SubjectSearchModel;
import com.infrastructure.project.base.dao.interfaces.IEnableEntityDao;

public interface ISubjectDao  extends IEnableEntityDao<Integer, Subject> {
	public List<Subject> listNoPage(SubjectSearchModel searchModel, int pageNo, int pageSize, ParamReference count);
}
