package com.cx.web.models.extension;

import com.cx.model.models.Account;
import com.cx.web.models.AccountAuthorizeModel;

public class AccountAuthorizeModelExtension {
	
	public static AccountAuthorizeModel toAccountBindModel(Account account){
		AccountAuthorizeModel ret=new AccountAuthorizeModel();
		ret.setName(account.getName());
		ret.setUsername(account.getUsername());
		if(account.getRole()!=null)
			ret.setRoleId(account.getRole().getId());
		if(account.getOrganization()!=null)
			ret.setOrganizationId(account.getOrganization().getId());
		
		return ret;
	}
	
}
