package com.cx.web.models.extension;

import com.cx.model.models.Role;
import com.cx.service.models.RoleSearch;
import com.cx.web.models.RoleEditModel;
import com.cx.web.models.RoleSearchModel;

public class RoleModelExtension {
	public static RoleSearch toRoleSearch(RoleSearchModel searchModel){
		RoleSearch ret=new RoleSearch();
		ret.setName(searchModel.getName());
		return ret;
	}
	
	public static Role toModel(RoleEditModel editModel){
		Role role=new Role();
		role.setId(editModel.getId());
		role.setName(editModel.getName());
		role.setEnable(editModel.getEnable());
		//版本
		role.setVersion(editModel.getVersion());
		return role;
	}

	public static RoleEditModel toEditModel(Role model) {
		RoleEditModel editModel=new RoleEditModel();
		editModel.setName(model.getName());
		editModel.setVersion(model.getVersion());
		editModel.setEnable(model.getEnable());
		return editModel;
	}
}
