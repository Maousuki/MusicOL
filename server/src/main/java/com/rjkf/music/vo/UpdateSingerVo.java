package com.rjkf.music.vo;

import lombok.Data;

/**
 * @author zhn
 * @version 1.0
 * @description: TODO
 * @date 2022/6/13 21:42
 */
@Data
public class UpdateSingerVo {
    private Integer id;
    private String name;
    private Integer sex;
    private String birth;
    private String location;
    private String introduction;
}
