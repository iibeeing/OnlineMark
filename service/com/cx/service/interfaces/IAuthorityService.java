package com.cx.service.interfaces;

import com.cx.dao.interfaces.IAuthorityDao;
import com.cx.model.models.Authority;
import com.cx.service.models.AuthoritySearch;
import com.infrastructure.project.base.service.interfaces.IChainEntityService;
import com.infrastructure.project.common.utilities.PageList;

public interface IAuthorityService extends IChainEntityService<Integer, Authority, IAuthorityDao> {

	public PageList<Authority> listPage(AuthoritySearch search, int pageNo, int pageSize);
	
}