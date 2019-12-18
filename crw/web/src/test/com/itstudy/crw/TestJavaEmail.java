package com.itstudy.crw;

import com.itstudy.crw.util.DesUtil;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.internet.MimeMessage;

public class TestJavaEmail {

    public static void main(String[] args) throws Exception {
// 使用JAVA程序发送邮件
        ApplicationContext application = new ClassPathXmlApplicationContext("spring/spring-*.xml");

// 邮件发送器，由Spring框架提供的
        JavaMailSenderImpl javaMailSender = (JavaMailSenderImpl)application.getBean("sendMail");
        //这是一份邮件
        javaMailSender.setDefaultEncoding("UTF-8");
        MimeMessage mail = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mail);
        //邮件标题
        helper.setSubject("标题");

        StringBuilder content = new StringBuilder();
        String param = "userid123";
        //加密
        param = DesUtil.encrypt(param, "abcdefghijklmnopquvwxyz");

        content.append("<a href='http://www.atcrowdfunding.com/test/act.do?p="+param+"'>激活链接</a>");

        helper.setText(content.toString(), true);

        helper.setFrom("admin@itstudy.com");
        helper.setTo("test@itstudy.com");
        javaMailSender.send(mail);

    }





}
