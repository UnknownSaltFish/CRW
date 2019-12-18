package com.itstudy.crw.manager.dao;

import java.util.List;
import java.util.Map;

import com.itstudy.crw.bean.Data;
import com.itstudy.crw.bean.Permission;
import com.itstudy.crw.bean.Role;
import com.itstudy.crw.bean.User;
import org.apache.ibatis.annotations.Param;



public interface UserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    User selectByPrimaryKey(Integer id);

    List<User> selectAll();

    int updateByPrimaryKey(User record);

	User queryUserlogin(Map<String, Object> paramMap);

	//List<User> queryList(@Param("startIndex") Integer startIndex, @Param("pagesize")  Integer pagesize);

	//Integer queryCount();

	List<User> queryList(Map<String, Object> paramMap);

	Integer queryCount(Map<String, Object> paramMap);

	//int deleteBatchUserByVO(Data data);
	
	//int deleteBatchUserByVO(List<User> userList);
	
	//int deleteBatchUserByVO(User[] userList);1
	
	int deleteBatchUserByVO(@Param("userList") List<User> userList);

	List<Role> querAllRole();

	List<Integer> queryRoleByUserid(Integer id);

	int saveUserRoleRelationship(@Param("userid") Integer userid, @Param("data") Data data);

	int deleteUserRoleRelationship(@Param("userid") Integer userid, @Param("data") Data data);

	List<Permission> queryPermissionByUserid(Integer id);
	
}