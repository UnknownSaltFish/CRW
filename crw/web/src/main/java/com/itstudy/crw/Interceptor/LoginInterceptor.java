package com.itstudy.crw.Interceptor;

import com.itstudy.crw.bean.Member;
import com.itstudy.crw.bean.User;
import com.itstudy.crw.util.Const;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashSet;
import java.util.Set;

/*
        定义登录权限拦截器，如果未登录状态对未在白名单的页面进行访问则不允许放行，并转发到登录页面
        注意： 要使拦截器起效，必须在SpringMVC配置文件中对其进行配置
 */
public class LoginInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
    // 第一步，定义不需要被拦截的路径（白名单）
        Set<String> uri = new HashSet<>();
        uri.add("/user/reg.do");
        uri.add("/user/reg.htm");
        uri.add("/login.htm");
        uri.add("/doLogin.do");
        uri.add("/logout.do");
        //获取请求路径
        String servletPath = request.getServletPath();
        //判断
        if(uri.contains(servletPath)){
            return true;
        }
     //第二步，判断用户是否登录
        // 获取session对象，并从session域中取的user对象
        HttpSession session = request.getSession();
        //取得user对象
        User user = (User) session.getAttribute(Const.LOGIN_USER);
        //取得member对象
        Member member = (Member)session.getAttribute(Const.LOGIN_MEMBER);
        // 判断，如果是会员或者管理，则放行。否则放行
        if(user!=null || member != null){
            return true;
        }else{
            //使用request.getContextPath()获取项目的根路径，也就是本项目定义的 APP_PATH
            response.sendRedirect(request.getContextPath()+"/login.htm");
            return false;
        }
    }
}
