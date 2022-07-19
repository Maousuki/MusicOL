package com.rjkf.music.service;

import com.baomidou.mybatisplus.extension.service.IService;
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
public interface SongService extends IService<Song> {

    /**
     * @description 添加歌曲
     * @param song
     * @return boolean
     * @author zhn
     * @date 2022/6/14 10:31
     */
    boolean addSong(Song song);

    /**
     * @description 删除歌曲
     * @param id
     * @return boolean
     * @author zhn
     * @date 2022/6/14 11:04
     */
    boolean deleteSong(String id);

    /**
     * @description 返回所有歌曲
     * @param
     * @return java.util.List<com.rjkf.music.pojo.Song>
     * @author zhn
     * @date 2022/6/14 11:07
     */
    List<Song> allSong();

    /**
     * @description 根据歌曲id查找歌曲
     * @param id
     * @return com.rjkf.music.pojo.Song
     * @author zhn
     * @date 2022/6/14 11:18
     */
    Song selectById(String id);

    /**
     * @description 返回指定歌手id的歌曲
     * @param id
     * @return com.rjkf.music.pojo.Song
     * @author zhn
     * @date 2022/6/14 11:24
     */
    List<Song> selectBySingerId(String id);

    /** 
     * @description 返回指定歌手名的歌曲
     * @param name 
     * @return com.rjkf.music.pojo.Song 
     * @author zhn
     * @date 2022/6/14 11:28
     */ 
    List<Song> selectBySingerName(String name);

    /**
     * @description 更新歌曲信息
     * @param song
     * @return boolean
     * @author zhn
     * @date 2022/6/14 14:48
     */
    boolean updateSongInfo(Song song);

    /**
     * @description 返回指定歌曲名的歌曲
     * @param name
     * @return java.util.List<com.rjkf.music.pojo.Song>
     * @author zhn
     * @date 2022/6/21 15:11
     */
    List<Song> selectBySongName(String name);
}
