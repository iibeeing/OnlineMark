package com.cx.service.services;

import java.security.NoSuchAlgorithmException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.cx.dao.interfaces.ISubjectDao;
import com.cx.model.models.Project;
import com.cx.model.models.Subject;
import com.cx.service.interfaces.IProjectService;
import com.cx.service.interfaces.ISubjectService;
import com.cx.utils.ParamReference;
import com.cx.web.models.SubjectSearchModel;
import com.infrastructure.project.base.service.services.EnableEntityService;
import com.infrastructure.project.common.exception.EntityOperateException;
import com.infrastructure.project.common.exception.ValidatException;
import com.infrastructure.project.common.utilities.PageList;
import com.infrastructure.project.common.utilities.PageListUtil;

@Service("SubjectService")
public class SubjectService extends EnableEntityService<Integer, Subject, ISubjectDao> implements ISubjectService {
	@Autowired
    @Qualifier("ProjectService")
	protected IProjectService projectService;
	
	@Autowired
    @Qualifier("SubjectDao")
	protected ISubjectDao subjectDao;
	
	@Autowired
	public SubjectService(@Qualifier("SubjectDao") ISubjectDao objectDao) {
		super(objectDao);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public PageList<Subject> listPage(String name, Boolean enable, int pageNo,
			int pageSize) {
		Criteria countCriteria = entityDao.getCriteria();
		Criteria listCriteria = entityDao.getCriteria();

		if (name != null && !name.isEmpty()) {
			countCriteria.add(Restrictions.eq("name", name));
			listCriteria.add(Restrictions.eq("name", name));
		}
		if (enable != null) {
			countCriteria.add(Restrictions.eq("enable", enable));
			listCriteria.add(Restrictions.eq("enable", enable));
		}
		//获取可用的Project列表
		List<Project> ableProjectList = projectService.listEnable();
		listCriteria.add(Restrictions.in("project", ableProjectList));
		listCriteria.setFirstResult((pageNo - 1) * pageSize);
		listCriteria.setMaxResults(pageSize);
		List<Subject> items = listCriteria.list();
		countCriteria.setProjection(Projections.rowCount());
		Integer count = Integer.parseInt(countCriteria.uniqueResult().toString());

		return PageListUtil.getPageList(count, pageNo, items, pageSize);
	}

	@Override
	public PageList<Subject> listNoPage(SubjectSearchModel searchModel, int pageNo, int pageSize) {
		// TODO Auto-generated method stub
		ParamReference count = new ParamReference();
		List<Subject> items = subjectDao.listNoPage(searchModel, pageNo, pageSize ,count);
		return PageListUtil.getPageList(count.getIntegerValue(), pageNo, items, pageSize);
	}
	
	@Override
	public boolean exist(String name) {
		return false;
	}

	@Override
	public void saveObject(Subject subject) throws NoSuchAlgorithmException,
			EntityOperateException, ValidatException {
		subject.setCreateTime(Calendar.getInstance());
		super.save(subject);
	}

	@Override
	public Map<Integer, String> getTotallyEnableSelectSource() {
		List<Subject> list = this.listEnable();
		Map<Integer, String> map = new HashMap<Integer, String>();
		for(Subject subject : list){
			if(subject.getProject().getEnable()){
				map.put(subject.getId(), subject.getProject().getName() + " : " + subject.getName());
			}
		}
		return map;
	}
}
