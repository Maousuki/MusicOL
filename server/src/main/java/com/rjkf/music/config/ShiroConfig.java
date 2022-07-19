package com.rjkf.music.config;

import com.rjkf.music.auth.AuthFilter;
import com.rjkf.music.auth.AuthRealm;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author zhn
 * @version 1.0
 * @description: Shiro配置
 * @date 2022/6/15 11:31
 */
@Configuration
public class ShiroConfig {

    /**
     * @description ShiroFilterFactoryBean
     * @param defaultWebSecurityManager
     * @return org.apache.shiro.spring.web.ShiroFilterFactoryBean
     * @author zhn
     * @date 2022/6/15 14:45
     */
    @Bean("shiroFilterFactoryBean")
    public ShiroFilterFactoryBean getShiroFilterFactoryBean(@Qualifier("getDefaultWebSecurityManager") DefaultWebSecurityManager defaultWebSecurityManager){
        ShiroFilterFactoryBean bean = new ShiroFilterFactoryBean();
        //设置安全管理器
        bean.setSecurityManager(defaultWebSecurityManager);

        //添加shiro内置过滤器
        /*
            anon：无需认证就可以访问
            auth：必须认证才能访问
            user：必须拥有 记住我 功能才能用
            perms：拥有对某个资源的权限才能访问
            role：拥有某个角色权限才能访问
         */
        Map<String, Filter> filters = new HashMap<>();
        filters.put("auth",new AuthFilter());
        bean.setFilters(filters);
        Map<String, String> filterMap = new LinkedHashMap<>();
        filterMap.put("/user/**","auth");
        filterMap.put("/singer/**","auth");
        filterMap.put("/song/**","auth");
        filterMap.put("/songList/**","auth");
        filterMap.put("/listSong/**","auth");
        filterMap.put("/comment/**","auth");
        filterMap.put("/collect/**","auth");

        bean.setFilterChainDefinitionMap(filterMap);
        return bean;
    }

    /**
     * @description DefaultWebSecurityManager
     * @param authRealm
     * @return org.apache.shiro.web.mgt.DefaultWebSecurityManager
     * @author zhn
     * @date 2022/6/15 14:45
     */
    @Bean
    public DefaultWebSecurityManager getDefaultWebSecurityManager(@Qualifier("authRealm") AuthRealm authRealm){
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        //关联UserRealm
        securityManager.setRealm(authRealm);
        securityManager.setRememberMeManager(null);
        return securityManager;
    }

}
