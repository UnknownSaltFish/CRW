package com.itstudy.crw;

import com.itstudy.crw.bean.User;
import com.itstudy.crw.listener.NoActivitiListener;
import com.itstudy.crw.listener.YesActivitiListener;
import com.itstudy.crw.manager.service.UserService;
import com.itstudy.crw.util.MD5Util;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.junit.Test;
import org.omg.CORBA.PUBLIC_MEMBER;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Test01 {

        //得到ioc容器并创建processEngine这个核心类
        ApplicationContext ioc = new ClassPathXmlApplicationContext("spring/spring-*.xml");
        ProcessEngine processEngine = (ProcessEngine) ioc.getBean("processEngine");

        @Test
        //部署流程定义
        public void test02(){

            RepositoryService repositoryService = processEngine.getRepositoryService();
            //拿到部署对象，“Process-02.bpmn” 为画的图的文件名
            Deployment deploy = repositoryService.createDeployment().addClasspathResource("listener.bpmn").deploy();
            System.out.println(deploy);
        }

        @Test
        public void test03(){

            RepositoryService repositoryService = processEngine.getRepositoryService();

            ProcessDefinitionQuery processDefinitionQuery = repositoryService.createProcessDefinitionQuery();
            //查询所有
            List<ProcessDefinition> list = processDefinitionQuery.list();

            for (ProcessDefinition processDefinition:list){
                System.out.println("id="+processDefinition.getId());
                System.out.println("key="+processDefinition.getKey());
            }
            //获取最后一次部署的流程定义对象
            ProcessDefinition singleResult = processDefinitionQuery.latestVersion().singleResult();
            processDefinitionQuery.orderByProcessDefinitionVersion().desc();


        }

        @Test
        //4， 创建流程实例对象
        public void test04(){
            ProcessDefinition processDefinition = processEngine.getRepositoryService().createProcessDefinitionQuery().latestVersion().singleResult();

            RuntimeService runtimeService = processEngine.getRuntimeService();
            // 流程实例对象
            ProcessInstance processInstance = runtimeService.startProcessInstanceById(processDefinition.getId());

        }

        @Test
        //查询流程实例的任务数据
        public void test05(){

            ProcessDefinition processDefinition = processEngine.getRepositoryService().createProcessDefinitionQuery().latestVersion().singleResult();

            TaskService taskService = processEngine.getTaskService();
            TaskQuery taskQuery = taskService.createTaskQuery();
            List<Task> list = taskQuery.taskAssignee("zhangsan").list();

            for(Task task : list){
                System.out.println(task.getId());
                System.out.println(task.getName());

            }

        }

    @Test
    //创建流程引擎，创建23张表
    public void test01(){
        //ProcessEngine processEngine = (ProcessEngine) ioc.getBean("processEngine");

        System.out.println(processEngine);
    }

    @Test
    public void testListener(){
        ProcessDefinition processDefinition = processEngine.getRepositoryService().createProcessDefinitionQuery().latestVersion().singleResult();

        RuntimeService runtimeService = processEngine.getRuntimeService();
        //给两个变量赋值
        Map<String,Object> varibales = new HashMap<>();
        varibales.put("YesActivitiListener",new YesActivitiListener());
        varibales.put("NoActivitiListener",new NoActivitiListener());

        ProcessInstance processInstance = runtimeService.startProcessInstanceById(processDefinition.getId(),varibales);

    }
    //主管审批并通过
    @Test
    public void testZhangsan(){
        ProcessDefinition processDefinition = processEngine.getRepositoryService().createProcessDefinitionQuery().latestVersion().singleResult();

        TaskService taskService = processEngine.getTaskService();

        TaskQuery taskQuery = taskService.createTaskQuery();

        List<Task> list = taskQuery.taskAssignee("zhangsan").list();

        for(Task task:list){
            taskService.setVariable(task.getId(),"flag","true");
            taskService.complete(task.getId());
        }

    }


}
