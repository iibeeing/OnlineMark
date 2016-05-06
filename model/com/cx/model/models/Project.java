package com.cx.model.models;

import java.util.Calendar;

import com.infrastructure.project.base.model.interfaces.ICUDEable;
import com.infrastructure.project.base.model.models.EnableEntity;

/**
* @ClassName: Project
* @Description: 考试
* @author BEE 
* @date 2015-12-22 下午3:24:30
 */
public class Project extends EnableEntity<Integer> implements ICUDEable {

	private String name;
	
	private Calendar endTime;
	
	private String flag;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Calendar getEndTime() {
		return endTime;
	}

	public void setEndTime(Calendar endTime) {
		this.endTime = endTime;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}
	
	
	
}
