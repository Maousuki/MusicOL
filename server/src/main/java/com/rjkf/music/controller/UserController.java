package com.rjkf.music.controller;


import com.alibaba.fastjson.JSONObject;
import com.rjkf.music.constant.Constants;
import com.rjkf.music.pojo.User;
import com.rjkf.music.service.ShiroService;
import com.rjkf.music.service.UserService;
import com.rjkf.music.vo.*;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author zhn
 * @since 2022-06-13
 */
@RestController
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private ShiroService shiroService;

    DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    /**
     * @description 用户注册
     * @param registerVo
     * @return com.rjkf.music.vo.RespBean
     * @author zhn
     * @date 2022/6/13 15:38
     */
    @PostMapping ("/register")
    public RespBean register(@RequestBody RegisterVo registerVo) {
        String avatar = "/img/avatarImages/user.jpg";

        if (userService.existUser(registerVo.getUsername())) {
            return RespBean.error(RespBeanEnum.USER_EXISTS);
        }

        User user = new User();
        user.setUsername(registerVo.getUsername());
        user.setPassword(registerVo.getPassword());
        user.setSex(registerVo.getSex());
        user.setPhoneNum(registerVo.getPhoneNum());
        user.setEmail(registerVo.getEmail());
        if (registerVo.getBirth().equals("")) {
            user.setBirth(null);
        } else {
            LocalDate user_birth = LocalDate.parse(registerVo.getBirth(), format);
            user.setBirth(user_birth);
        }
        user.setIntroduction(registerVo.getIntroduction());
        user.setLocation(registerVo.getLocation());
        user.setAvatar(avatar);
        user.setCreateTime(LocalDateTime.now());
        user.setUpdateTime(LocalDateTime.now());
        user.setRole(0);

        if (userService.addUser(user)) {
            return RespBean.success(user);
        } else {
            return RespBean.error(RespBeanEnum.REGISTER_ERROR);
        }
    }

    /**
     * @description  登录
     * @param loginVo
     * @return com.rjkf.music.vo.RespBean
     * @author zhn
     * @date: 2022/6/13 16:05
     */
    @PostMapping("/login")
    public RespBean doLogin(@RequestBody LoginVo loginVo) {
        String username = loginVo.getUsername();
        String password = loginVo.getPassword();
        User user = userService.selectUserByName(username);
        if (user == null) {
            return RespBean.error(RespBeanEnum.USER_NOT_EXIST);
        }
        if (!userService.verify(username,password)) {
            return RespBean.error(RespBeanEnum.PASSWORD_WRONG);
        } else {
            String token = shiroService.createToken(user);
            Map<String,Object> map = new HashMap<>();
            map.put("token",token);
            map.put("user",user);
            return RespBean.success(map);
        }
    }

    /**
     * @description 登出
     * @param token
     * @return com.rjkf.music.vo.RespBean
     * @author zhn
     * @date 2022/6/15 15:15
     */
    @PostMapping("/user/logout")
    public RespBean logout(@RequestHeader("token") String token) {
        shiroService.logout(token);
        return RespBean.success();
    }

    /**
     * @description: 获取所有用户
     * @param
     * @return com.rjkf.music.vo.RespBean
     * @author zhn
     * @date 2022/6/13 16:40
     */
    @GetMapping("/user/allUser")
    @RequiresRoles("1")
    public RespBean allUser() {
        List<User> users = userService.allUser();
        return RespBean.success(users);
    }

    /**
     * @description 根据id查找用户
     * @param id
     * @return com.rjkf.music.vo.RespBean
     * @author zhn
     * @date 2022/6/14 11:17
     */
    @GetMapping("/userDetail")
    public RespBean userById(@RequestParam String id) {
        User user = userService.selectUserById(Integer.parseInt(id));
        if (user == null) {
            return RespBean.error(RespBeanEnum.USER_NOT_EXIST);
        } else {
            return RespBean.success(user);
        }
    }

    /**
     * @description 根据id删除用户
     * @param id
     * @return com.rjkf.music.vo.RespBean
     * @author zhn
     * @date 2022/6/13 16:57
     */
    @DeleteMapping("/user/delete")
    public RespBean delete(@RequestParam String id) {
        if (!userService.deleteUser(id)) {
            return RespBean.error(RespBeanEnum.DELETE_ERROR);
        } else {
            return RespBean.success();
        }
    }

    /**
     * @description 更新用户信息
     * @param updateInfoVo
     * @return com.rjkf.music.vo.RespBean
     * @author zhn
     * @date 2022/6/13 17:06
     */
    @PostMapping ("/user/updateInfo")
    public RespBean updateUserInfo(@RequestBody UpdateInfoVo updateInfoVo) {
        User user = userService.selectUserById(updateInfoVo.getId());
        if (user.getUsername().equals(updateInfoVo.getUsername())) {
            user.setUsername(updateInfoVo.getUsername());
            user.setSex(updateInfoVo.getSex());
            user.setPhoneNum(updateInfoVo.getPhoneNum());
            user.setEmail(updateInfoVo.getEmail());
            if (updateInfoVo.getBirth().equals("")) {
                user.setBirth(null);
            } else {
                LocalDate user_birth = LocalDate.parse(updateInfoVo.getBirth(), format);
                user.setBirth(user_birth);
            }
            user.setIntroduction(updateInfoVo.getIntroduction());
            user.setLocation(updateInfoVo.getLocation());
            user.setUpdateTime(LocalDateTime.now());

            if (!userService.updateInfo(user)) {
                return RespBean.error(RespBeanEnum.UPDATE_INFO_ERROR);
            } else {
                return RespBean.success(user);
            }
        } else if (userService.existUser(updateInfoVo.getUsername())){
            return RespBean.error(RespBeanEnum.USER_EXISTS);
        } else {
            return RespBean.error(RespBeanEnum.UPDATE_INFO_ERROR);
        }
    }

    /**
     * @description 更新密码
     * @param updatePwd
     * @return com.rjkf.music.vo.RespBean
     * @author zhn
     * @date 2022/6/13 19:03
     */
    @PostMapping("/user/updatePwd")
    public RespBean updatePwd(@RequestBody UpdatePwd updatePwd) {
        Integer id = updatePwd.getId();
        String username = updatePwd.getUsername();
        String oldPassword = updatePwd.getOldPassword();
        String password = updatePwd.getPassword();

        if (!userService.verify(username,oldPassword)) {
            return RespBean.error(RespBeanEnum.PASSWORD_WRONG);
        }

        if (!userService.updatePwd(id,oldPassword,password)) {
            return RespBean.error(RespBeanEnum.UPDATE_PWD_ERROR);
        } else {
            return RespBean.success();
        }
    }

    /**
     * @description 更新头像
     * @param avatarFile
     * @param id
     * @return com.rjkf.music.vo.RespBean
     * @author zhn
     * @date 2022/6/13 19:57
     */
    @PostMapping("/user/avatar")
    public RespBean updateAvatar(@RequestParam("file") MultipartFile avatarFile, @RequestParam("id") Integer id) {
        String fileName = System.currentTimeMillis() + avatarFile.getOriginalFilename();
        String filePath = Constants.PROJECT_PATH + System.getProperty("file.separator") + "img"
                + System.getProperty("file.separator") + "avatarImages";
        File file = new File(filePath);
        if (!file.exists()) {
            file.mkdirs();
        }
        File dest = new File(filePath + System.getProperty("file.separator") + fileName);
        String imgPath ="/img/avatarImages/" + fileName;

        try {
            avatarFile.transferTo(dest);
            User user = userService.selectUserById(id);
            user.setAvatar(imgPath);
            user.setUpdateTime(LocalDateTime.now());
            if (!userService.updateInfo(user)) {
                return RespBean.error(RespBeanEnum.UPDATE_AVATAR_ERROR);
            } else {
                return RespBean.success(imgPath);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return RespBean.error(RespBeanEnum.UPDATE_AVATAR_ERROR);
        }
    }
}
