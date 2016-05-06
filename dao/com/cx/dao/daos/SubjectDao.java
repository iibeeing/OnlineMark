package com.cx.dao.daos;

import org.springframework.stereotype.Repository;
import com.cx.dao.interfaces.ISubjectDao;
import com.cx.model.models.Subject;
import com.infrastructure.project.base.dao.daos.EnableEntityDao;

@Repository("SubjectDao")
public class SubjectDao extends EnableEntityDao<Integer, Subject> implements ISubjectDao {

}
