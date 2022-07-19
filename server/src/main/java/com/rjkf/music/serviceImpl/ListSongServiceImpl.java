package com.rjkf.music.serviceImpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rjkf.music.mapper.ListSongMapper;
import com.rjkf.music.mapper.SongMapper;
import com.rjkf.music.pojo.ListSong;
import com.rjkf.music.pojo.Song;
import com.rjkf.music.service.ListSongService;
import com.rjkf.music.service.SongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
public class ListSongServiceImpl extends ServiceImpl<ListSongMapper, ListSong> implements ListSongService {

    @Autowired
    private ListSongMapper listSongMapper;
    @Autowired
    private SongService songService;

    /**
     * @description 给歌单添加歌曲
     * @param listSong
     * @return boolean
     * @author zhn
     * @date 2022/6/14 17:04
     */
    @Override
    public boolean add(ListSong listSong) {
        int res = listSongMapper.insert(listSong);
        return res == 0 ? false : true;
    }

    /**
     * @description 删除歌单里指定歌曲id的歌曲
     * @param songId
     * @param songListId
     * @return boolean
     * @author zhn
     * @date 2022/6/14 17:11
     */
    @Override
    public boolean deleteBySongId(Integer songId, Integer songListId) {
        int res = listSongMapper.delete(new QueryWrapper<ListSong>().eq("song_id",songId).eq("song_list_id",songListId));
        return res == 0 ? false : true;
    }

    /**
     * @description 返回指定id的listSong
     * @param id
     * @return com.rjkf.music.pojo.ListSong
     * @author zhn
     * @date 2022/6/14 17:19
     */
    @Override
    public ListSong selectById(Integer id) {
        ListSong listSong = listSongMapper.selectById(id);
        return listSong;
    }

    /**
     * @description 更新歌单里的歌曲信息
     * @param listSong
     * @return boolean
     * @author zhn
     * @date 2022/6/14 17:21
     */
    @Override
    public boolean updateInfo(ListSong listSong) {
        int res = listSongMapper.updateById(listSong);
        return res == 0 ? false : true;
    }

    /**
     * @description 返回歌单里指定歌单id的歌曲
     * @param songListId
     * @return boolean
     * @author zhn
     * @date 2022/6/19 14:16
     */
    @Override
    public List<ListSong> getBySongListId(String songListId) {
        List<ListSong> list = listSongMapper.selectList(new QueryWrapper<ListSong>().eq("song_list_id", songListId));
        return list;
    }

    /**
     * @description 判断歌单是否存在该歌曲
     * @param songId
     * @param songListId
     * @return boolean
     * @author zhn
     * @date 2022/6/24 10:37
     */
    @Override
    public boolean exists(Integer songId, Integer songListId) {
        ListSong listSong = listSongMapper.selectOne(new QueryWrapper<ListSong>().eq("song_id", songId).eq("song_list_id", songListId));
        return listSong == null ? false : true;
    }
}
