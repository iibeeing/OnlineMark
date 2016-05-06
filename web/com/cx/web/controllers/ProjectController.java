package com.cx.web.controllers;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.cx.model.models.Project;
import com.cx.web.auth.AuthPassport;
import com.cx.web.models.ProjectEditModel;
import com.cx.web.models.ProjectSearchModel;
import com.cx.web.models.extension.ProjectModelExtension;
import com.infrastructure.project.common.exception.EntityOperateException;
import com.infrastructure.project.common.exception.ValidatException;
import com.infrastructure.project.common.utilities.PageListUtil;

/**
* @ClassName: ProjectController
* @Description: 考试管理类:project/list
* @author BEE 
* @date 2015-12-15 下午5:07:10
 */
@Controller
@RequestMapping(value = "/project")
public class ProjectController extends BaseController {
	
	@AuthPassport
	@RequestMapping(value="/list", method = {RequestMethod.GET})
    public String list(HttpServletRequest request, Model model, ProjectSearchModel searchModel){ 			
    	model.addAttribute("requestUrl", request.getServletPath());
		model.addAttribute("requestQuery", request.getQueryString());
        model.addAttribute("searchModel", searchModel);
        
        int pageNo = ServletRequestUtils.getIntParameter(request, PageListUtil.PAGE_NO_NAME, PageListUtil.DEFAULT_PAGE_NO);
        int pageSize = ServletRequestUtils.getIntParameter(request, PageListUtil.PAGE_SIZE_NAME, PageListUtil.DEFAULT_PAGE_SIZE);      
        
        model.addAttribute("contentModel", projectService.listPage(searchModel.getName(), searchModel.getEnable(), pageNo, pageSize));
        return "project/list";
    }
	
	@AuthPassport
	@RequestMapping(value = "/add", method = {RequestMethod.GET})
	public String add(HttpServletRequest request, Model model){	
		if(!model.containsAttribute("contentModel"))
		{
			ProjectEditModel projectEditModel=new ProjectEditModel();
			model.addAttribute("contentModel", projectEditModel);
		}
        return "project/edit";
	}
	
	@AuthPassport
	@RequestMapping(value = "/add", method = {RequestMethod.POST})
    public String add(HttpServletRequest request, Model model, @ModelAttribute("contentModel") ProjectEditModel editModel, BindingResult result) throws EntityOperateException, ValidatException {
        if(result.hasErrors()){
        	return add(request, model);		
        }
        String returnUrl = ServletRequestUtils.getStringParameter(request, "returnUrl", null);
        System.out.println("时间： " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime()) + " returnUrl --- " + returnUrl);
        Project project=ProjectModelExtension.toProject(editModel);
        System.out.println(project.getName());
        projectService.save(project);
        if(returnUrl==null)
        	returnUrl="project/list";
    	return "redirect:"+returnUrl;      	
    }
	
	@AuthPassport
	@RequestMapping(value = "/edit/{id}", method = {RequestMethod.GET})
	public String edit(HttpServletRequest request, Model model, @PathVariable(value="id") Integer id) throws ValidatException{	
		if(!model.containsAttribute("contentModel")){
			ProjectEditModel projectEditModel=ProjectModelExtension.toProjectEditModel(projectService.get(id));
			model.addAttribute("contentModel", projectEditModel);
		}

        return "project/edit";	
	}
	
	@AuthPassport
	@RequestMapping(value = "/edit/{id}", method = {RequestMethod.POST})
    public String edit(HttpServletRequest request, Model model, @Valid @ModelAttribute("contentModel") ProjectEditModel editModel, @PathVariable(value="id") Integer id, BindingResult result) throws EntityOperateException, ValidatException {
        if(result.hasErrors()){
        	return edit(request, model, id);
        }
        String returnUrl = ServletRequestUtils.getStringParameter(request, "returnUrl", null);
        System.out.println("时间： " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime()) + " returnUrl --- " + returnUrl);
        //System.out.println("returnUrl --- " + returnUrl);
        Project project=ProjectModelExtension.toProject(editModel);
        project.setId(id);
        projectService.update(project);
        if(returnUrl==null)
        	returnUrl="project/list";
    	return "redirect:"+returnUrl;      	
    }
	
	@AuthPassport
	@RequestMapping(value = "/enable/{id}", method = {RequestMethod.GET})
    public String enable(HttpServletRequest request, Model model, @Valid @ModelAttribute("contentModel") Project ableModel, @PathVariable(value="id") Integer id, BindingResult result) throws EntityOperateException, ValidatException {
        if(result.hasErrors())
            return edit(request, model, id);
        
        String returnUrl = ServletRequestUtils.getStringParameter(request, "returnUrl", null);
        Project project = projectService.get(id);
        projectService.enable(project);
        if(returnUrl==null)
        	returnUrl="project/list";
    	return "redirect:"+returnUrl;
    }

	@AuthPassport
	@RequestMapping(value = "/disable/{id}", method = {RequestMethod.GET})
    public String disable(HttpServletRequest request, Model model, @PathVariable(value="id") Integer id) throws EntityOperateException, ValidatException {
        String returnUrl = ServletRequestUtils.getStringParameter(request, "returnUrl", null);
        Project project = projectService.get(id);
        projectService.disable(project);
        if(returnUrl==null)
        	returnUrl="project/list";
    	return "redirect:"+returnUrl;      	
    }
	
	@AuthPassport
	@RequestMapping(value = "/delete/{id}", method = {RequestMethod.GET})
	public String delete(HttpServletRequest request, Model model, @PathVariable(value="id") Integer id) throws ValidatException, EntityOperateException{	
		projectService.delete(id);
		String returnUrl = ServletRequestUtils.getStringParameter(request, "returnUrl", null);
		if(returnUrl==null)
        	returnUrl="project/list";
        return "redirect:"+returnUrl;	
	}
}
