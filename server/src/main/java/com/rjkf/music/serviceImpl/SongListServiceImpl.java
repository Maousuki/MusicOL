package com.rjkf.music.serviceImpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rjkf.music.mapper.SongListMapper;
import com.rjkf.music.pojo.SongList;
import com.rjkf.music.service.SongListService;
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
public class SongListServiceImpl extends ServiceImpl<SongListMapper, SongList> implements SongListService {

    @Autowired
    private SongListMapper songListMapper;

    /**
     * @description 添加歌单
     * @param songList
     * @return boolean
     * @author zhn
     * @date 2022/6/14 16:12
     */
    @Override
    public boolean add(SongList songList) {
        int res = songListMapper.insert(songList);
        return res == 0 ? false : true;
    }

    /**
     * @description 根据id删除歌单
     * @param id
     * @return boolean
     * @author zhn
     * @date 2022/6/14 16:17
     */
    @Override
    public boolean delete(Integer id) {
        int res = songListMapper.deleteById(id);
        return res == 0 ? false : true;
    }

    /**
     * @description 返回所有歌单
     * @param
     * @return java.util.List<com.rjkf.music.pojo.SongList>
     * @author zhn
     * @date 2022/6/14 16:20
     */
    @Override
    public List<SongList> allSongList() {
        List<SongList> songLists = songListMapper.selectList(new QueryWrapper<>());
        return songLists;
    }

    /**
     * @description 返回所给标题的歌单
     * @param title
     * @return java.util.List<com.rjkf.music.pojo.SongList>
     * @author zhn
     * @date 2022/6/14 16:23
     */
    @Override
    public List<SongList> selectByTitle(String title) {
        List<SongList> songLists = songListMapper.selectList(new QueryWrapper<SongList>().like("title", title));
        return songLists;
    }

    /**
     * @description 返回所给类型的歌单
     * @param style
     * @return java.util.List<com.rjkf.music.pojo.SongList>
     * @author zhn
     * @date 2022/6/14 16:26
     */
    @Override
    public List<SongList> selectByStyle(String style) {
        List<SongList> songLists = songListMapper.selectList(new QueryWrapper<SongList>().like("style", style));
        return songLists;
    }

    /**
     * @description 返回指定id的歌单
     * @param id
     * @return com.rjkf.music.pojo.SongList
     * @author zhn
     * @date 2022/6/14 16:31
     */
    @Override
    public SongList selectById(int id) {
        SongList songList = songListMapper.selectById(id);
        return songList;
    }

    /**
     * @description 更新歌单信息
     * @param songList
     * @return boolean
     * @author zhn
     * @date 2022/6/14 16:34
     */
    @Override
    public boolean updateInfo(SongList songList) {
        int res = songListMapper.updateById(songList);
        return res == 0 ? false : true;
    }
}
