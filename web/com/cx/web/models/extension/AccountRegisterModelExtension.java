package com.cx.web.models.extension;

import com.cx.model.models.Account;
import com.cx.web.models.AccountRegisterModel;

public class AccountRegisterModelExtension {
	
	//注册模型
	public static Account toAccount(AccountRegisterModel registerModel){
		Account ret=new Account();
		ret.setName(registerModel.getName());
		ret.setEmail(registerModel.getEmail());
		ret.setUsername(registerModel.getUsername());
		ret.setPassword(registerModel.getPassword());
		return ret;
	}

}
