package com.cx.web.controllers;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.cx.web.auth.AuthPassport;
import com.cx.web.models.AccountSearchModel;
import com.infrastructure.project.common.utilities.PageListUtil;

/**
* @ClassName: TaskController
* @Description: TODO(待办任务Controller)
* @author BEE 
* @date 2015-11-18 上午11:54:40
 */
@Controller
@RequestMapping(value="/task")
public class TaskController extends BaseController {
	
	@AuthPassport
	@RequestMapping(value="/index", method = {RequestMethod.GET})
	public String index(HttpServletRequest request, Model model, AccountSearchModel searchModel){
		model.addAttribute("requestUrl", request.getServletPath());
		model.addAttribute("requestQuery", request.getQueryString());

        model.addAttribute("searchModel", searchModel);
        int pageNo = ServletRequestUtils.getIntParameter(request, PageListUtil.PAGE_NO_NAME, PageListUtil.DEFAULT_PAGE_NO);
        int pageSize = ServletRequestUtils.getIntParameter(request, PageListUtil.PAGE_SIZE_NAME, PageListUtil.DEFAULT_PAGE_SIZE);      
        model.addAttribute("contentModel", accountService.listPage(searchModel.getName(), searchModel.getUsername(), pageNo, pageSize));

		return "task/index";
	}
	
}
