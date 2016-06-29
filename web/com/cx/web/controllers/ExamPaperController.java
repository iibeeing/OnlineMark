package com.cx.web.controllers;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.cx.service.interfaces.IPaperService;
import com.cx.web.auth.AuthPassport;
import com.cx.web.models.PaperSearchModel;
import com.infrastructure.project.common.utilities.PageListUtil;

@Controller
@RequestMapping(value = "/paper")
public class ExamPaperController extends BaseController {
	
	@Autowired
    @Qualifier("PaperService")
	protected IPaperService paperService;
	
	@AuthPassport
	@RequestMapping(value="/list", method = {RequestMethod.GET})
    public String list(HttpServletRequest request, Model model, PaperSearchModel searchModel){ 			
    	model.addAttribute("requestUrl", request.getServletPath());
		model.addAttribute("requestQuery", request.getQueryString());
        model.addAttribute("searchModel", searchModel);
        
        int pageNo = ServletRequestUtils.getIntParameter(request, PageListUtil.PAGE_NO_NAME, PageListUtil.DEFAULT_PAGE_NO);
        int pageSize = ServletRequestUtils.getIntParameter(request, PageListUtil.PAGE_SIZE_NAME, PageListUtil.DEFAULT_PAGE_SIZE);      
        
        model.addAttribute("contentModel", paperService.listPage(searchModel.getName(), searchModel.getEnable(), pageNo, pageSize));
        return "paper/list";
    }
	
	@AuthPassport
	@RequestMapping(value = "/toupload", method = {RequestMethod.GET})
	public String toUpload(HttpServletRequest request, Model model){
        return "paper/toupload";
	}
}
