package com.rjkf.music.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.rjkf.music.pojo.SongList;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zhn
 * @since 2022-06-13
 */
public interface SongListService extends IService<SongList> {

    /**
     * @description 添加歌单
     * @param songList
     * @return boolean
     * @author zhn
     * @date 2022/6/14 16:12
     */
    boolean add(SongList songList);

    /**
     * @description 根据id删除歌单
     * @param id
     * @return boolean
     * @author zhn
     * @date 2022/6/14 16:17
     */
    boolean delete(Integer id);

    /**
     * @description 返回所有歌单信息
     * @param
     * @return java.util.List<com.rjkf.music.pojo.SongList>
     * @author zhn
     * @date 2022/6/14 16:19
     */
    List<SongList> allSongList();

    /**
     * @description 返回所给标题的歌单
     * @param title
     * @return java.util.List<com.rjkf.music.pojo.SongList>
     * @author zhn
     * @date 2022/6/14 16:23
     */
    List<SongList> selectByTitle(String title);

    /**
     * @description 返回所给类型的歌单
     * @param style
     * @return java.util.List<com.rjkf.music.pojo.SongList>
     * @author zhn
     * @date 2022/6/14 16:26
     */
    List<SongList> selectByStyle(String style);

    /**
     * @description 返回指定id的歌单
     * @param id
     * @return com.rjkf.music.pojo.SongList
     * @author zhn
     * @date 2022/6/14 16:31
     */
    SongList selectById(int id);

    /**
     * @description 更新歌单信息
     * @param songList
     * @return boolean
     * @author zhn
     * @date 2022/6/14 16:33
     */
    boolean updateInfo(SongList songList);
}
