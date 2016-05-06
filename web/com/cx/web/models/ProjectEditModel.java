package com.cx.web.models;

import org.hibernate.validator.constraints.NotEmpty;

/**
* @ClassName: ProjectEditModel
* @Description: 考试管理（编辑模型）
* @author BEE 
* @date 2015-12-15 下午5:09:42
 */
public class ProjectEditModel {
	private Integer id;
	@NotEmpty(message="{name.not.empty}")
	private String name;	
	
	public void setId(Integer id){
		this.id=id;
	}
	public void setName(String name){
		this.name=name;
	}	
	
	public Integer getId(){
		return this.id;
	}
	public String getName(){
		return this.name;
	}
}
