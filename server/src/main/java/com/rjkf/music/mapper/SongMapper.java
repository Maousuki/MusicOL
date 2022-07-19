package com.rjkf.music.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.rjkf.music.pojo.Song;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author zhn
 * @since 2022-06-13
 */
@Mapper
public interface SongMapper extends BaseMapper<Song> {

}
