package com.rjkf.music.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * <p>
 * 
 * </p>
 *
 * @author zhn
 * @since 2022-06-13
 */
@Getter
@Setter
@TableName("user")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @TableField("username")
    private String username;

    @TableField("password")
    private String password;

    /**
     * 0：女 1：男
     */
    @TableField("sex")
    private Integer sex;

    @TableField("phone_num")
    private String phoneNum;

    @TableField("email")
    private String email;

    @TableField("birth")
    private LocalDate birth;

    @TableField("introduction")
    private String introduction;

    @TableField("location")
    private String location;

    @TableField("avatar")
    private String avatar;

    @TableField("create_time")
    private LocalDateTime createTime;

    @TableField("update_time")
    private LocalDateTime updateTime;

    /**
     * 0：普通用户 1：管理员
     */
    @TableField("role")
    private Integer role;


}
