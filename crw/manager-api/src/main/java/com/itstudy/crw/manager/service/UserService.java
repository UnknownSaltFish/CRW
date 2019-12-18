package com.itstudy.crw.manager.service;

import java.util.List;
import java.util.Map;

import com.itstudy.crw.bean.Data;
import com.itstudy.crw.bean.Permission;
import com.itstudy.crw.bean.Role;
import com.itstudy.crw.bean.User;
import com.itstudy.crw.util.Page;

public interface UserService {

	User queryUserlogin(Map<String, Object> paramMap);

	//@Deprecated
	//Page queryPage(Integer pageno, Integer pagesize);
	
	
	int saveUser(User user);

	Page queryPage(Map<String, Object> paramMap);

	User getUserById(Integer id);

	int updateUser(User user);

	int deleteUser(Integer id);

	int deleteBatchUser(Integer[] ids);

	int deleteBatchUserByVO(Data data);

	List<Role> querAllRole();

	List<Integer> queryRoleByUserid(Integer id);

	int saveUserRoleRelationship(Integer userid, Data data);

	int deleteUserRoleRelationship(Integer userid, Data data);

	List<Permission> queryPermissionByUserid(Integer id);

}
