package com.cx.web.models;

/**
* @ClassName: SubjectSearchModel
* @Description: 科目搜索类
* @author BEE 
* @date 2016-1-12 上午9:59:11
 */
public class SubjectSearchModel {
	private String name;
	private Boolean enable;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Boolean getEnable() {
		return enable;
	}

	public void setEnable(Boolean enable) {
		this.enable = enable;
	}
}
