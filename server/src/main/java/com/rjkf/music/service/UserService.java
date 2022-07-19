package com.rjkf.music.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.rjkf.music.pojo.User;
import com.rjkf.music.vo.RespBean;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zhn
 * @since 2022-06-13
 */
public interface UserService extends IService<User> {

    /**
     * @description 判断用户是否存在
     * @param username
     * @return boolean
     * @author zhn
     * @date 2022/6/13 14:23
     */
    boolean existUser(String username);


    /**
     * @description 添加用户
     * @param user
     * @return boolean
     * @author zhn
     * @date 2022/6/13 14:24
     */
    boolean addUser(User user);

    /**
     * @description 验证密码
     * @param username
     * @param password
     * @return void
     * @author zhn
     * @date 2022/6/13 15:43
     */
    boolean verify(String username, String password);

    /**
     * @description 通过用户名查找用户
     * @param username
     * @return boolean
     * @author zhn
     * @date 2022/6/13 16:01
     */
    User selectUserByName(String username);

    /**
     * @description 通过id查找用户
     * @param id
     * @return com.rjkf.music.pojo.User
     * @author zhn
     * @date 2022/6/13 17:30
     */
    User selectUserById(int id);

    /**
     * @description 返回所有用户
     * @param
     * @return java.util.List<com.rjkf.music.pojo.User>
     * @author zhn
     * @date 2022/6/13 16:41
     */
    List<User> allUser();

    /**
     * @description 根据id删除用户
     * @param id
     * @return boolean
     * @author zhn
     * @date 2022/6/13 16:52
     */
    boolean deleteUser(String id);

    /**
     * @description 更新用户信息
     * @param user
     * @return boolean
     * @author zhn
     * @date 2022/6/13 17:14
     */
    boolean updateInfo(User user);

    /**
     * @description 更改密码
     * @param id
     * @param old_password
     * @param password
     * @return boolean
     * @author zhn
     * @date 2022/6/13 18:55
     */
    boolean updatePwd(Integer id, String old_password, String password);
}
