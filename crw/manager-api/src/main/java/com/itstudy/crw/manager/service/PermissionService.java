package com.itstudy.crw.manager.service;


import com.itstudy.crw.bean.Permission;

import java.util.List;

public interface PermissionService {

    Permission getRootPermission();

    List<Permission> getChildrenPermissionByPid(Integer id);

    List<Permission> queryAllPermission();

    int savePermission(Permission permission);

    Permission queryPermission(Integer id);

    int updatePermission(Permission permission);

    int deletePermission(Integer id);

    List<Integer> queryPermissionidsByRoleid(Integer roleid);
}
