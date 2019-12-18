package com.itstudy.crw.listener;

import com.itstudy.crw.bean.Permission;
import com.itstudy.crw.manager.service.PermissionService;
import com.itstudy.crw.util.Const;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class StartSystemListener implements ServletContextListener {

    // 在服务器启动时，创建application对象时需要执行的方法
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {

//    监听器 1.    将项目的requset.getContextPath()放置到application域中
        ServletContext application = servletContextEvent.getServletContext();
        String contextPath = application.getContextPath();
        application.setAttribute("APP_PATH",contextPath);

        System.out.println("APP_PATH执行了 ");
//   监听器 2.   加载所有许可路径
        // 通过Spring专门提供获取ioc容器的工具类获取ioc容器
        ApplicationContext ioc = WebApplicationContextUtils.getWebApplicationContext(application);

        PermissionService permissionService = ioc.getBean(PermissionService.class);
        List<Permission> permissions = permissionService.queryAllPermission();

        Set<String> allUris = new HashSet<>();
        // 将permission中的uri循环遍历放到allUris中
        for(Permission permission : permissions){
            allUris.add("/"+permission.getUrl());
        }
        application.setAttribute(Const.ALL_PERMISSION_URI,allUris);

    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
