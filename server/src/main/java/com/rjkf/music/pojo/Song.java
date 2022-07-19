package com.rjkf.music.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
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
@TableName("song")
public class Song implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @TableField("singer_id")
    private Integer singerId;

    @TableField("name")
    private String name;

    @TableField("introduction")
    private String introduction;

    /**
     * 发行时间
     */
    @TableField("create_time")
    private LocalDateTime createTime;

    @TableField("update_time")
    private LocalDateTime updateTime;

    @TableField("pic")
    private String pic;

    @TableField("lyric")
    private String lyric;

    @TableField("url")
    private String url;


}
