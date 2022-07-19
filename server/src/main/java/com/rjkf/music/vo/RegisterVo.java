package com.rjkf.music.vo;

import lombok.Data;

/**
 * @author zhn
 * @version 1.0
 * @description: TODO
 * @date 2022/6/13 10:38
 */
@Data
public class RegisterVo {
    private String username;
    private String password;
    private Integer sex;
    private String phoneNum;
    private String email;
    private String birth;
    private String introduction;
    private String location;
}
