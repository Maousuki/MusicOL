package com.rjkf.music.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.rjkf.music.pojo.Collect;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zhn
 * @since 2022-06-13
 */
public interface CollectService extends IService<Collect> {

    /**
     * @description 添加收藏
     * @param collect
     * @return boolean
     * @author zhn
     * @date 2022/6/15 10:07
     */
    boolean add(Collect collect);

    /**
     * @description 删除歌曲收藏
     * @param userId
     * @param songId
     * @return boolean
     * @author zhn
     * @date 2022/6/16 9:46
     */
    boolean deleteSong(Integer userId, Integer songId);

    /**
     * @description
     * @param userId
     * @param songId
     * @return boolean
     * @author zhn
     * @date 2022/6/16 9:51
     */
    boolean existSong(Integer userId, Integer songId);

    /**
     * @description 返回指定用户的收藏列表
     * @param userId
     * @return java.util.List<com.rjkf.music.pojo.Collect>
     * @author zhn
     * @date 2022/6/16 10:02
     */
    List<Collect> collectSongList(Integer userId);
}
