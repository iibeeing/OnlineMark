package com.cx.dao.daos;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import com.cx.dao.interfaces.ISubjectDao;
import com.cx.model.models.Subject;
import com.cx.utils.ParamReference;
import com.cx.web.models.SubjectSearchModel;
import com.infrastructure.project.base.dao.daos.EnableEntityDao;

@Repository("SubjectDao")
public class SubjectDao extends EnableEntityDao<Integer, Subject> implements ISubjectDao {
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Subject> listNoPage(SubjectSearchModel searchModel, int pageNo, int pageSize, ParamReference count) {
		Session session = getSession();
		String hql = "From Subject s where s.project.enable=:projectenable";
		if(searchModel != null && searchModel.getName() != null && !"".equals(searchModel.getName())){
			hql += " and s.name=:name";
		}
		if(searchModel != null && searchModel.getEnable() != null){
			hql += " and s.enable=:enable";
		}
		Query query = session.createQuery(hql);
		query.setParameter("projectenable", true);
		if(searchModel != null && searchModel.getName() != null && !"".equals(searchModel.getName())){
			query.setParameter("name", searchModel.getName());
		}
		if(searchModel != null && searchModel.getEnable() != null){
			query.setParameter("enable", searchModel.getEnable());
		}
		count.setIntegerValue(query.list().size());
		query.setFirstResult((pageNo - 1) * pageSize);
		query.setMaxResults(pageSize);
		return query.list();
	}
}
