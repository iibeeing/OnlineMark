package com.cx.web.controllers;

import java.sql.SQLException;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.cx.service.interfaces.IAccountService;
import com.cx.service.interfaces.IAuthorityService;
import com.cx.service.interfaces.IOrganizationService;
import com.cx.service.interfaces.IProjectService;
import com.cx.service.interfaces.IRoleService;

public abstract class BaseController {  

	protected final String searchModelName="searchModel";
	protected final String treeDataSourceName="treeDataSource";
	protected final String selectDataSourceName="selectDataSource";
	
	@Autowired
    @Qualifier("RoleService")
    protected IRoleService roleService;
	
	@Autowired
    @Qualifier("AccountService")
    protected IAccountService accountService;
	
	@Autowired
    @Qualifier("AuthorityService")
	protected IAuthorityService authorityService;
	
	@Autowired
    @Qualifier("OrganizationService")
	protected IOrganizationService organizationService;
	
	@Autowired
    @Qualifier("ProjectService")
	protected IProjectService projectService;
/*	
	@Autowired
    @Qualifier("QuestionTypeService")
	protected IQuestionTypeService questionTypeService;*/
	
	@ExceptionHandler  
    public String exception(HttpServletRequest request, Exception e) {  
          
        request.setAttribute("exceptionMessage", e.getMessage());  
          
        // 根据不同错误转向不同页面  
        if(e instanceof SQLException) 
            return "testerror";   
        else
            return "error";  
    }  
	
}  
