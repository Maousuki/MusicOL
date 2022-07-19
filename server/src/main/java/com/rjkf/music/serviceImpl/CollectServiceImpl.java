package com.rjkf.music.serviceImpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rjkf.music.mapper.CollectMapper;
import com.rjkf.music.pojo.Collect;
import com.rjkf.music.service.CollectService;
import org.springframework.beans.factory.annotation.Autowired;
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
public class CollectServiceImpl extends ServiceImpl<CollectMapper, Collect> implements CollectService {

    @Autowired
    private CollectMapper collectMapper;

    /**
     * @description 添加收藏
     * @param collect
     * @return boolean
     * @author zhn
     * @date 2022/6/15 10:07
     */
    @Override
    public boolean add(Collect collect) {
        int res = collectMapper.insert(collect);
        return res == 0 ? false : true;
    }

    /**
     * @description 删除歌曲收藏
     * @param userId
     * @param songId
     * @return boolean
     * @author zhn
     * @date 2022/6/16 9:47
     */
    @Override
    public boolean deleteSong(Integer userId, Integer songId) {
        int res = collectMapper.delete(new QueryWrapper<Collect>().eq("user_id", userId).eq("song_id", songId));
        return res == 0 ? false : true;
    }

    /**
     * @description  是否收藏
     * @param userId
     * @param songId
     * @return boolean
     * @author zhn
     * @date 2022/6/16 9:52
     */
    @Override
    public boolean existSong(Integer userId, Integer songId) {
        Collect collect = collectMapper.selectOne(new QueryWrapper<Collect>().eq("user_id", userId).eq("song_id", songId));
        return collect == null ? false : true;
    }

    /**
     * @description 返回指定用户的收藏列表
     * @param userId
     * @return java.util.List<com.rjkf.music.pojo.Collect>
     * @author zhn
     * @date 2022/6/16 10:03
     */
    @Override
    public List<Collect> collectSongList(Integer userId) {
        List<Collect> collects = collectMapper.selectList(new QueryWrapper<Collect>().eq("user_id", userId).eq("type",0));
        return collects;
    }
}
