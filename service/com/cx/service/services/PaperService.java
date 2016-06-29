package com.cx.service.services;

import java.security.NoSuchAlgorithmException;
import java.util.Calendar;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.cx.dao.interfaces.IPaperDao;
import com.cx.model.models.Paper;
import com.cx.service.interfaces.IPaperService;
import com.infrastructure.project.base.service.services.EnableEntityService;
import com.infrastructure.project.common.exception.EntityOperateException;
import com.infrastructure.project.common.exception.ValidatException;
import com.infrastructure.project.common.utilities.PageList;
import com.infrastructure.project.common.utilities.PageListUtil;

@Service("PaperService")
public class PaperService extends EnableEntityService<Integer, Paper, IPaperDao> implements IPaperService {
	@Autowired
	public PaperService(@Qualifier("PaperDao")IPaperDao dao) {
		super(dao);
	}

	@SuppressWarnings("unchecked")
	@Override
	public PageList<Paper> listPage(String name, Boolean enable, int pageNo,
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

		listCriteria.setFirstResult((pageNo - 1) * pageSize);
		listCriteria.setMaxResults(pageSize);
		List<Paper> items = listCriteria.list();
		countCriteria.setProjection(Projections.rowCount());
		Integer count = Integer.parseInt(countCriteria.uniqueResult().toString());

		return PageListUtil.getPageList(count, pageNo, items, pageSize);
	}

	@Override
	public boolean exist(String name) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void saveObject(Paper object) throws NoSuchAlgorithmException,
			EntityOperateException, ValidatException {
		object.setUploadTime(Calendar.getInstance());
		super.save(object);
		
	}

}
