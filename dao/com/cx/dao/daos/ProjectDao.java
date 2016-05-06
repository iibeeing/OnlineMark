package com.cx.dao.daos;
import org.springframework.stereotype.Repository;

import com.cx.dao.interfaces.IProjectDao;
import com.cx.model.models.Project;
import com.infrastructure.project.base.dao.daos.EnableEntityDao;

@Repository("ProjectDao")
public class ProjectDao extends EnableEntityDao<Integer, Project> implements IProjectDao {

}
