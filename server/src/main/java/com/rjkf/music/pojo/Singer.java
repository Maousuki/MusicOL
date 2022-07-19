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
@TableName("singer")
public class Singer implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @TableField("name")
    private String name;

    @TableField("sex")
    private Integer sex;

    @TableField("pic")
    private String pic;

    @TableField("birth")
    private LocalDate birth;

    @TableField("location")
    private String location;

    @TableField("introduction")
    private String introduction;


}
