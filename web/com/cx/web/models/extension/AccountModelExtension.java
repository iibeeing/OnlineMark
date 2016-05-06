package com.cx.web.models.extension;

import com.cx.model.models.Account;
import com.cx.web.models.AccountEditModel;
import com.infrastructure.project.common.extension.DESUtils;

public class AccountModelExtension {
	public static Account toModel(AccountEditModel editModel) {
		Account model =new Account();
		model.setName(editModel.getName());
		String encrypt = DESUtils.encryptString(editModel.getPassword());
		model.setPassword(encrypt);
		model.setUsername(editModel.getUsername());
		model.setEmail(editModel.getEmail());
		model.setEnable(editModel.getEnable());
		//版本
		model.setVersion(editModel.getVersion());
		if(editModel.getRole() != null && editModel.getRole().getId() != null){
			model.setRole(editModel.getRole());
		}
		if(editModel.getOrganization() != null && editModel.getOrganization().getId() != null){
			model.setOrganization(editModel.getOrganization());
		}
		return model;
	}

	public static AccountEditModel toEditModel(Account model) {
		AccountEditModel editModel=new AccountEditModel();
		editModel.setName(model.getName());
		editModel.setUsername(model.getUsername());
		editModel.setEmail(model.getEmail());
		editModel.setVersion(model.getVersion());
		editModel.setEnable(model.getEnable());
		//明文
		String decrypt = DESUtils.decryptString(model.getPassword());
		editModel.setPassword(decrypt);
		
		if(model.getRole() != null && model.getRole().getId() != null){
			editModel.setRole(model.getRole());
		}
		if(model.getOrganization() != null && model.getOrganization().getId() != null){
			editModel.setOrganization(model.getOrganization());
		}
		return editModel;
	}
}
