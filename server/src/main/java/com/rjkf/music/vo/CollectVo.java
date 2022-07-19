package com.rjkf.music.vo;

import lombok.Data;

/**
 * @author zhn
 * @version 1.0
 * @description: TODO
 * @date 2022/6/15 10:02
 */
@Data
public class CollectVo {
    private Integer userId;
    private Integer songId;
    private Integer songListId;
    private Integer type;
}
