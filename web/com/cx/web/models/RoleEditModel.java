package com.cx.web.models;

import org.hibernate.validator.constraints.NotEmpty;

public class RoleEditModel {
	private Integer id;
	@NotEmpty(message="{name.not.empty}")
	private String name;	
	private Integer version;
	//是否可用
	private Boolean enable;
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
	public Integer getVersion() {
		return version;
	}
	public void setVersion(Integer version) {
		this.version = version;
	}
	public Boolean getEnable() {
		return enable;
	}
	public void setEnable(Boolean enable) {
		this.enable = enable;
	}
	
}
