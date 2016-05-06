package com.cx.model.models;

import java.util.Calendar;

import com.cx.web.utils.QuestionAuditStage;
import com.infrastructure.project.base.model.interfaces.ICUDEable;
import com.infrastructure.project.base.model.models.EnableEntity;

/**
* @ClassName: Question
* @Description: 试题(考试科目试题)
* @author BEE 
* @date 2015-12-22 下午3:57:25
 */
public class Question extends EnableEntity<Integer> implements ICUDEable{

	private Integer id;
	private String name;
	private Calendar updateTime;
	private Integer no;
	private QuestionType questionType;
	private QuestionAuditStage auditStage = QuestionAuditStage.FIRST;
	//所属科目
	private Subject subject;
	//所属考试
	private Project project;
	
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
	public Calendar getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Calendar updateTime) {
		this.updateTime = updateTime;
	}
	public Integer getNo() {
		return no;
	}
	public void setNo(Integer no) {
		this.no = no;
	}
	public QuestionType getQuestionType() {
		return questionType;
	}
	public void setQuestionType(QuestionType questionType) {
		this.questionType = questionType;
	}
	public QuestionAuditStage getAuditStage() {
		return auditStage;
	}
	public void setAuditStage(QuestionAuditStage auditStage) {
		this.auditStage = auditStage;
	}
	public Project getProject() {
		return project;
	}
	public void setProject(Project project) {
		this.project = project;
	}
	public Subject getSubject() {
		return subject;
	}
	public void setSubject(Subject subject) {
		this.subject = subject;
	}
	
}
