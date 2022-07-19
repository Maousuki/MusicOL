package com.rjkf.music.vo;

import lombok.Data;

/**
 * @author zhn
 * @version 1.0
 * @description: TODO
 * @date 2022/6/13 18:52
 */
@Data
public class UpdatePwd {
    private Integer id;
    private String username;
    private String oldPassword;
    private String password;
}
