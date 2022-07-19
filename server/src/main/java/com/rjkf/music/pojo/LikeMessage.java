package com.rjkf.music.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author zhn
 * @version 1.0
 * @description: TODO
 * @date 2022/6/15 18:53
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LikeMessage {
    private Integer commentId;
    private Long likeCount;
}
