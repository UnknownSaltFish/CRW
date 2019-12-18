package com.itstudy.crw.manager.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.itstudy.crw.bean.Data;
import com.itstudy.crw.bean.Permission;
import com.itstudy.crw.bean.Role;
import com.itstudy.crw.manager.service.PermissionService;
import com.itstudy.crw.manager.service.RoleService;
import com.itstudy.crw.util.AjaxResult;
import com.itstudy.crw.util.Page;
import com.itstudy.crw.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;



@Controller
@RequestMapping("/role")
public class RoleController {

	@Autowired
	private RoleService roleService;
	
	@Autowired
	private PermissionService permissionService;

	@RequestMapping("/index")
	public String index() {
		return "role/index";
	}

	
	@RequestMapping("/add")
	public String add() {
		return "role/add";
	}

	
	
	
	@RequestMapping("/assignPermission")
	public String assignPermission() {
		return "role/assignPermission";
	}

	@ResponseBody
	@RequestMapping("/loadDataAsync")
	public Object loadDataAsync(Integer roleid){

			//Demo 4 一次查询所有数据
			List<Permission> root = new ArrayList<Permission>();

			List<Permission> childredPermissons =  permissionService.queryAllPermission();

			List<Integer> permissonIdsForRoleid = permissionService.queryPermissionidsByRoleid(roleid);


			for (Permission permission : childredPermissons){
				//通过子查找父

				if(permissonIdsForRoleid.contains(permission.getId())){
					permission.setChecked(true);
				}

				Permission child = permission; //假设为子菜单

				if (child.getPid() == null) {
					root.add(permission);
				} else {

					for (Permission innerpermission : childredPermissons) {

						if (child.getPid() == innerpermission.getId()) {

							Permission parent = innerpermission;
							parent.getChildren().add(child);
							break; //跳出内层循环,如果跳出外层循环,需要使用标签跳出
						}
					}
				}
			}
		return root ;
	}

	
	@ResponseBody
	@RequestMapping("/doAssignPermission")
	public Object doAssignPermission(Integer roleid, Data datas){
		AjaxResult result = new AjaxResult();
		try {
			int count = roleService.saveRolePermissionRelationship(roleid,datas);
			
			result.setSuccess(count==datas.getIds().size());
			
		} catch (Exception e) {
			e.printStackTrace();
			result.setSuccess(false);
		}
		
		return result;
	}
	
	
	
	//传递多个对象方式
	@ResponseBody
	@RequestMapping("/batchDelete")
	public Object batchDelete(Data datas){
		AjaxResult result = new AjaxResult();
		try {
			int count = roleService.batchDeleteRole(datas);
			if(count==datas.getDatas().size()){
				result.setSuccess(true);
			}else{
				result.setSuccess(false);
			}
		} catch (Exception e) {
			e.printStackTrace();
			result.setSuccess(false);
		}
		
		return result;
	}
	/* 传递多个id值方式
	@ResponseBody
	@RequestMapping("/batchDelete")
	public Object batchDelete(Integer[] ids){
		AjaxResult result = new AjaxResult();
		try {
			int count = roleService.batchDeleteRole(ids);
			if(count==ids.length){
				result.setSuccess(true);
			}else{
				result.setSuccess(false);
			}
		} catch (Exception e) {
			e.printStackTrace();
			result.setSuccess(false);
		}
		
		return result;
	}*/
	
	@ResponseBody
	@RequestMapping("/delete")
	public Object delete(Integer uid){
		AjaxResult result = new AjaxResult();
		try {
			int count = roleService.deleteRole(uid);
			if(count==1){
				result.setSuccess(true);
			}else{
				result.setSuccess(false);
			}
		} catch (Exception e) {
			e.printStackTrace();
			result.setSuccess(false);
		}
		
		return result;
	}
	
	
	@ResponseBody
	@RequestMapping("/doEdit")
	public Object doEdit(Role role) {
		AjaxResult result = new AjaxResult();
		try {
			int count = roleService.updateRole(role);// id没传的情况下,更新并不会报错,但是并没有更新成功,所以,需要加以判断
			if(count==1){
				result.setSuccess(true);
			}else{
				result.setSuccess(false);
			}
		} catch (Exception e) {
			e.printStackTrace();
			result.setSuccess(false);
		}
		
		return result;
	}
	
	@RequestMapping("/edit")
	public String edit(Integer id,Map<String,Object> map) {
		Role role = roleService.getRole(id);
		map.put("role", role);
		return "role/edit";
	}
	
	
	@ResponseBody
	@RequestMapping("/doAdd")
	public Object doAdd(Role role) {
		
		AjaxResult result = new AjaxResult();
		
		try {
			
			roleService.saveRole(role);
			
			result.setSuccess(true);
		} catch (Exception e) {
			e.printStackTrace();
			result.setSuccess(false);
		}
		
		return result;
	}
	
	/**
	 * 异步分页查询
	 * @param pageno
	 * @param pagesize
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/pageQuery")
	public Object pageQuery(String queryText,@RequestParam(required = false, defaultValue = "1") Integer pageno,
			@RequestParam(required = false, defaultValue = "2") Integer pagesize){
		AjaxResult result = new AjaxResult();
		try {
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("pageno", pageno); // 空指针异常
			paramMap.put("pagesize", pagesize);
			
			if(StringUtil.isNotEmpty(queryText)){
				queryText = queryText.replaceAll("%", "\\\\%"); //斜线本身需要转译
				System.out.println("--------------"+queryText);
			}
			
			paramMap.put("queryText", queryText);
			
			// 分页查询数据
			Page<Role> rolePage = roleService.pageQuery(paramMap);

			result.setPage(rolePage);
			result.setSuccess(true);
		} catch (Exception e) {			
			e.printStackTrace();
			result.setSuccess(false);
		}
		return  result;
	}
}
