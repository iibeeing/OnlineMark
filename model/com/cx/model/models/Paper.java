package com.cx.model.models;

import java.util.Calendar;

import com.infrastructure.project.base.model.interfaces.ICUDEable;
import com.infrastructure.project.base.model.models.EnableEntity;

public class Paper extends EnableEntity<Integer> implements ICUDEable {
	
	private String name;

	private Calendar uploadTime;

	private Subject subject;
	
	private String flag;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Calendar getUploadTime() {
		return uploadTime;
	}

	public void setUploadTime(Calendar uploadTime) {
		this.uploadTime = uploadTime;
	}

	public Subject getSubject() {
		return subject;
	}

	public void setSubject(Subject subject) {
		this.subject = subject;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}
	
}
