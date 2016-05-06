package com.cx.web.models;

import java.util.Calendar;

/**
* @ClassName: ProjectAbleModel
* @Description: 考试管理（可用模型）
* @author BEE 
* @date 2016-1-12 上午10:11:15
 */
public class ProjectAbleModel {
	private Integer id;
	private String name;	
	private Calendar endTime;
	private boolean enable;
	private String flag;
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

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

	public boolean isEnable() {
		return enable;
	}

	public void setEnable(boolean enable) {
		this.enable = enable;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}
	
}
