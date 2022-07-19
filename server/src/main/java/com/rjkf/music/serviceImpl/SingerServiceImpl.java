package com.rjkf.music.serviceImpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rjkf.music.mapper.SingerMapper;
import com.rjkf.music.pojo.Singer;
import com.rjkf.music.service.SingerService;
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
public class SingerServiceImpl extends ServiceImpl<SingerMapper, Singer> implements SingerService {

    @Autowired
    private SingerMapper singerMapper;

    /**
     * @description 添加歌手
     * @param singer
     * @return boolean
     * @author zhn
     * @date 2022/6/13 21:06
     */
    @Override
    public boolean addSinger(Singer singer) {
        int res = singerMapper.insert(singer);
        return res == 0 ? false : true;
    }

    /**
     * @description 删除歌手
     * @param id
     * @return boolean
     * @author zhn
     * @date 2022/6/13 21:15
     */
    @Override
    public boolean deleteSinger(String id) {
        Singer singer = singerMapper.selectById(id);
        if (singer == null) {
            return false;
        }
        int res = singerMapper.deleteById(id);
        return res == 0 ? false : true;
    }

    /**
     * @description 返回所有歌手
     * @param
     * @return java.util.List<com.rjkf.music.pojo.Singer>
     * @author zhn
     * @date 2022/6/13 21:25
     */
    @Override
    public List<Singer> allSinger() {
        List<Singer> singers = singerMapper.selectList(new QueryWrapper<>());
        return singers;
    }

    /**
     * @description 通过id查找歌手
     * @param id
     * @return com.rjkf.music.pojo.Singer
     * @author zhn
     * @date 2022/6/14 16:01
     */
    @Override
    public Singer selectById(String id) {
        Singer singer = singerMapper.selectById(id);
        return singer;
    }

    /**
     * @description 根据姓名查找歌手
     * @param name
     * @return java.util.List<com.rjkf.music.pojo.Singer>
     * @author zhn
     * @date 2022/6/13 21:32
     */
    @Override
    public List<Singer> selectByName(String name) {
        List<Singer> singers = singerMapper.selectList(new QueryWrapper<Singer>().like("name", name));
        return singers;
    }

    /**
     * @description 根据性别查找歌手
     * @param sex
     * @return java.util.List<com.rjkf.music.pojo.Singer>
     * @author zhn
     * @date 2022/6/13 21:35
     */
    @Override
    public List<Singer> selectBySex(String sex) {
        List<Singer> singers = singerMapper.selectList(new QueryWrapper<Singer>().eq("sex", sex));
        return singers;
    }

    /**
     * @description 更新歌手信息
     * @param singer
     * @return boolean
     * @author zhn
     * @date 2022/6/13 21:52
     */
    @Override
    public boolean updateSingerInfo(Singer singer) {
        int res = singerMapper.updateById(singer);
        return res == 0 ? false : true;
    }

    /**
     * @description 更新歌手头像
     * @param singer
     * @return boolean
     * @author zhn
     * @date 2022/6/13 22:01
     */
    @Override
    public boolean updatePic(Singer singer) {
        int res = singerMapper.updateById(singer);
        return res == 0 ? false : true;
    }
}
