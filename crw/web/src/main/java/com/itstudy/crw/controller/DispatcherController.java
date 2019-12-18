package com.itstudy.crw.controller;


import com.itstudy.crw.bean.Member;
import com.itstudy.crw.bean.Permission;
import com.itstudy.crw.bean.User;
import com.itstudy.crw.manager.service.PermissionService;
import com.itstudy.crw.manager.service.UserService;
import com.itstudy.crw.potal.service.MemberService;
import com.itstudy.crw.util.AjaxResult;
import com.itstudy.crw.util.Const;
import com.itstudy.crw.util.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.*;

@Controller
public class DispatcherController {

    @Autowired
    private PermissionService permissionService;

    @Autowired
    private MemberService memberService;

    @Autowired
    private UserService userService;

    @RequestMapping("/index")
    public String index(){

        return  "index";
    }

    @RequestMapping("/member")
    public String member(){

        return  "member/member";
    }


    @RequestMapping("/login")
    public String login(){

        return  "login";
    }
    //登录后进行许可校验并只显示拥有的许可
    @RequestMapping("/main")
    public String main(HttpSession session){

        return  "main";
    }

    @RequestMapping("/logout")
    public String logout(HttpSession session){

            //销毁session域
            session.invalidate();

        return  "redirect:/index.htm";
    }

////    同步请求
//    @RequestMapping("/doLogin")
//    public String doLogin(String loginacct, String userpswd, String type, HttpSession session){
//
//        Map<String,Object> map = new HashMap<>();
//        map.put("loginacct",loginacct);
//        map.put("userpswd",userpswd);
//        map.put("type",type);
//
//        User user = userService.queryUserlogin(map);
//
//        session.setAttribute(Const.LOGIN_USER,user);
//
//        //做重定向：刷新页面时不会再提交表单，而是刷新main。否则会刷新doLogin，重复登录。
//        return "redirect:/main.htm";
//    }

    //    异步请求
    //    由于是异步请求，不会再刷新页面，所以需要用注解@ResponseBody 返回消息
   //ResponseBody 结合jackson组件，将返回结果转换为字符串，将json串以流的方式返回给客户端

    @ResponseBody
    @RequestMapping("/doLogin")
    public Object doLogin(String loginacct, String userpswd, String type, HttpSession session){

        AjaxResult result = new AjaxResult();

        try {
            Map<String,Object> map = new HashMap<>();
            map.put("loginacct",loginacct);
            map.put("userpswd", MD5Util.digest(userpswd));
            map.put("type",type);
            //如果是会员 member对象
            if(type.equals("member")){

                Member member = memberService.queryMembmerlogin(map);

                session.setAttribute(Const.LOGIN_MEMBER ,member);
            }else if(type.equals("user")){

                User user = userService.queryUserlogin(map);

                session.setAttribute(Const.LOGIN_USER,user);

// ----------------------------------------------------------------------------------------------------

                //通过Id 查询当前user所拥有的的许可权限
                List<Permission> myPermissions = userService.queryPermissionByUserid(user.getId());
                //定义根许可
                Permission permissionRoot = null;
                // 用于拦截器拦截许可权限
                Set<String> myUris = new HashSet<>();

                for (Permission permission : myPermissions){
                    //通过子查找父
                    Permission child = permission; //假设为子菜单
                    //将拥有的许可权限放到myUris中
                    myUris.add("/"+child.getUrl());
                    if (child.getPid() == null) {
                        permissionRoot = permission;

                    } else {

                        for (Permission innerpermission : myPermissions) {

                            if (child.getPid() == innerpermission.getId()) {

                                Permission parent = innerpermission;
                                parent.getChildren().add(child);
                                break; //跳出内层循环,如果跳出外层循环,需要使用标签跳出
                            }
                        }
                    }
                }

                session.setAttribute(Const.MY_URIS,myUris);
                session.setAttribute("permissionRoot",permissionRoot);
//-------------------------------------------------------------------------------------------------------------

              }else {

            }
            result.setData(type);
            result.setSuccess(true);

        } catch (Exception e) {
            e.printStackTrace();
            result.setMessage("登录失败");
            result.setSuccess(false);
        }

            return result;
    }

}
