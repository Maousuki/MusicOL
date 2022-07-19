package com.rjkf.music.auth;

import com.rjkf.music.pojo.User;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

/**
 * @author zhn
 * @version 1.0
 * @description: TODO
 * @date 2022/6/15 13:51
 */
@Component
public class AuthRealm extends AuthorizingRealm {

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * @description 授权
     * @param principalCollection
     * @return org.apache.shiro.authz.AuthorizationInfo
     * @author zhn
     * @date 2022/6/15 13:52
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        //1. 从 PrincipalCollection 中来获取登录用户的信息
        User user = (User) principalCollection.getPrimaryPrincipal();
        //2.添加角色和权限
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        simpleAuthorizationInfo.addRole(user.getRole()+"");
        return simpleAuthorizationInfo;
    }

    /**
     * @description 验证
     * @param authenticationToken
     * @return org.apache.shiro.authc.AuthenticationInfo
     * @author zhn
     * @date 2022/6/15 13:52
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        //获取前端传来的token
        String token = (String) authenticationToken.getPrincipal();
        String userId = (String) redisTemplate.opsForValue().get("token:" + token);
        if (userId == null) {
            throw new IncorrectCredentialsException("token失效,请重新登录");
        }
        User user = (User) redisTemplate.opsForValue().get("userId:" + userId);
        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(user, token, this.getName());
        return info;
    }
}
