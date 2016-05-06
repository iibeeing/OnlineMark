package com.cx.model.models;

import java.util.Calendar;

import com.infrastructure.project.base.model.interfaces.ICUDEable;
import com.infrastructure.project.base.model.models.EnableEntity;

/**
* @ClassName: Subject
* @Description: TODO(科目)
* @author BEE 
* @date 2015-11-23 下午5:11:43
 */
public class Subject extends EnableEntity<Integer> implements ICUDEable {

	private String name;

//	private String code;
	
	private Project project;
	
	private Calendar createTime;

	private String flag;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

//	public String getCode() {
//		return code;
//	}
//
//	public void setCode(String code) {
//		this.code = code;
//	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public Calendar getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Calendar createTime) {
		this.createTime = createTime;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

}
