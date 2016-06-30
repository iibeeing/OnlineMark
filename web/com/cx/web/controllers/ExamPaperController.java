package com.cx.web.controllers;

import java.io.File;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

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
	
	@RequestMapping(value = "/upload.do", method = RequestMethod.POST)
	public ModelAndView excelUpload(Exception ex,@RequestParam("fileToUpload") MultipartFile file,@RequestParam("remark") String remark,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		if (ex instanceof MaxUploadSizeExceededException) {
			return null;
		}

		if (!file.isEmpty()) {
			String add = request.getSession().getServletContext().getRealPath("/") + "files/";
			String fileName = file.getOriginalFilename();
			try {
				file.transferTo(new File(add + fileName));
			}catch (Exception e) {
			}
		}
		return null;
	}
}
