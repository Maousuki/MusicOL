package com.rjkf.music.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.rjkf.music.pojo.ListSong;
import com.rjkf.music.pojo.Song;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zhn
 * @since 2022-06-13
 */
public interface ListSongService extends IService<ListSong> {

    /**
     * @description 给歌单添加歌曲
     * @param listSong
     * @return boolean
     * @author zhn
     * @date 2022/6/14 17:03
     */
    boolean add(ListSong listSong);

    /**
     * @description 删除歌单里指定歌曲id的歌曲
     * @param songId
     * @param songListId
     * @return boolean
     * @author zhn
     * @date 2022/6/14 17:11
     */
    boolean deleteBySongId(Integer songId, Integer songListId);
    
    /** 
     * @description 返回指定id的listSong 
     * @param id 
     * @return com.rjkf.music.pojo.ListSong 
     * @author zhn
     * @date 2022/6/14 17:19
     */ 
    ListSong selectById(Integer id);

    /** 
     * @description 更新歌单里的歌曲信息
     * @param listSong 
     * @return boolean 
     * @author zhn
     * @date 2022/6/14 17:21
     */ 
    boolean updateInfo(ListSong listSong);

    /**
     * @description 返回歌单里指定歌单id的歌曲
     * @param songListId
     * @return boolean
     * @author zhn
     * @date 2022/6/19 14:15
     */
    List<ListSong> getBySongListId(String songListId);

    /**
     * @description 判断歌单是否存在该歌曲
     * @param songId
     * @param songListId
     * @return boolean
     * @author zhn
     * @date 2022/6/24 10:37
     */
    boolean exists(Integer songId, Integer songListId);
}
