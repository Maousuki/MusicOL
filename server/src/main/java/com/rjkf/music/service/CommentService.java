package com.rjkf.music.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.rjkf.music.pojo.Comment;
import com.rjkf.music.vo.LikeVo;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zhn
 * @since 2022-06-13
 */
public interface CommentService extends IService<Comment> {

    /**
     * @description 提交评论
     * @param comment
     * @return boolean
     * @author zhn
     * @date 2022/6/15 9:25
     */
    boolean add(Comment comment);

    /**
     * @description 删除评论
     * @param id
     * @return boolean
     * @author zhn
     * @date 2022/6/15 9:29
     */
    boolean delete(Integer id);

    /**
     * @description 返回指定歌曲id的评论
     * @param song_id
     * @return java.util.List<com.rjkf.music.pojo.Comment>
     * @author zhn
     * @date 2022/6/15 9:34
     */
    List<Comment> selectBySongId(Integer song_id);

    /**
     * @description 返回指定歌单id的评论
     * @param song_list_id
     * @return java.util.List<com.rjkf.music.pojo.Comment>
     * @author zhn
     * @date 2022/6/15 9:37
     */
    List<Comment> selectBySongListId(Integer song_list_id);

    /**
     * @description 点赞
     * @param likeVo
     * @return java.lang.Integer 点赞状态
     * @author zhn
     * @date 2022/6/15 19:44
     */
    Integer doLike(LikeVo likeVo);

    /**
     * @description 返回点赞数量
     * @param likeVo
     * @return java.lang.Long
     * @author zhn
     * @date 2022/6/15 19:52
     */
    Long likeCount(LikeVo likeVo);

    /**
     * @description 更新数据库点赞数量
     * @param id
     * @param likeCount
     * @return boolean
     * @author zhn
     * @date 2022/6/15 20:44
     */
    boolean updateLikeCount(Integer id, Long likeCount);

    /**
     * @description 当前用户的点赞状态
     * @param likeVo
     * @return java.lang.Integer 点赞状态 0:未点赞 1:已点赞
     * @author zhn
     * @date 2022/6/21 22:24
     */
    Integer likeStatus(LikeVo likeVo);
}
