package com.itstudy.crw.Interceptor;

import com.itstudy.crw.bean.Permission;
import com.itstudy.crw.manager.service.PermissionService;
import com.itstudy.crw.util.Const;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class AuthInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private PermissionService permissionService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

//    1.查询所有许可
//
//        List<Permission> permissions = permissionService.queryAllPermission();
//
//        Set<String> allUris = new HashSet<>();
//        // 将permission中的uri循环遍历放到allUris中
//        for(Permission permission : permissions){
//            allUris.add("/"+permission.getUrl());
//        }
//        如果每次都拦截都查询数据库，则效率比较低
//
//        改进：通过监听器
//              在服务器启动时加载所有许可路径，并存放在applicaiton域中
        Set<String> allUris = (Set<String>)request.getSession().getServletContext().getAttribute(Const.ALL_PERMISSION_URI);

//    2.判断请求请求路径是否在所有许可范围
        String servletPath = request.getServletPath();
        if(allUris.contains(servletPath)){
            //  定义一个set获取session域中的uris
            Set<String> myURIs = (Set<String>)request.getSession().getAttribute(Const.MY_URIS);
            // 当前路径包括在许可权限的路径，放行
            if(myURIs.contains(servletPath)){
                return true;
            }else{
                //当前路径不包括在许可权限的路径，不放行，并转发到登录页面
                response.sendRedirect(request.getContextPath()+"/login.htm");
                return false;
            }
        }else{
            // 不在拦截的范围内，放行
            return true;
        }
    }
}
