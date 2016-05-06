package com.cx.web.controllers;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import net.sf.json.JSONArray;
import net.sf.json.JsonConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.cx.model.models.Account;
import com.cx.model.models.Authority;
import com.cx.model.models.Project;
import com.cx.service.interfaces.IAccountService;
import com.cx.web.auth.AccountAuth;
import com.cx.web.auth.AccountRole;
import com.cx.web.auth.AuthHelper;
import com.cx.web.auth.AuthPassport;
import com.cx.web.auth.AuthorityMenu;
import com.cx.web.auth.PermissionMenu;
import com.cx.web.models.AccountAddModel;
import com.cx.web.models.AccountAuthorizeModel;
import com.cx.web.models.AccountEditModel;
import com.cx.web.models.AccountLoginModel;
import com.cx.web.models.AccountRegisterModel;
import com.cx.web.models.AccountSearchModel;
import com.cx.web.models.RoleEditModel;
import com.cx.web.models.TreeModel;
import com.cx.web.models.extension.AccountAddModelExtension;
import com.cx.web.models.extension.AccountAuthorizeModelExtension;
import com.cx.web.models.extension.AccountModelExtension;
import com.cx.web.models.extension.AccountRegisterModelExtension;
import com.cx.web.models.extension.TreeModelExtension;
import com.infrastructure.project.common.exception.EntityOperateException;
import com.infrastructure.project.common.exception.ValidatException;
import com.infrastructure.project.common.extension.StringHelper;
import com.infrastructure.project.common.utilities.PageListUtil;

@Controller
@RequestMapping(value = "/account")
public class AccountController extends BaseController {  
	
	@Autowired
    @Qualifier("AccountService")
    private IAccountService accountService;
	
	@RequestMapping(value="/login", method = {RequestMethod.GET})
    public String login(Model model){
		if(!model.containsAttribute("contentModel"))
            model.addAttribute("contentModel", new AccountLoginModel());
        return "account/login";
    }
	
	@RequestMapping(value="/login", method = {RequestMethod.POST})
	public String login(HttpServletRequest request, Model model, @Valid @ModelAttribute("contentModel") AccountLoginModel accountLoginModel, BindingResult result) throws ValidatException, EntityOperateException, NoSuchAlgorithmException{
		//如果有验证错误 返回到form页面
        if(result.hasErrors())
            return login(model);
        Account account=accountService.login(accountLoginModel.getUsername().trim(), accountLoginModel.getPassword().trim());
        if(account==null || account.getEnable()==false || account.getRole()==null){
        	if(account==null){
	        	result.addError(new FieldError("contentModel","username","用户名或密码错误。"));
	        	result.addError(new FieldError("contentModel","password","用户名或密码错误。"));
        	}
        	else if(account.getEnable()==false)
        		result.addError(new FieldError("contentModel","username","此用户被禁用，不能登录。"));
        	else
        		result.addError(new FieldError("contentModel","username","此用户当前未被授权，不能登录。"));
            return login(model);
        }
        else{
        	AccountAuth accountAuth=new AccountAuth(account.getId(), account.getName(), account.getUsername());
        	AccountRole accountRole=new AccountRole(account.getRole().getId(), account.getRole().getName());
        	List<AuthorityMenu> authorityMenus=new ArrayList<AuthorityMenu>();
        	List<Authority> roleAuthorities=account.getRole().getAuthorities();
        	
        	for(Authority authority :roleAuthorities){
        		if(authority.getParent()==null){
        			AuthorityMenu authorityMenu=new AuthorityMenu(authority.getId(), authority.getName(), authority.getItemIcon(), authority.getUrl());
        			
        			List<AuthorityMenu> childrenAuthorityMenus=new ArrayList<AuthorityMenu>();
        			for(Authority subAuthority :roleAuthorities){   				
        				if(subAuthority.getParent()!=null && subAuthority.getParent().getId().equals(authority.getId()))
        					childrenAuthorityMenus.add(new AuthorityMenu(subAuthority.getId(), subAuthority.getName(), subAuthority.getItemIcon(), subAuthority.getUrl()));
        			}
        			authorityMenu.setChildrens(childrenAuthorityMenus);
        			authorityMenus.add(authorityMenu);
        		}
        	}
        	
    		List<PermissionMenu> permissionMenus=new ArrayList<PermissionMenu>(); 	
        	for(Authority authority : roleAuthorities){ 	  		
        		List<Authority> parentAuthorities=new ArrayList<Authority>();
        		Authority tempAuthority=authority;
        		while(tempAuthority.getParent()!=null){
        			parentAuthorities.add(tempAuthority.getParent());
        			tempAuthority=tempAuthority.getParent();
        		}
        		if(parentAuthorities.size()>=2)
        			permissionMenus.add(new PermissionMenu(parentAuthorities.get(parentAuthorities.size()-1).getId(),parentAuthorities.get(parentAuthorities.size()-1).getName(),parentAuthorities.get(parentAuthorities.size()-2).getId(),parentAuthorities.get(parentAuthorities.size()-2).getName(),authority.getName(),authority.getMatchUrl()));
        		else if(parentAuthorities.size()==1)
        			permissionMenus.add(new PermissionMenu(parentAuthorities.get(0).getId(),parentAuthorities.get(0).getName(),authority.getId(),authority.getName(),authority.getName(),authority.getMatchUrl()));
        		else
        			permissionMenus.add(new PermissionMenu(authority.getId(),authority.getName(),null,null,authority.getName(),authority.getMatchUrl()));
        	}
        	accountRole.setAuthorityMenus(authorityMenus);
        	accountRole.setPermissionMenus(permissionMenus);
        	accountAuth.setAccountRole(accountRole);
        	AuthHelper.setSessionAccountAuth(request, accountAuth);
        }
        
        String returnUrl = ServletRequestUtils.getStringParameter(request, "returnUrl", null);
        if(returnUrl==null)
        	returnUrl="/home/index";
    	return "redirect:"+returnUrl; 	
	}
	
	@RequestMapping(value="/register", method = {RequestMethod.GET})
    public String register(Model model){
		if(!model.containsAttribute("contentModel"))
            model.addAttribute("contentModel", new AccountRegisterModel());
        return "account/register";
    }
	
	@RequestMapping(value="/register", method = {RequestMethod.POST})
	public String register(HttpServletRequest request, Model model, @Valid @ModelAttribute("contentModel") AccountRegisterModel accountRegisterModel, BindingResult result) throws ValidatException, EntityOperateException, NoSuchAlgorithmException{
		if(!accountRegisterModel.getPassword().equals(accountRegisterModel.getConfirmPassword()))
			result.addError(new FieldError("contentModel","confirmPassword","确认密码与密码输入不一致。"));
		//如果有验证错误 返回到form页面
        if(result.hasErrors())
            return register(model);
        else if(accountService.accountExist(accountRegisterModel.getUsername())){
        	result.addError(new FieldError("contentModel","username","该用户名已被注册。"));
            return register(model);
        }      
        accountService.saveRegister(AccountRegisterModelExtension.toAccount(accountRegisterModel));
        
        String returnUrl = ServletRequestUtils.getStringParameter(request, "returnUrl", null);
        if(returnUrl==null)
        	returnUrl="/account/login";
    	return "redirect:"+returnUrl; 	
	}
	
	@AuthPassport
	@RequestMapping(value="/list", method = {RequestMethod.GET})
    public String list(HttpServletRequest request, Model model, AccountSearchModel searchModel){ 			
    	model.addAttribute("requestUrl", request.getServletPath());
		model.addAttribute("requestQuery", request.getQueryString());

        model.addAttribute("searchModel", searchModel);

        int pageNo = ServletRequestUtils.getIntParameter(request, PageListUtil.PAGE_NO_NAME, PageListUtil.DEFAULT_PAGE_NO);
        int pageSize = ServletRequestUtils.getIntParameter(request, PageListUtil.PAGE_SIZE_NAME, PageListUtil.DEFAULT_PAGE_SIZE);      
        model.addAttribute("contentModel", accountService.listPage(searchModel.getName(), searchModel.getUsername(), pageNo, pageSize));

        return "account/list";
    }
	
	@AuthPassport
	@RequestMapping(value="/authorize/{id}", method = {RequestMethod.GET})
    public String authorize(HttpServletRequest request, Model model, @PathVariable(value="id") Integer id) throws ValidatException{	
		if(!model.containsAttribute("contentModel")){
			AccountAuthorizeModel accountBindModel=AccountAuthorizeModelExtension.toAccountBindModel(accountService.get(id));
            model.addAttribute("contentModel", accountBindModel);
        }	

		List<TreeModel> treeModels;
		AccountAuthorizeModel authorizeModel=(AccountAuthorizeModel)model.asMap().get("contentModel");
		String expanded = ServletRequestUtils.getStringParameter(request, "expanded", null);
		if(authorizeModel.getOrganizationId()!=null && authorizeModel.getOrganizationId()>0){
			List<TreeModel> children=TreeModelExtension.ToTreeModels(organizationService.listChain(), authorizeModel.getOrganizationId(), null, StringHelper.toIntegerList( expanded, ","));		
			treeModels=new ArrayList<TreeModel>(Arrays.asList(new TreeModel("0","0","根节点",false,false,false,children)));
		}
		else{
			List<TreeModel> children=TreeModelExtension.ToTreeModels(organizationService.listChain(), null, null, StringHelper.toIntegerList( expanded, ","));		
			treeModels=new ArrayList<TreeModel>(Arrays.asList(new TreeModel("0","0","根节点",false,true,false,children)));
		}
		model.addAttribute(treeDataSourceName, JSONArray .fromObject(treeModels, new JsonConfig()).toString());
		model.addAttribute(selectDataSourceName, roleService.getSelectSource());
		
        return "account/authorize";
    }

	@AuthPassport
	@RequestMapping(value="/authorize/{id}", method = {RequestMethod.POST})
	public String authorize(HttpServletRequest request, Model model, @Valid @ModelAttribute("contentModel") AccountAuthorizeModel accountAuthorizeModel, @PathVariable(value="id") Integer id, BindingResult result) throws ValidatException, EntityOperateException{
		if(result.hasErrors())
            return authorize(request, model, id);

		accountService.updateBind(id, accountAuthorizeModel.getRoleId(), accountAuthorizeModel.getOrganizationId());       
        String returnUrl = ServletRequestUtils.getStringParameter(request, "returnUrl", null);
        if(returnUrl==null)
        	returnUrl="account/list";
    	return "redirect:"+returnUrl; 	
	}
	
	@AuthPassport
	@RequestMapping(value = "/enable/{id}", method = {RequestMethod.GET})
    public String enable(HttpServletRequest request, Model model, @Valid @ModelAttribute("contentModel") Project ableModel, @PathVariable(value="id") Integer id, BindingResult result) throws EntityOperateException, ValidatException {
        if(result.hasErrors())
        	return list(request,model, new AccountSearchModel());
        String returnUrl = ServletRequestUtils.getStringParameter(request, "returnUrl", null);
        Account account = accountService.get(id);
        accountService.enable(account);
        if(returnUrl==null)
        	returnUrl="account/list";
    	return "redirect:"+returnUrl;
    }

	@AuthPassport
	@RequestMapping(value = "/disable/{id}", method = {RequestMethod.GET})
    public String disable(HttpServletRequest request, Model model, @PathVariable(value="id") Integer id) throws EntityOperateException, ValidatException {
        String returnUrl = ServletRequestUtils.getStringParameter(request, "returnUrl", null);
        Account account = accountService.get(id);
        accountService.disable(account);
        if(returnUrl==null)
        	returnUrl="account/list";
    	return "redirect:"+returnUrl;      	
    }
	
	/**
	* @Title: edit
	* @Description: 响应编辑事件
	* @param @param request
	* @param @param model
	* @param @param id
	* @param @return
	* @param @throws ValidatException    设定文件
	* @return String    返回类型
	* @throws
	 */
	@AuthPassport
	@RequestMapping(value = "/edit/{id}", method = {RequestMethod.GET})
	public String edit(HttpServletRequest request, Model model, @PathVariable(value="id") Integer id) throws ValidatException{	
		if(!model.containsAttribute("contentModel")){
			AccountEditModel editModel=AccountModelExtension.toEditModel(accountService.get(id));
			model.addAttribute("contentModel", editModel);
		}
        return "account/edit";	
	}
	
	/**
	* @Title: edit
	* @Description: 处理编辑事件
	* @param @param request
	* @param @param model
	* @param @param editModel
	* @param @param id
	* @param @param result
	* @param @return    设定文件
	* @return String    返回类型
	* @throws
	 */
	@AuthPassport
	@RequestMapping(value = "/edit/{id}", method = {RequestMethod.POST})
    public String edit(HttpServletRequest request, Model model, @Valid @ModelAttribute("contentModel") AccountEditModel editModel, @PathVariable(value="id") Integer id, BindingResult result)  {
		String returnUrl = ServletRequestUtils.getStringParameter(request, "returnUrl", null);
		try{
    		if(result.hasErrors()){
            	return edit(request, model, id);
            }
            Account account=AccountModelExtension.toModel(editModel);
            account.setId(id);
            accountService.updateAccount(account);
            if(returnUrl==null){
            	returnUrl="account/list";
            }
            return "redirect:"+returnUrl;
        }catch (Exception e) {
			e.printStackTrace();
			return "redirect:"+returnUrl;
		}
    }
	
	/**
	* @Title: delete
	* @Description: 删除账户
	* @param @param request
	* @param @param model
	* @param @param id
	* @param @return
	* @param @throws ValidatException
	* @param @throws EntityOperateException    设定文件
	* @return String    返回类型
	* @throws
	 */
	@AuthPassport
	@RequestMapping(value = "/delete/{id}", method = {RequestMethod.GET})
	public String delete(HttpServletRequest request, Model model, @PathVariable(value="id") Integer id) throws ValidatException, EntityOperateException{	
		accountService.delete(id);
		String returnUrl = ServletRequestUtils.getStringParameter(request, "returnUrl", null);
		if(returnUrl==null)
        	returnUrl="project/list";
        return "redirect:"+returnUrl;	
	}
	
	@RequestMapping(value="/add", method = {RequestMethod.POST})
	public String add(HttpServletRequest request, Model model, @Valid @ModelAttribute("contentModel") AccountAddModel accountAddModel, BindingResult result) throws ValidatException, EntityOperateException, NoSuchAlgorithmException{
		if(!accountAddModel.getPassword().equals(accountAddModel.getConfirmPassword()))
			result.addError(new FieldError("contentModel","confirmPassword","确认密码与密码输入不一致。"));
		//如果有验证错误 返回到form页面
        if(result.hasErrors())
            return register(model);
        else if(accountService.accountExist(accountAddModel.getUsername())){
        	result.addError(new FieldError("contentModel","username","该用户名已被注册。"));
            return add(model);
        }      
        accountService.saveRegister(AccountAddModelExtension.toAccount(accountAddModel));
        String returnUrl = ServletRequestUtils.getStringParameter(request, "returnUrl", null);
        if(returnUrl==null)
        	returnUrl="/account/add";
    	return "redirect:"+returnUrl; 	
	}
	
	@AuthPassport
	@RequestMapping(value="/add", method = {RequestMethod.GET})
    public String add(Model model){
		if(!model.containsAttribute("contentModel"))
            model.addAttribute("contentModel", new AccountAddModel());
        return "account/add";
    }

    //通过准考证号获取考生信息
	@RequestMapping("/zlj.controller.ViolationDisciplineController/getStudentInfo.cqzk")
	@ResponseBody
	public String getStudentInfo(HttpServletRequest request) {
		String t = request.getParameter("id").trim();
		System.out.println("-------------- " + t);
		return "success";
	}
	
    //通过准考证号获取考生信息
	@RequestMapping("/zlj.controller.ViolationDisciplineController/testJson.cqzk")
	@ResponseBody
	public List<Map<String, Object>> testJson(HttpServletRequest request) {
		String t = request.getParameter("id").trim();
		System.out.println("-------------- " + t);
		List<Map<String, Object>> maplist = new ArrayList<Map<String,Object>>();
		for(int j=0;j<3;j++){
			Map<String,Object> map = new HashMap<String, Object>();
			for(int i=0;i<3;i++){
				map.put(String.valueOf(i), String.valueOf(i));
			}
			maplist.add(map);
		}
		return maplist;
	}
	
    //通过准考证号获取考生信息
	@RequestMapping("/zlj.controller.ViolationDisciplineController/toEdit.cqzk")
	@ResponseBody
	public ModelAndView toEdit(HttpServletRequest request, Model model) throws NumberFormatException, ValidatException {
		String id = request.getParameter("id").trim();
		System.out.println("-------------- " + id);
		System.out.println("model class is " + Model.class);
		System.out.println("model class is " + model.toString());
		AccountEditModel editModel=AccountModelExtension.toEditModel(accountService.get(Integer.valueOf(id)));
		model.addAttribute("contentModel", editModel);
        return new ModelAndView("account/edit");
	}
	
    //通过准考证号获取考生信息
	@RequestMapping("/zlj.controller.ViolationDisciplineController/delete.cqzk")
	@ResponseBody
	public Map<String,Object> delete(HttpServletRequest request) throws NumberFormatException, EntityOperateException, ValidatException {
		String id = request.getParameter("id").trim();
		accountService.delete(Integer.valueOf(id));
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("msg", "删除成功");
		return map;
	}
	
	@RequestMapping(value = "/toEdit/{id}", method = {RequestMethod.GET})
	public String toEdit(HttpServletRequest request, Model model, @PathVariable(value="id") Integer id) throws ValidatException{	
		//String id = request.getParameter("id").trim();
		//System.out.println("id is " + id);
		if(!model.containsAttribute("contentModel")){
			AccountEditModel editModel=AccountModelExtension.toEditModel(accountService.get(id));
			model.addAttribute("contentModel", editModel);
		}
        return "account/edit";	
	}
	
	@RequestMapping(value = "/toRequestEdit", method = {RequestMethod.GET})
	public String toRequestEdit(HttpServletRequest request, Model model) throws ValidatException{	
		String id = request.getParameter("id").trim();
		System.out.println("id is " + id);
		if(!model.containsAttribute("contentModel")){
			AccountEditModel editModel=AccountModelExtension.toEditModel(accountService.get(Integer.valueOf(id)));
			model.addAttribute("contentModel", editModel);
			model.addAttribute("xx", "这是这是这是");
		}
        return "account/edit";	
	}
	
	@RequestMapping(value = "/toEdit/{id}", method = {RequestMethod.POST})
    public String toEdit(HttpServletRequest request, Model model, @Valid @ModelAttribute("contentModel") AccountEditModel editModel, @PathVariable(value="id") Integer id, BindingResult result)  {
		String returnUrl = ServletRequestUtils.getStringParameter(request, "returnUrl", null);
		try{
    		if(result.hasErrors()){
            	return edit(request, model, id);
            }
            Account account=AccountModelExtension.toModel(editModel);
            account.setId(id);
            accountService.updateAccount(account);
            if(returnUrl==null){
            	returnUrl="/account/list";
            }
            return "redirect:"+returnUrl;
        }catch (Exception e) {
			e.printStackTrace();
			return "redirect:"+returnUrl;
		}
    }
	
}  
