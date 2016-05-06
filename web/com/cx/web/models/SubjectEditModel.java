package com.cx.web.models;

import org.hibernate.validator.constraints.NotEmpty;


/**
* @ClassName: SubjectEditModel
* @Description: 科目管理（编辑模型）
* @author BEE 
* @date 2016-1-12 上午10:02:13
 */
public class SubjectEditModel {
	private Integer id;
	@NotEmpty(message="{name.not.empty}")
	private String name;	
//	private Project project;
	private Integer projectId;
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
/*	public Project getProject() {
		return project;
	}
	public void setProject(Project project) {
		this.project = project;
	}*/
	public Integer getProjectId() {
		return projectId;
	}
	public void setProjectId(Integer projectId) {
		this.projectId = projectId;
	}
	
}
