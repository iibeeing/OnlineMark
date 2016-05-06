package com.cx.web.models.extension;

import java.util.Calendar;

import com.cx.model.models.Project;
import com.cx.web.models.ProjectAbleModel;
import com.cx.web.models.ProjectEditModel;

public class ProjectModelExtension {

	public static Project toProject(ProjectEditModel editModel) {
		Project ret =new Project();
		//ret.setId(editModel.getId());
		ret.setName(editModel.getName());
		ret.setEnable(false);
		ret.setFlag("0");
		ret.setEndTime(Calendar.getInstance());
		return ret;
	}

	public static ProjectEditModel toProjectEditModel(Project model) {
		ProjectEditModel ret=new ProjectEditModel();
		ret.setId(model.getId());
		ret.setName(model.getName());
		
		return ret;
	}

	public static Project toProject(ProjectAbleModel model) {
		Project ret=new Project();
		//ret.setId(model.getId());
		ret.setName(model.getName());
		ret.setEndTime(model.getEndTime());
		ret.setFlag(model.getFlag());
		return ret;
	}

}
