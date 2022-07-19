package com.rjkf.music.service;

import com.rjkf.music.pojo.User;

/**
 * @author zhn
 * @version 1.0
 * @description: TODO
 * @date 2022/6/15 14:09
 */
public interface ShiroService {

    /**
     * @description 创建Token
     * @param user
     * @return java.lang.String
     * @author zhn
     * @date 2022/6/15 14:11
     */
    String createToken(User user);

        void logout(String token);
}
