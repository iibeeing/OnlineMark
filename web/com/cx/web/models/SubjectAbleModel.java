package com.cx.web.models;

import java.util.Calendar;

import com.cx.model.models.Project;

/**
* @ClassName: SubjectAbleModel
* @Description: 科目管理（可用模型）
* @author BEE 
* @date 2016-1-12 上午10:10:36
 */
public class SubjectAbleModel {
	private Integer id;
	private String name;	
	private boolean enable;
	private String code;
	private Project project;
	private Calendar createTime;
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
	public boolean isEnable() {
		return enable;
	}
	public void setEnable(boolean enable) {
		this.enable = enable;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
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
