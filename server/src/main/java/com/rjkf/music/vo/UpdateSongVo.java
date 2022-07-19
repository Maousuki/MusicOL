package com.rjkf.music.vo;

import lombok.Data;

/**
 * @author zhn
 * @version 1.0
 * @description: TODO
 * @date 2022/6/14 14:43
 */
@Data
public class UpdateSongVo {
    private Integer id;
    private Integer singerId;
    private String name;
    private String introduction;
    private String lyric;
}
