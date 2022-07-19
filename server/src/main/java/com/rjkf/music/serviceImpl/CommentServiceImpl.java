package com.rjkf.music.serviceImpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rjkf.music.mapper.CommentMapper;
import com.rjkf.music.pojo.Comment;
import com.rjkf.music.service.CommentService;
import com.rjkf.music.vo.LikeVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zhn
 * @since 2022-06-13
 */
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {

    @Autowired
    private CommentMapper commentMapper;
    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * @description 提交评论
     * @param comment
     * @return boolean
     * @author zhn
     * @date 2022/6/15 9:26
     */
    @Override
    public boolean add(Comment comment) {
        int res = commentMapper.insert(comment);
        return res == 0 ? false : true;
    }

    /**
     * @description 删除评论
     * @param id
     * @return boolean
     * @author zhn
     * @date 2022/6/15 9:29
     */
    @Override
    public boolean delete(Integer id) {
        int res = commentMapper.deleteById(id);
        return res == 0 ? false : true;
    }

    /**
     * @description 返回指定歌曲id的评论
     * @param song_id
     * @return java.util.List<com.rjkf.music.pojo.Comment>
     * @author zhn
     * @date 2022/6/15 9:35
     */
    @Override
    public List<Comment> selectBySongId(Integer song_id) {
        List<Comment> comments = commentMapper.selectList(new QueryWrapper<Comment>().eq("song_id", song_id));
        return comments;
    }

    /**
     * @description 返回指定歌单id的评论
     * @param song_list_id
     * @return java.util.List<com.rjkf.music.pojo.Comment>
     * @author zhn
     * @date 2022/6/15 9:37
     */
    @Override
    public List<Comment> selectBySongListId(Integer song_list_id) {
        List<Comment> comments = commentMapper.selectList(new QueryWrapper<Comment>().eq("song_list_id", song_list_id));
        return comments;
    }

    /**
     * @description 点赞
     * @param likeVo
     * @return java.lang.Integer 点赞状态 0:未点赞 1:已点赞
     * @author zhn
     * @date 2022/6/15 19:45
     */
    @Override
    public Integer doLike(LikeVo likeVo) {
        String key = "like:" + likeVo.getType() + ":" + likeVo.getId();
        Boolean member = redisTemplate.opsForSet().isMember(key, likeVo.getUserId());
        if (member) {
            redisTemplate.opsForSet().remove(key,likeVo.getUserId());
        } else {
            redisTemplate.opsForSet().add(key,likeVo.getUserId());
        }
        return member ? 0 : 1;
    }

    /**
     * @description 获取点赞数量
     * @param likeVo
     * @return java.lang.Long
     * @author zhn
     * @date 2022/6/15 19:53
     */
    @Override
    public Long likeCount(LikeVo likeVo) {
        String key = "like:" + likeVo.getType() + ":" + likeVo.getId();
        return redisTemplate.opsForSet().size(key);
    }

    /**
     * @description 更新数据库点赞数量
     * @param id
     * @param likeCount
     * @return boolean
     * @author zhn
     * @date 2022/6/15 20:44
     */
    @Override
    public boolean updateLikeCount(Integer id, Long likeCount) {
        Comment comment = commentMapper.selectById(id);
        int res = commentMapper.update(comment, new UpdateWrapper<Comment>().eq("id",id).set("like_count", likeCount));
        return res == 0 ? false : true;
    }

    /**
     * @description 当前用户的点赞状态
     * @param likeVo
     * @return java.lang.Integer    点赞状态 0:未点赞 1:已点赞
     * @author zhn
     * @date 2022/6/21 22:24
     */
    @Override
    public Integer likeStatus(LikeVo likeVo) {
        String key = "like:" + likeVo.getType() + ":" + likeVo.getId();
        Boolean member = redisTemplate.opsForSet().isMember(key, likeVo.getUserId());
        return member ? 1 : 0;
    }
}
