package com.cx.web.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.cx.model.models.QuestionType;
import com.cx.service.interfaces.IQuestionTypeService;
import com.cx.web.auth.AuthPassport;
import com.cx.web.models.QuestionTypeEditModel;
import com.cx.web.models.QuestionTypeSearchModel;
import com.cx.web.models.extension.QuestionTypeModelExtension;
import com.infrastructure.project.common.exception.EntityOperateException;
import com.infrastructure.project.common.exception.ValidatException;
import com.infrastructure.project.common.utilities.PageListUtil;

@Controller
@RequestMapping("/questiontype")
public class QuestionTypeController extends BaseController {

	
	@Autowired
    @Qualifier("QuestionTypeService")
	protected IQuestionTypeService questionTypeService;
	
	@AuthPassport
	@RequestMapping(value="/list", method = {RequestMethod.GET})
    public String list(HttpServletRequest request, Model model, QuestionTypeSearchModel searchModel){ 			
    	model.addAttribute("requestUrl", request.getServletPath());
		model.addAttribute("requestQuery", request.getQueryString());
        model.addAttribute("searchModel", searchModel);
        
        int pageNo = ServletRequestUtils.getIntParameter(request, PageListUtil.PAGE_NO_NAME, PageListUtil.DEFAULT_PAGE_NO);
        int pageSize = ServletRequestUtils.getIntParameter(request, PageListUtil.PAGE_SIZE_NAME, PageListUtil.DEFAULT_PAGE_SIZE);      
        
        model.addAttribute("contentModel", questionTypeService.listPage(searchModel.getName(), searchModel.getEnable(), pageNo, pageSize));
        
        return "questiontype/list";
    }
	
	@AuthPassport
	@RequestMapping(value = "/add", method = {RequestMethod.GET})
	public String add(HttpServletRequest request, Model model){	
		if(!model.containsAttribute("contentModel"))
		{
			QuestionTypeEditModel editModel=new QuestionTypeEditModel();
			model.addAttribute("contentModel", editModel);
		}
        return "questiontype/edit";
	}
	
	@AuthPassport
	@RequestMapping(value = "/add", method = {RequestMethod.POST})
    public String add(HttpServletRequest request, Model model, @ModelAttribute("contentModel") QuestionTypeEditModel editModel, BindingResult result) throws EntityOperateException, ValidatException {
        if(result.hasErrors()){
        	return add(request, model);		
        }
        String returnUrl = ServletRequestUtils.getStringParameter(request, "returnUrl", null);
        System.out.println(returnUrl);
        QuestionType questionType=QuestionTypeModelExtension.toModel(editModel);
        questionTypeService.save(questionType);
        if(returnUrl==null)
        	returnUrl="questiontype/list";
    	return "redirect:"+returnUrl;      	
    }
	
	@AuthPassport
	@RequestMapping(value = "/edit/{id}", method = {RequestMethod.GET})
	public String edit(HttpServletRequest request, Model model, @PathVariable(value="id") Integer id) throws ValidatException{	
		if(!model.containsAttribute("contentModel")){
			QuestionTypeEditModel editModel=QuestionTypeModelExtension.toQuestionTypeEditModel(questionTypeService.get(id));
			model.addAttribute("contentModel", editModel);
		}

        return "questiontype/edit";	
	}
	
	@AuthPassport
	@RequestMapping(value = "/edit/{id}", method = {RequestMethod.POST})
    public String edit(HttpServletRequest request, Model model, @Valid @ModelAttribute("contentModel") QuestionTypeEditModel editModel, @PathVariable(value="id") Integer id, BindingResult result) throws EntityOperateException, ValidatException {
        if(result.hasErrors())
            return edit(request, model, id);
        
        String returnUrl = ServletRequestUtils.getStringParameter(request, "returnUrl", null);
        QuestionType questionType=QuestionTypeModelExtension.toModel(editModel);
        questionType.setId(id);
        questionTypeService.update(questionType);
        if(returnUrl==null)
        	returnUrl="questionType/list";
    	return "redirect:"+returnUrl;      	
    }
	
	@AuthPassport
	@RequestMapping(value = "/enable/{id}", method = {RequestMethod.GET})
    public String enable(HttpServletRequest request, Model model, @Valid @ModelAttribute("contentModel") QuestionType ableModel, @PathVariable(value="id") Integer id, BindingResult result) throws EntityOperateException, ValidatException {
        if(result.hasErrors())
            return edit(request, model, id);
        
        String returnUrl = ServletRequestUtils.getStringParameter(request, "returnUrl", null);
        QuestionType questionType = questionTypeService.get(id);
        questionTypeService.enable(questionType);
        if(returnUrl==null)
        	returnUrl="questiontype/list";
    	return "redirect:"+returnUrl;
    }

	@AuthPassport
	@RequestMapping(value = "/disable/{id}", method = {RequestMethod.GET})
    public String disable(HttpServletRequest request, Model model, @PathVariable(value="id") Integer id) throws EntityOperateException, ValidatException {
        String returnUrl = ServletRequestUtils.getStringParameter(request, "returnUrl", null);
        QuestionType questionType = questionTypeService.get(id);
        questionTypeService.disable(questionType);
        if(returnUrl==null)
        	returnUrl="questiontype/list";
    	return "redirect:"+returnUrl;      	
    }
	
	@AuthPassport
	@RequestMapping(value = "/delete/{id}", method = {RequestMethod.GET})
	public String delete(HttpServletRequest request, Model model, @PathVariable(value="id") Integer id) throws ValidatException, EntityOperateException{	
		questionTypeService.delete(id);
		String returnUrl = ServletRequestUtils.getStringParameter(request, "returnUrl", null);
		if(returnUrl==null)
        	returnUrl="questiontype/list";
        return "redirect:"+returnUrl;	
	}
}
