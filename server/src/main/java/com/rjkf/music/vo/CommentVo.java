package com.rjkf.music.vo;

import lombok.Data;

/**
 * @author zhn
 * @version 1.0
 * @description: TODO
 * @date 2022/6/15 9:16
 */
@Data
public class CommentVo {
    private Integer userId;
    private Integer songId;
    private Integer songListId;
    private String content;
    private Integer type;

}
