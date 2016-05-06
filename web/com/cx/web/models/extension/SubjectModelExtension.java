package com.cx.web.models.extension;

import java.util.Calendar;

import com.cx.model.models.Subject;
import com.cx.web.models.SubjectAbleModel;
import com.cx.web.models.SubjectEditModel;


public class SubjectModelExtension {
	public static Subject toObject(SubjectEditModel editModel) {
		Subject object =new Subject();
		object.setName(editModel.getName());
		object.setEnable(false);
		object.setFlag("0");
		object.setCreateTime(Calendar.getInstance());
		return object;
	}

	public static SubjectEditModel toObjectEditModel(Subject model) {
		SubjectEditModel ret=new SubjectEditModel();
		ret.setId(model.getId());
		ret.setName(model.getName());
		ret.setProjectId(model.getProject().getId());
		return ret;
	}

	public static Subject toObject(SubjectAbleModel model) {
		Subject ret=new Subject();
		//ret.setId(model.getId());
		ret.setName(model.getName());
		ret.setCreateTime(model.getCreateTime());
		ret.setFlag(model.getFlag());
		ret.setProject(model.getProject());
		return ret;
	}
}
