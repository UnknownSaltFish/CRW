package com.itstudy.crw.manager.serviceImpl;


import com.itstudy.crw.bean.Permission;
import com.itstudy.crw.manager.dao.PermissionMapper;
import com.itstudy.crw.manager.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PermissionServiceImpl implements PermissionService{

    @Autowired
    private PermissionMapper  permissionMapper;

    public Permission getRootPermission() {
        return permissionMapper.getRootPermission();
    }

    public List<Permission> getChildrenPermissionByPid(Integer id) {
        return permissionMapper.getChildrenPermissionByPid(id);
    }

    public List<Permission> queryAllPermission() {
        return permissionMapper.queryAllPermission();
    }

    public int savePermission(Permission permission) {
        return permissionMapper.insert(permission);
    }

    public Permission queryPermission(Integer id) {
        return permissionMapper.selectByPrimaryKey(id);
    }

    public int updatePermission(Permission permission) {
        return permissionMapper.updateByPrimaryKey(permission);
    }

    public int deletePermission(Integer id) {
        return permissionMapper.deleteByPrimaryKey(id);
    }

    public List<Integer> queryPermissionidsByRoleid(Integer roleid) {
        return permissionMapper.queryPermissionidsByRoleid(roleid);
    }
}
