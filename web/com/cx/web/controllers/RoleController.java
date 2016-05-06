package com.cx.web.controllers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import net.sf.json.JSONArray;
import net.sf.json.JsonConfig;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.infrastructure.project.common.exception.EntityOperateException;
import com.infrastructure.project.common.exception.ValidatException;
import com.infrastructure.project.common.extension.ArrayHelper;
import com.infrastructure.project.common.extension.StringHelper;
import com.infrastructure.project.common.utilities.PageListUtil;
import com.cx.model.models.Authority;
import com.cx.model.models.Role;
import com.cx.web.auth.AuthPassport;
import com.cx.web.models.RoleBindModel;
import com.cx.web.models.RoleEditModel;
import com.cx.web.models.RoleSearchModel;
import com.cx.web.models.TreeModel;
import com.cx.web.models.extension.RoleBindModelExtension;
import com.cx.web.models.extension.RoleModelExtension;
import com.cx.web.models.extension.TreeModelExtension;

@Controller
//@Scope("prototype")// 设置返回实例的方式 prototype or singleton
@RequestMapping(value = "/role")
public class RoleController extends BaseController {  
	
	@AuthPassport
	@RequestMapping(value="/list", method = {RequestMethod.GET})
    public String list(HttpServletRequest request, Model model, @ModelAttribute RoleSearchModel searchModel){
    	model.addAttribute("requestUrl", request.getServletPath());
		model.addAttribute("requestQuery", request.getQueryString());

        model.addAttribute(searchModelName, searchModel);
        int pageNo = ServletRequestUtils.getIntParameter(request, PageListUtil.PAGE_NO_NAME, PageListUtil.DEFAULT_PAGE_NO);
        int pageSize = ServletRequestUtils.getIntParameter(request, PageListUtil.PAGE_SIZE_NAME, PageListUtil.DEFAULT_PAGE_SIZE);      
        model.addAttribute("contentModel", roleService.listPage(RoleModelExtension.toRoleSearch(searchModel), pageNo, pageSize));
        
        //List<Role> list = roleService.getList();
        return "role/list";
    }
	
	@AuthPassport
	@RequestMapping(value = "/add", method = {RequestMethod.GET})
	public String add(HttpServletRequest request, Model model){	
		if(!model.containsAttribute("contentModel"))
		{
			RoleEditModel roleEditModel=new RoleEditModel();
			model.addAttribute("contentModel", roleEditModel);
		}
        return "role/edit";	  
	}
	
	@AuthPassport
	@RequestMapping(value = "/add", method = {RequestMethod.POST})
    public String add(HttpServletRequest request, Model model, @Valid @ModelAttribute("contentModel") RoleEditModel editModel, BindingResult result) throws EntityOperateException, ValidatException {
        if(result.hasErrors())
            return add(request, model);
		
        String returnUrl = ServletRequestUtils.getStringParameter(request, "returnUrl", null);
        Role role=RoleModelExtension.toModel(editModel);
        roleService.save(role);
        if(returnUrl==null)
        	returnUrl="role/list";
    	return "redirect:"+returnUrl;     	
    }
	
	@AuthPassport
	@RequestMapping(value="/bind/{id}", method = {RequestMethod.GET})
	public String bind(HttpServletRequest request, Model model, @PathVariable(value="id") Integer id) throws ValidatException{
		Role role=roleService.get(id);
		List<Integer> checkedAuthorityIds = new ArrayList<Integer>();
		
		if(role.getAuthorities()!=null){	
			List<Authority> roleAuthorities=role.getAuthorities();
			for(Authority item : roleAuthorities){
				checkedAuthorityIds.add(item.getId());
			}			
		}
		
		if(!model.containsAttribute("contentModel")){
			RoleBindModel roleBindModel=RoleBindModelExtension.toRoleBindModel(role);
			Integer[] checkedAuthorityIdsArray=new Integer[checkedAuthorityIds.size()];
			checkedAuthorityIds.toArray(checkedAuthorityIdsArray);
			roleBindModel.setAuthorityIds(checkedAuthorityIdsArray);
			model.addAttribute("contentModel", roleBindModel);
		}
		
		String expanded = ServletRequestUtils.getStringParameter(request, "expanded", null);
		List<TreeModel> children=TreeModelExtension.ToTreeModels(authorityService.listChain(), null, checkedAuthorityIds, StringHelper.toIntegerList( expanded, ","));		
		List<TreeModel> treeModels=new ArrayList<TreeModel>(Arrays.asList(new TreeModel(null,null,"根节点",false,false,false,children)));
		model.addAttribute(treeDataSourceName, JSONArray .fromObject(treeModels, new JsonConfig()).toString());

		return "role/bind";
	}
	
	@AuthPassport
	@RequestMapping(value="/bind/{id}", method = {RequestMethod.POST})
	public String bind(HttpServletRequest request, Model model, @Valid @ModelAttribute("contentModel") RoleBindModel roleBindModel, @PathVariable(value="id") Integer id, BindingResult result) throws ValidatException, EntityOperateException{
        if(result.hasErrors())
            return bind(request, model, id);
        
        roleService.saveAuthorize(id, ArrayHelper.removeArrayItem(roleBindModel.getAuthorityIds(), new Integer(0)));      
        String returnUrl = ServletRequestUtils.getStringParameter(request, "returnUrl", null);
        if(returnUrl==null)
        	returnUrl="role/list";
    	return "redirect:"+returnUrl; 
	}   
	
	@AuthPassport
	@RequestMapping(value = "/edit/{id}", method = {RequestMethod.GET})
	public String edit(HttpServletRequest request, Model model, @PathVariable(value="id") Integer id) throws ValidatException{	
		if(!model.containsAttribute("contentModel")){
			RoleEditModel editModel=RoleModelExtension.toEditModel(roleService.get(id));
			model.addAttribute("contentModel", editModel);
		}
        return "role/edit";	
	}
	
	@AuthPassport
	@RequestMapping(value = "/edit/{id}", method = {RequestMethod.POST})
    public String edit(HttpServletRequest request, Model model, @Valid @ModelAttribute("contentModel") RoleEditModel editModel, @PathVariable(value="id") Integer id, BindingResult result)  {
		String returnUrl = ServletRequestUtils.getStringParameter(request, "returnUrl", null);
		try{
    		if(result.hasErrors()){
            	return edit(request, model, id);
            }
            Role role=RoleModelExtension.toModel(editModel);
            role.setId(id);
//            roleService.update(role);
            roleService.updateName(role);
            if(returnUrl==null){
            	returnUrl="account/list";
            }
            return "redirect:"+returnUrl;
        }catch (Exception e) {
			e.printStackTrace();
			return "redirect:"+returnUrl;
		}
    }

	@AuthPassport
	@RequestMapping(value = "/enable/{id}", method = {RequestMethod.GET})
    public String enable(HttpServletRequest request, Model model, @Valid @ModelAttribute("contentModel") Role ableModel, @PathVariable(value="id") Integer id, BindingResult result) throws EntityOperateException, ValidatException {
        if(result.hasErrors())
        	return list(request,model, new RoleSearchModel());
        String returnUrl = ServletRequestUtils.getStringParameter(request, "returnUrl", null);
        Role role = roleService.get(id);
        roleService.enable(role);
        if(returnUrl==null)
        	returnUrl="role/list";
    	return "redirect:"+returnUrl;
    }

	@AuthPassport
	@RequestMapping(value = "/disable/{id}", method = {RequestMethod.GET})
    public String disable(HttpServletRequest request, Model model, @PathVariable(value="id") Integer id) throws EntityOperateException, ValidatException {
        String returnUrl = ServletRequestUtils.getStringParameter(request, "returnUrl", null);
        Role role = roleService.get(id);
        roleService.disable(role);
        if(returnUrl==null)
        	returnUrl="role/list";
    	return "redirect:"+returnUrl;      	
    }
}  
