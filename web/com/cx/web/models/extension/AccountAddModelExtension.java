package com.cx.web.models.extension;

import com.cx.model.models.Account;
import com.cx.web.models.AccountAddModel;
//添加模型扩展
public class AccountAddModelExtension {

	//添加模型
	public static Account toAccount(AccountAddModel addModel) {
		Account ret=new Account();
		ret.setName(addModel.getName());
		ret.setEmail(addModel.getEmail());
		ret.setUsername(addModel.getUsername());
		ret.setPassword(addModel.getPassword());
		return ret;
	}
}
