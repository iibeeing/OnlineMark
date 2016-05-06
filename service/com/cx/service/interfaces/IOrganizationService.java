package com.cx.service.interfaces;

import com.cx.dao.interfaces.IOrganizationDao;
import com.cx.model.models.Organization;
import com.infrastructure.project.base.service.interfaces.IChainEntityService;
import com.infrastructure.project.common.utilities.PageList;

public interface IOrganizationService extends IChainEntityService<Integer, Organization, IOrganizationDao> {

	public PageList<Organization> listPage(String name, int pageNo, int pageSize);
	
}