package com.cx.dao.daos;

import org.springframework.stereotype.Repository;

import com.cx.dao.interfaces.IQuestionTypeDao;
import com.cx.model.models.QuestionType;
import com.infrastructure.project.base.dao.daos.EnableEntityDao;
@Repository("QuestionTypeDao")
public class QuestionTypeDao extends EnableEntityDao<Integer, QuestionType>
		implements IQuestionTypeDao {

}
