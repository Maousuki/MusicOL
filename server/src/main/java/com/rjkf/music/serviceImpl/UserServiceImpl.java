package com.rjkf.music.serviceImpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rjkf.music.mapper.UserMapper;
import com.rjkf.music.pojo.User;
import com.rjkf.music.service.UserService;
import com.rjkf.music.vo.RespBean;
import com.rjkf.music.vo.RespBeanEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zhn
 * @since 2022-06-13
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private UserMapper userMapper;

    /**
     * @description 判断用户是否存在
     * @param username
     * @return boolean
     * @author zhn
     * @date 2022/6/13 14:24
     */
    @Override
    public boolean existUser(String username) {
        return userMapper.selectOne(new QueryWrapper<User>().eq("username",username)) == null ? false : true;
    }

    /**
     * @description 添加用户
     * @param user
     * @return boolean
     * @author zhn
     * @date 2022/6/13 14:24
     */
    @Override
    public boolean addUser(User user) {
        return userMapper.insert(user) == 0 ? false : true;
    }

    /**
     * @description 验证密码
     * @param username
     * @param password
     * @return void
     * @author zhn
     * @date 2022/6/13 15:43
     */
    @Override
    public boolean verify(String username, String password) {
        User user = userMapper.selectOne(new QueryWrapper<User>().eq("username", username));
        if (user == null) {
            return false;
        }
        if (!user.getPassword().equals(password)) {
            return false;
        } else {
            return true;
        }

    }

    /**
     * @description 通过用户名查找用户
     * @param username
     * @return boolean
     * @author zhn
     * @date 2022/6/13 16:02
     */
    @Override
    public User selectUserByName(String username) {
        return userMapper.selectOne(new QueryWrapper<User>().eq("username",username));
    }

    /**
     * @description 通过id查找用户
     * @param id
     * @return com.rjkf.music.pojo.User
     * @author zhn
     * @date 2022/6/13 17:30
     */
    @Override
    public User selectUserById(int id) {
        return userMapper.selectById(id);
    }

    /**
     * @description 返回所有用户
     * @param
     * @return java.util.List<com.rjkf.music.pojo.User>
     * @author zhn
     * @date 2022/6/13 16:44
     */
    @Override
    public List<User> allUser() {
        List<User> users = userMapper.selectList(new QueryWrapper<>());
        return users;
    }

    /**
     * @description 根据id删除用户
     * @param id
     * @return boolean
     * @author zhn
     * @date 2022/6/13 16:53
     */
    @Override
    public boolean deleteUser(String id) {
        User user = userMapper.selectById(id);
        if (user == null) {
            return false;
        }
        int res = userMapper.delete(new QueryWrapper<User>().eq("id", id));
        return res == 0 ? false : true;
    }

    /**
     * @description 更新用户信息
     * @param user
     * @return boolean
     * @author zhn
     * @date 2022/6/13 17:14
     */
    @Override
    public boolean updateInfo(User user) {
        int res = userMapper.updateById(user);
        return res == 0 ? false : true;
    }

    /**
     * @description 更改密码
     * @param id
     * @param old_password
     * @param password
     * @return boolean
     * @author zhn
     * @date 2022/6/13 18:55
     */
    @Override
    public boolean updatePwd(Integer id, String old_password, String password) {
        User user = userMapper.selectById(id);
        int update = userMapper.update(user, new UpdateWrapper<User>().eq("id", id).set("password", password));
        return update == 0 ? false : true;
    }
}
