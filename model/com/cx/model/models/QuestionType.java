package com.cx.model.models;

import java.util.Calendar;

import com.infrastructure.project.base.model.interfaces.ICUDEable;
import com.infrastructure.project.base.model.models.EnableEntity;

/**
* @ClassName: QuestionType
* @Description: TODO(题型类)
* @author BEE 
* @date 2015-11-20 下午5:28:42
 */
public class QuestionType extends EnableEntity<Integer> implements ICUDEable {

	private String name;

	private Calendar createTime;

	private String flag;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public Calendar getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Calendar createTime) {
		this.createTime = createTime;
	}
	
	
}
