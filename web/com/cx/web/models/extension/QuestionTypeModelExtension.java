package com.cx.web.models.extension;

import java.util.Calendar;

import com.cx.model.models.QuestionType;
import com.cx.web.models.QuestionTypeEditModel;

public class QuestionTypeModelExtension {
	public static QuestionType toModel(QuestionTypeEditModel editModel) {
		QuestionType ret =new QuestionType();
		ret.setId(editModel.getId());
		ret.setName(editModel.getName());
		ret.setEnable(false);
		ret.setFlag("0");
		ret.setCreateTime(Calendar.getInstance());
		return ret;
	}

	public static QuestionTypeEditModel toQuestionTypeEditModel(
			QuestionType model) {
		QuestionTypeEditModel ret=new QuestionTypeEditModel();
		ret.setId(model.getId());
		ret.setName(model.getName());
		return ret;
	}
}
