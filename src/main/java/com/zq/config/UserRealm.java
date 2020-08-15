package com.zq.config;

import com.zq.bean.User;
import com.zq.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;

//自定义的UserRealm
public class UserRealm extends AuthorizingRealm {

    @Autowired
    UserService userService;


    //授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        return null;
    }

    //认证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {

        UsernamePasswordToken userToken = (UsernamePasswordToken) authenticationToken;

        User user = userService.findUserByName(userToken.getUsername());

        if (user == null){
            //表明用户名输入错误
            return null;
        }

        Subject subject = SecurityUtils.getSubject();
        Session session = subject.getSession();
        //将用户的信息存储到session中登录成功后进入页面显示用户的用户名
        //为了安全，把用户的密码信息清除
        String psw = user.getPassword();
        user.setPassword(null);
        session.setAttribute("user", user);

        //密码认证
        return new SimpleAuthenticationInfo("",psw,"");
    }
}
