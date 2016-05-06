package com.cx.web.utils;

/**
* @ClassName: QuestionAuditStage
* @Description: 试题审核阶段
* @author BEE 
* @date 2015-12-22 下午3:20:06
 */
public enum QuestionAuditStage {
	FIRST("待初审", 0), 
	SECOND("已初审", 1), 
	THIRD("带复审", 2), 
	FOURTH("已复审", 3);
	// 成员变量
	private String name;
	private int index;

	// 构造方法
	private QuestionAuditStage(String name, int index) {
		this.name = name;
		this.index = index;
	}

	// 普通方法
	public static String getName(int index) {
		for (QuestionAuditStage c : QuestionAuditStage.values()) {
			if (c.getIndex() == index) {
				return c.name;
			}
		}
		return null;
	}

	// get set 方法
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}
}
