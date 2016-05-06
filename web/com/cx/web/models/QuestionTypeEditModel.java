package com.cx.web.models;

import org.hibernate.validator.constraints.NotEmpty;

public class QuestionTypeEditModel {
	private Integer id;
	@NotEmpty(message="{name.not.empty}")
	private String name;
	
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
	
	
}
