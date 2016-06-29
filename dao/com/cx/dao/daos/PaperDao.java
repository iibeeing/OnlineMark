package com.cx.dao.daos;

import org.springframework.stereotype.Repository;

import com.cx.dao.interfaces.IPaperDao;
import com.cx.model.models.Paper;
import com.infrastructure.project.base.dao.daos.EnableEntityDao;
@Repository("PaperDao")
public class PaperDao extends EnableEntityDao<Integer, Paper> implements IPaperDao {

}
