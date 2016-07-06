package com.cx.web.controllers;

import java.io.File;
import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.cx.model.models.Paper;
import com.cx.model.models.Subject;
import com.cx.service.interfaces.IPaperService;
import com.cx.service.interfaces.ISubjectService;
import com.cx.web.auth.AuthPassport;
import com.cx.web.models.PaperEditModel;
import com.cx.web.models.PaperSearchModel;
import com.infrastructure.project.common.exception.EntityOperateException;
import com.infrastructure.project.common.exception.ValidatException;
import com.infrastructure.project.common.utilities.PageListUtil;

@Controller
@RequestMapping(value = "/paper")
public class ExamPaperController extends BaseController {
	
	@Autowired
    @Qualifier("PaperService")
	protected IPaperService paperService;
	
	@Autowired
	@Qualifier("SubjectService")
	protected ISubjectService subjectService;
	
	@AuthPassport
	@RequestMapping(value="/list", method = {RequestMethod.GET})
    public String list(HttpServletRequest request, Model model, PaperSearchModel searchModel) throws Exception{ 			
    	model.addAttribute("requestUrl", request.getServletPath());
		model.addAttribute("requestQuery", request.getQueryString());
        model.addAttribute("searchModel", searchModel);
        
        int pageNo = ServletRequestUtils.getIntParameter(request, PageListUtil.PAGE_NO_NAME, PageListUtil.DEFAULT_PAGE_NO);
        int pageSize = ServletRequestUtils.getIntParameter(request, PageListUtil.PAGE_SIZE_NAME, PageListUtil.DEFAULT_PAGE_SIZE);      
        
        //model.addAttribute("contentModel", paperService.listPage(searchModel.getName(), searchModel.getEnable(), pageNo, pageSize));
        model.addAttribute("contentModel", paperService.listPage(searchModel, pageNo, pageSize));
        model.addAttribute(selectDataSourceName, subjectService.getTotallyEnableSelectSource());
        return "/paper/list";
    }
	
	@AuthPassport
	@RequestMapping(value = "/toupload", method = {RequestMethod.GET})
	public String toUpload(HttpServletRequest request, Model model){
		if(!model.containsAttribute("contentModel"))
		{
			PaperEditModel editModel=new PaperEditModel();
			model.addAttribute("contentModel", editModel);
			model.addAttribute(selectDataSourceName, subjectService.getTotallyEnableSelectSource());
		}
        return "paper/toupload";
	}
	
	@RequestMapping(value = "/upload.do", method = RequestMethod.POST)
	public ModelAndView excelUpload(Exception ex,@RequestParam("fileToUpload") MultipartFile file,@RequestParam("subjectId") String subjectId,
			PaperEditModel contentModel, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		if (ex instanceof MaxUploadSizeExceededException) {
			return null;
		}

		if (!file.isEmpty()) {
			String add = request.getSession().getServletContext().getRealPath("/") + "files/";
			String fileName = file.getOriginalFilename();
			try {
				file.transferTo(new File(add + fileName));
				
				Paper paper = new Paper();
				Subject subject = subjectService.get(Integer.valueOf(subjectId));
				paper.setSubject(subject);
				paper.setEnable(true);
				paper.setName(fileName);
				paper.setUploadTime(Calendar.getInstance());
				paper.setFlag("0");
				paperService.saveObject(paper);
			}catch (Exception e) {
			}
		}
		return null;
	}
	
	@AuthPassport
	@RequestMapping(value = "/enable/{id}", method = {RequestMethod.GET})
    public String enable(HttpServletRequest request, Model model, @Valid @ModelAttribute("contentModel") Subject ableModel, @PathVariable(value="id") Integer id, BindingResult result) throws EntityOperateException, ValidatException {
        
        String returnUrl = ServletRequestUtils.getStringParameter(request, "returnUrl", null);
        if(returnUrl==null)
        	returnUrl="paper/list";
        if(result.hasErrors())
            return "redirect:"+returnUrl;
        Paper object = paperService.get(id);
        paperService.enable(object);
    	return "redirect:"+returnUrl;
    }

	@AuthPassport
	@RequestMapping(value = "/disable/{id}", method = {RequestMethod.GET})
    public String disable(HttpServletRequest request, Model model, @PathVariable(value="id") Integer id) throws EntityOperateException, ValidatException {
        String returnUrl = ServletRequestUtils.getStringParameter(request, "returnUrl", null);
        Paper object = paperService.get(id);
        paperService.disable(object);
        if(returnUrl==null)
        	returnUrl="paper/list";
    	return "redirect:"+returnUrl;      	
    }
	
	@AuthPassport
	@RequestMapping(value = "/delete/{id}", method = {RequestMethod.GET})
	public String delete(HttpServletRequest request, Model model, @PathVariable(value="id") Integer id) throws ValidatException, EntityOperateException{	
		paperService.delete(id);
		String returnUrl = ServletRequestUtils.getStringParameter(request, "returnUrl", null);
		if(returnUrl==null)
        	returnUrl="paper/list";
        return "redirect:"+returnUrl;	
	}
}
