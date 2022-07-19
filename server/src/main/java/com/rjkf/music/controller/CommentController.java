package com.rjkf.music.controller;


import com.alibaba.fastjson.JSONObject;
import com.rjkf.music.pojo.Comment;
import com.rjkf.music.pojo.LikeMessage;
import com.rjkf.music.rabbitmq.MQSender;
import com.rjkf.music.service.CommentService;
import com.rjkf.music.vo.CommentVo;
import com.rjkf.music.vo.LikeVo;
import com.rjkf.music.vo.RespBean;
import com.rjkf.music.vo.RespBeanEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author zhn
 * @since 2022-06-13
 */
@RestController
public class CommentController {

    @Autowired
    private CommentService commentService;
    @Autowired
    private MQSender mqSender;

    /**
     * @description 提交评论
     * @param commentVo
     * @return com.rjkf.music.vo.RespBean
     * @author zhn
     * @date 2022/6/15 9:15
     */
    @PostMapping("/comment/add")
    public RespBean add(@RequestBody CommentVo commentVo) {
        Comment comment = new Comment();
        comment.setUserId(commentVo.getUserId());
        comment.setCreateTime(LocalDateTime.now());
        comment.setContent(commentVo.getContent());
        Integer type = commentVo.getType();
        comment.setType(type);
        if (type == 0) {
            comment.setSongId(commentVo.getSongId());
        } else {
            comment.setSongListId(commentVo.getSongListId());
        }

        if (!commentService.add(comment)) {
            return RespBean.error(RespBeanEnum.ADD_ERROR);
        } else {
            return RespBean.success(comment);
        }

    }

    /**
     * @description 删除评论
     * @param id
     * @return com.rjkf.music.vo.RespBean
     * @author zhn
     * @date 2022/6/15 9:28
     */
    @DeleteMapping("/comment/delete")
    public RespBean delete(@RequestParam String id) {
        if (!commentService.delete(Integer.parseInt(id))) {
            return RespBean.error(RespBeanEnum.DELETE_ERROR);
        } else {
            return RespBean.success();
        }
    }

    /** 
     * @description 返回指定歌曲id的评论
     * @param songId
     * @return com.rjkf.music.vo.RespBean 
     * @author zhn
     * @date 2022/6/15 9:33
     */ 
    @GetMapping("/commentBySongId")
    public RespBean bySongId(@RequestParam String songId) {
        List<Comment> comments = commentService.selectBySongId(Integer.parseInt(songId));
        return RespBean.success(comments);
    }

    /**
     * @description 返回指定歌单id的评论
     * @param songListId
     * @return com.rjkf.music.vo.RespBean
     * @author zhn
     * @date 2022/6/15 9:37
     */
    @GetMapping("/commentBySongListId")
    public RespBean bySongListId(@RequestParam String songListId) {
        List<Comment> comments = commentService.selectBySongListId(Integer.parseInt(songListId));
        return RespBean.success(comments);
    }


    /**
     * @description 点赞
     * @param likeVo
     * @return com.rjkf.music.vo.RespBean
     * @author zhn
     * @date 2022/6/15 19:27
     */
    @PostMapping("/comment/like")
    public RespBean like(@RequestBody LikeVo likeVo) {
        Integer likeStatus = commentService.doLike(likeVo);
        Long likeCount = commentService.likeCount(likeVo);
        Map<String, Object> map = new HashMap<>();
        map.put("likeStatus",likeStatus);
        map.put("likeCount",likeCount);
        LikeMessage likeMessage = new LikeMessage();
        likeMessage.setCommentId(likeVo.getId());
        likeMessage.setLikeCount(likeCount);
        mqSender.sendMessage(JSONObject.toJSONString(likeMessage));
        return RespBean.success(map);
    }

    /**
     * @description 获取当前用户的点赞状态
     * @param likeVo
     * @return com.rjkf.music.vo.RespBean 点赞状态 0:未点赞 1:已点赞
     * @author zhn
     * @date 2022/6/21 22:19
     */
    @PostMapping("/comment/likeStatus")
    public RespBean likeStatus(@RequestBody LikeVo likeVo) {
        Integer status = commentService.likeStatus(likeVo);
        return RespBean.success(status);
    }
}
