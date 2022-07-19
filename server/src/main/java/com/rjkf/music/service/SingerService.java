package com.rjkf.music.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.rjkf.music.pojo.Singer;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zhn
 * @since 2022-06-13
 */
public interface SingerService extends IService<Singer> {

    /**
     * @description 添加歌手
     * @param singer
     * @return boolean
     * @author zhn
     * @date 2022/6/13 21:06
     */
    boolean addSinger(Singer singer);

    /**
     * @description 删除歌手
     * @param id
     * @return boolean
     * @author zhn
     * @date 2022/6/13 21:15
     */
    boolean deleteSinger(String id);

    /**
     * @description 返回所有歌手
     * @param
     * @return java.util.List<com.rjkf.music.pojo.Singer>
     * @author zhn
     * @date 2022/6/13 21:25
     */
    List<Singer> allSinger();

    /**
     * @description 通过id查找歌手
     * @param id
     * @return com.rjkf.music.pojo.Singer
     * @author zhn
     * @date 2022/6/14 16:00
     */
    Singer selectById(String id);

    /**
     * @description 根据名字查找歌手
     * @param name
     * @return java.util.List<com.rjkf.music.pojo.Singer>
     * @author zhn
     * @date 2022/6/13 21:32
     */
    List<Singer> selectByName(String name);

    /**
     * @description 根据性别查找歌手
     * @param sex
     * @return java.util.List<com.rjkf.music.pojo.Singer>
     * @author zhn
     * @date 2022/6/13 21:34
     */
    List<Singer> selectBySex(String sex);

    /** 
     * @description 更新歌手信息 
     * @param singer 
     * @return boolean 
     * @author zhn
     * @date 2022/6/13 21:51
     */ 
    boolean updateSingerInfo(Singer singer);

    /** 
     * @description 更新歌手头像
     * @param singer 
     * @return boolean 
     * @author zhn
     * @date 2022/6/13 22:01
     */ 
    boolean updatePic(Singer singer);
}
