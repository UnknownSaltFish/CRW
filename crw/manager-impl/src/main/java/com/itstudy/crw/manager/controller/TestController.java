package com.itstudy.crw.manager.controller;


import com.itstudy.crw.manager.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class TestController {

    @Autowired
    private TestService testService;

    @RequestMapping("/test")
    public String test(){

        testService.insert();
        System.out.println("testController执行了");
        return  "success";
    }


}
