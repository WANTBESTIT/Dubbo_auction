package org.web.auction.shiro;

import com.alibaba.dubbo.config.annotation.Reference;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.web.auction.pojo.User;
import org.web.auction.service.UserService;

public class ShiroRealm extends AuthorizingRealm {
    @Reference
    private UserService userService;

    //用户认证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {

        //获取用户的帐号
        String username = (String) token.getPrincipal();
        System.out.println("帐号：" + username);
        User user = userService.findUserByusername(username);
        if (user == null) {
            return null;   // 报UnknowAccountException
        }
        String password_db = user.getUserpassword();
        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(user,password_db,"ShiroRealm");
        return info;
    }


    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        return null;
    }
}
