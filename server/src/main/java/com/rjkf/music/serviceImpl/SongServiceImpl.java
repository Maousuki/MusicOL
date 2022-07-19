package com.rjkf.music.serviceImpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rjkf.music.mapper.SingerMapper;
import com.rjkf.music.mapper.SongMapper;
import com.rjkf.music.pojo.Singer;
import com.rjkf.music.pojo.Song;
import com.rjkf.music.service.SingerService;
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
public class SongServiceImpl extends ServiceImpl<SongMapper, Song> implements SongService {

    @Autowired
    private SongMapper songMapper;
    @Autowired
    private SingerService singerService;

    /**
     * @description 添加歌曲
     * @param song
     * @return boolean
     * @author zhn
     * @date 2022/6/14 10:31
     */
    @Override
    public boolean addSong(Song song) {
        int res = songMapper.insert(song);
        return res == 0 ? false : true;
    }

    /**
     * @description 删除歌曲
     * @param id
     * @return boolean
     * @author zhn
     * @date 2022/6/14 11:04
     */
    @Override
    public boolean deleteSong(String id) {
        int res = songMapper.deleteById(id);
        return res == 0 ? false : true;
    }

    /**
     * @description 返回所有歌曲
     * @param
     * @return java.util.List<com.rjkf.music.pojo.Song>
     * @author zhn
     * @date 2022/6/14 11:07
     */
    @Override
    public List<Song> allSong() {
        List<Song> songs = songMapper.selectList(new QueryWrapper<>());
        return songs;
    }

    /**
     * @description 根据歌曲id查找歌曲
     * @param id
     * @return com.rjkf.music.pojo.Song
     * @author zhn
     * @date 2022/6/14 11:19
     */
    @Override
    public Song selectById(String id) {
        return songMapper.selectById(id);
    }

    /**
     * @description 返回指定歌手id的歌曲
     * @param id
     * @return com.rjkf.music.pojo.Song
     * @author zhn
     * @date 2022/6/14 11:25
     */
    @Override
    public List<Song> selectBySingerId(String id) {
        List<Song> songs = songMapper.selectList(new QueryWrapper<Song>().eq("singer_id", id));
        return songs;
    }

    /**
     * @description 返回指定歌手名的歌曲
     * @param name
     * @return com.rjkf.music.pojo.Song
     * @author zhn
     * @date 2022/6/14 11:28
     */
    @Override
    public List<Song> selectBySingerName(String name) {
        List<Song> songs = new ArrayList<>();
        List<Singer> singers = singerService.selectByName(name);
        for (Singer singer : singers) {
            List<Song> singerSongs = selectBySingerId(singer.getId() + "");
            for (Song singerSong : singerSongs) {
                songs.add(singerSong);
            }
        }
        return songs;
    }

    /**
     * @description 更新歌曲信息
     * @param song
     * @return boolean
     * @author zhn
     * @date 2022/6/14 14:48
     */
    @Override
    public boolean updateSongInfo(Song song) {
        int res = songMapper.updateById(song);
        return res == 0 ? false : true;
    }

    /**
     * @description 返回指定歌曲名的歌曲
     * @param name
     * @return java.util.List<com.rjkf.music.pojo.Song>
     * @author zhn
     * @date 2022/6/21 15:12
     */
    @Override
    public List<Song> selectBySongName(String name) {
        List<Song> songs = songMapper.selectList(new QueryWrapper<Song>().like("name", name));
        return songs;
    }
}
