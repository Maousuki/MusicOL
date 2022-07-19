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
@TableName("comment")
public class Comment implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @TableField("user_id")
    private Integer userId;

    @TableField("song_id")
    private Integer songId;

    @TableField("song_list_id")
    private Integer songListId;

    @TableField("content")
    private String content;

    @TableField("create_time")
    private LocalDateTime createTime;

    /**
     * 0：歌曲评论 1：歌单评论
     */
    @TableField("type")
    private Integer type;

    @TableField("like_count")
    private Long likeCount;


}
