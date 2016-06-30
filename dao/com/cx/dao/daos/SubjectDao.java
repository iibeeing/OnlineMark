package com.cx.dao.daos;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import com.cx.dao.interfaces.ISubjectDao;
import com.cx.model.models.Subject;
import com.cx.utils.ParamInteger;
import com.infrastructure.project.base.dao.daos.EnableEntityDao;

@Repository("SubjectDao")
public class SubjectDao extends EnableEntityDao<Integer, Subject> implements ISubjectDao {
	
	@Override
	public List<Subject> listNoPage(Subject subject, int pageNo, int pageSize, ParamInteger count) {
		Session session = getSession();
		String hql = "From Subject s where s.project.enable=?";
		Query query = session.createQuery(hql);
		query.setBoolean(0, true);
		count.setValue(query.list().size());
		System.out.println("count is  " + count);
		query.setFirstResult((pageNo - 1) * pageSize);
		query.setMaxResults(pageSize);
		return query.list();
	}
}
