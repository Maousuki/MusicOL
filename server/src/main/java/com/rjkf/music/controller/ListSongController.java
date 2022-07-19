package com.rjkf.music.controller;


import com.alibaba.fastjson.JSONObject;
import com.rjkf.music.pojo.ListSong;
import com.rjkf.music.pojo.Song;
import com.rjkf.music.service.ListSongService;
import com.rjkf.music.vo.ListSongVo;
import com.rjkf.music.vo.RespBean;
import com.rjkf.music.vo.RespBeanEnum;
import com.rjkf.music.vo.UpdateListSVo;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author zhn
 * @since 2022-06-13
 */
@RestController
public class ListSongController {

    @Autowired
    private ListSongService listSongService;

    /**
     * @description 给歌单添加歌曲
     * @param listSongVo
     * @return com.rjkf.music.vo.RespBean
     * @author zhn
     * @date 2022/6/14 17:00
     */
    @PostMapping("/listSong/add")
    @RequiresRoles("1")
    public RespBean add(@RequestBody ListSongVo listSongVo) {
        ListSong listSong = new ListSong();
        listSong.setSongId(listSongVo.getSongId());
        listSong.setSongListId(listSongVo.getSongListId());

        if (listSongService.exists(listSongVo.getSongId(),listSongVo.getSongListId())) {
            return RespBean.error(RespBeanEnum.SONG_EXIST);
        } else if (!listSongService.add(listSong)) {
            return RespBean.error(RespBeanEnum.ADD_ERROR);
        } else {
            return RespBean.success(listSong);
        }

    }
    
    /** 
     * @description 删除歌单里指定歌曲id的歌曲
     * @param songId
     * @param songListId
     * @return com.rjkf.music.vo.RespBean
     * @author zhn
     * @date 2022/6/14 17:07
     */ 
    @DeleteMapping("/listSong/delete")
    @RequiresRoles("1")
    public RespBean deleteBySongId(@RequestParam String songId, @RequestParam String songListId) {
        if (!listSongService.deleteBySongId(Integer.parseInt(songId),Integer.parseInt(songListId))) {
            return RespBean.error(RespBeanEnum.DELETE_ERROR);
        } else {
            return RespBean.success();
        }
    }

    /**
     * @description 返回歌单里指定歌单id的歌曲
     * @param songListId
     * @return com.rjkf.music.vo.RespBean
     * @author zhn
     * @date 2022/6/14 17:15
     */
    @GetMapping("/getBySongListId")
    public RespBean getBySongListId(@RequestParam String songListId) {
        List<ListSong> list = listSongService.getBySongListId(songListId);
        return RespBean.success(list);
    }

    /** 
     * @description 更新歌单里面的歌曲信息
     * @param vo
     * @return com.rjkf.music.vo.RespBean 
     * @author zhn
     * @date 2022/6/14 17:17
     */ 
    @PostMapping("/listSong/update")
    @RequiresRoles("1")
    public RespBean updateInfo(@RequestBody UpdateListSVo vo) {
        ListSong listSong = listSongService.selectById(vo.getId());
        listSong.setSongId(vo.getSongId());
        listSong.setSongListId(vo.getSongListId());

        if (!listSongService.updateInfo(listSong)) {
            return RespBean.error(RespBeanEnum.UPDATE_INFO_ERROR);
        } else {
            return RespBean.success(listSong);
        }
    }
}
