package com.zq.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/user")
public class UserController {

    //到登录页
    @GetMapping(value = {"", "toLogin"})
    public String loginPage() {
        return "Login";
    }

    //验证登录
    @PostMapping("/login")
    public String login(@RequestParam String username,
                        @RequestParam String password,
                        RedirectAttributes attributes) {
        try {
            //获取当前的用户
            Subject subject = SecurityUtils.getSubject();

            //封装用户的登入数据
            UsernamePasswordToken token = new UsernamePasswordToken(username,
                    DigestUtils.md5DigestAsHex(password.getBytes()));//工具类

            //执行登入方法，如果没有异常则执行成功
            subject.login(token);
            return "redirect:/file/showAll";
        } catch (AuthenticationException e) {
            //表示用户名不存在,重定向携带信息
            attributes.addFlashAttribute("message", "用户名或者密码错误");
            return "redirect:/user";
        }
    }
}
