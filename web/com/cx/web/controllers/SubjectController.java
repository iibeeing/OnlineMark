package com.cx.web.controllers;

import java.text.SimpleDateFormat;
import java.util.Calendar;

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

import com.cx.model.models.Project;
import com.cx.model.models.Subject;
import com.cx.service.interfaces.ISubjectService;
import com.cx.web.auth.AuthPassport;
import com.cx.web.models.SubjectEditModel;
import com.cx.web.models.SubjectSearchModel;
import com.cx.web.models.extension.SubjectModelExtension;
import com.infrastructure.project.common.exception.EntityOperateException;
import com.infrastructure.project.common.exception.ValidatException;
import com.infrastructure.project.common.utilities.PageListUtil;

/**
* @ClassName: SubjectController
* @Description: 科目管理类:subject/list
* @author BEE 
* @date 2016-1-12 上午9:51:09
 */
@Controller
@RequestMapping(value = "/subject")
public class SubjectController extends BaseController {

	@Autowired
    @Qualifier("SubjectService")
	protected ISubjectService subjectService;
	
	@AuthPassport
	@RequestMapping(value="/list", method = {RequestMethod.GET})
    public String list(HttpServletRequest request, Model model, SubjectSearchModel searchModel){ 			
    	model.addAttribute("requestUrl", request.getServletPath());
		model.addAttribute("requestQuery", request.getQueryString());
        model.addAttribute("searchModel", searchModel);
        
        int pageNo = ServletRequestUtils.getIntParameter(request, PageListUtil.PAGE_NO_NAME, PageListUtil.DEFAULT_PAGE_NO);
        int pageSize = ServletRequestUtils.getIntParameter(request, PageListUtil.PAGE_SIZE_NAME, PageListUtil.DEFAULT_PAGE_SIZE);      
        
        model.addAttribute("contentModel", subjectService.listPage(searchModel.getName(), searchModel.getEnable(), pageNo, pageSize));
        return "subject/list";
    }
	
	@AuthPassport
	@RequestMapping(value = "/add", method = {RequestMethod.GET})
	public String add(HttpServletRequest request, Model model){	
		if(!model.containsAttribute("contentModel"))
		{
			SubjectEditModel editModel=new SubjectEditModel();
			model.addAttribute("contentModel", editModel);
			model.addAttribute(selectDataSourceName, projectService.getSelectSource());
		}
        return "subject/edit";
	}
	
	@AuthPassport
	@RequestMapping(value = "/add", method = {RequestMethod.POST})
    public String add(HttpServletRequest request, Model model, @ModelAttribute("contentModel") SubjectEditModel editModel, BindingResult result) throws EntityOperateException, ValidatException {
        if(result.hasErrors()){
        	return add(request, model);		
        }
        String returnUrl = ServletRequestUtils.getStringParameter(request, "returnUrl", null);
        System.out.println("时间： " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime()) + " returnUrl --- " + returnUrl);
        Subject subject=SubjectModelExtension.toObject(editModel);
        System.out.println("---- " + editModel.getProjectId());
        Project project = projectService.get(editModel.getProjectId());
        if(project != null){
        	subject.setProject(project);
        	subjectService.save(subject);
        }else{
        	System.out.println("project ----  is null");
        }
        if(returnUrl==null)
        	returnUrl="subject/list";
    	return "redirect:"+returnUrl;      	
    }
	
	@AuthPassport
	@RequestMapping(value = "/edit/{id}", method = {RequestMethod.GET})
	public String edit(HttpServletRequest request, Model model, @PathVariable(value="id") Integer id) throws ValidatException{	
		if(!model.containsAttribute("contentModel")){
			SubjectEditModel editModel=SubjectModelExtension.toObjectEditModel(subjectService.get(id));
			model.addAttribute("contentModel", editModel);
//			Project project = projectService.get(editModel.getProjectId());
			model.addAttribute(selectDataSourceName, projectService.getSelectSource());
		}
        return "subject/edit";	
	}
	
	@AuthPassport
	@RequestMapping(value = "/edit/{id}", method = {RequestMethod.POST})
    public String edit(HttpServletRequest request, Model model, @Valid @ModelAttribute("contentModel") SubjectEditModel editModel, @PathVariable(value="id") Integer id, BindingResult result) throws EntityOperateException, ValidatException {
        if(result.hasErrors()){
        	return edit(request, model, id);
        }
        String returnUrl = ServletRequestUtils.getStringParameter(request, "returnUrl", null);
        Subject subject=SubjectModelExtension.toObject(editModel);
        subject.setId(id);
        Project project = projectService.get(editModel.getProjectId());
        if(project != null){
        	subject.setProject(project);
        	subjectService.update(subject);
        }else{
        	System.out.println("project ----  is null");
        }
        if(returnUrl==null)
        	returnUrl="subject/list";
    	return "redirect:"+returnUrl;      	
    }
	
	@AuthPassport
	@RequestMapping(value = "/enable/{id}", method = {RequestMethod.GET})
    public String enable(HttpServletRequest request, Model model, @Valid @ModelAttribute("contentModel") Subject ableModel, @PathVariable(value="id") Integer id, BindingResult result) throws EntityOperateException, ValidatException {
        if(result.hasErrors())
            return edit(request, model, id);
        
        String returnUrl = ServletRequestUtils.getStringParameter(request, "returnUrl", null);
        Subject subject = subjectService.get(id);
        subjectService.enable(subject);
        if(returnUrl==null)
        	returnUrl="subject/list";
    	return "redirect:"+returnUrl;
    }

	@AuthPassport
	@RequestMapping(value = "/disable/{id}", method = {RequestMethod.GET})
    public String disable(HttpServletRequest request, Model model, @PathVariable(value="id") Integer id) throws EntityOperateException, ValidatException {
        String returnUrl = ServletRequestUtils.getStringParameter(request, "returnUrl", null);
        Subject subject = subjectService.get(id);
        subjectService.disable(subject);
        if(returnUrl==null)
        	returnUrl="subject/list";
    	return "redirect:"+returnUrl;      	
    }
	
	@AuthPassport
	@RequestMapping(value = "/delete/{id}", method = {RequestMethod.GET})
	public String delete(HttpServletRequest request, Model model, @PathVariable(value="id") Integer id) throws ValidatException, EntityOperateException{	
		subjectService.delete(id);
		String returnUrl = ServletRequestUtils.getStringParameter(request, "returnUrl", null);
		if(returnUrl==null)
        	returnUrl="subject/list";
        return "redirect:"+returnUrl;	
	}
}
