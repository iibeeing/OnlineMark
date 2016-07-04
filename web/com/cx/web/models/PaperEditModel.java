package com.cx.web.models;

import org.hibernate.validator.constraints.NotEmpty;

public class PaperEditModel {
	private Integer id;
	@NotEmpty(message="{name.not.empty}")
	private String name;	
	private Integer subjectId;
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
	public Integer getSubjectId() {
		return subjectId;
	}
	public void setSubjectId(Integer subjectId) {
		this.subjectId = subjectId;
	}
	
	
}
