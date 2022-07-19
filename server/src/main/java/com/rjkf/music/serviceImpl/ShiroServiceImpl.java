package com.rjkf.music.serviceImpl;

import com.rjkf.music.mapper.UserMapper;
import com.rjkf.music.pojo.User;
import com.rjkf.music.service.ShiroService;
import com.rjkf.music.utils.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * @author zhn
 * @version 1.0
 * @description: TODO
 * @date 2022/6/15 14:10
 */
@Service
public class ShiroServiceImpl implements ShiroService {

    //12小时后失效
    private final static int EXPIRE = 12;

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * @description 生成token
     * @param user
     * @return java.lang.String
     * @author zhn
     * @date 2022/6/15 15:14
     */
    @Override
    public String createToken(User user) {
        Integer userId = user.getId();
        //生成一个token
        String token = TokenUtil.generateValue();
        String getToken = (String) redisTemplate.opsForValue().get("id:" + userId);
        if (getToken == null) {
            redisTemplate.opsForValue().set("userId:" + userId,user,EXPIRE,TimeUnit.HOURS);
            redisTemplate.opsForValue().set("id:" + userId, token,EXPIRE,TimeUnit.HOURS);
            redisTemplate.opsForValue().set("token:" + token,String.valueOf(userId),EXPIRE,TimeUnit.HOURS);
            return token;
        } else {
            redisTemplate.opsForValue().set("userId:" + userId,user,EXPIRE,TimeUnit.HOURS);
            redisTemplate.opsForValue().set("id:" + userId, getToken,EXPIRE,TimeUnit.HOURS);
            redisTemplate.opsForValue().set("token:" + getToken,String.valueOf(userId),EXPIRE,TimeUnit.HOURS);
            return getToken;
        }
    }

    /**
     * @description 登出
     * @param token
     * @return void
     * @author zhn
     * @date 2022/6/15 15:14
     */
    @Override
    public void logout(String token) {
        Object getToken = redisTemplate.opsForValue().get("token:" + token);
        redisTemplate.delete("userId:" + getToken);
        redisTemplate.delete("id:" + getToken);
        redisTemplate.delete("token:" + token);
    }
}
