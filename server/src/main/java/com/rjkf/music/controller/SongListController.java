package com.rjkf.music.controller;


import com.alibaba.fastjson.JSONObject;
import com.rjkf.music.pojo.SongList;
import com.rjkf.music.service.SongListService;
import com.rjkf.music.vo.RespBean;
import com.rjkf.music.vo.RespBeanEnum;
import com.rjkf.music.vo.SongListVo;
import com.rjkf.music.vo.UpdateSListVo;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
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
public class SongListController {

    @Autowired
    private SongListService songListService;


    /**
     * @description 添加歌单
     * @param songListVo
     * @return com.rjkf.music.vo.RespBean
     * @author zhn
     * @date 2022/6/14 16:07
     */
    @PostMapping("/songList/add")
    @RequiresRoles("1")
    public RespBean add(@RequestBody SongListVo songListVo) {
        String pic = "/img/songListPic/pic.jpg";

        SongList songList = new SongList();
        songList.setTitle(songListVo.getTitle());
        songList.setIntroduction(songListVo.getIntroduction());
        songList.setStyle(songListVo.getStyle());
        songList.setPic(pic);

        if (!songListService.add(songList)) {
            return RespBean.error(RespBeanEnum.ADD_ERROR);
        } else {
            return RespBean.success(songList);
        }
    }

    /**
     * @description 根据id删除歌单
     * @param id
     * @return com.rjkf.music.vo.RespBean
     * @author zhn
     * @date 2022/6/14 16:15
     */
    @DeleteMapping("/songList/delete")
    @RequiresRoles("1")
    public RespBean delete(@RequestParam String id) {
        if (!songListService.delete(Integer.parseInt(id))) {
            return RespBean.error(RespBeanEnum.DELETE_ERROR);
        } else {
            return RespBean.success();
        }
    }

    /**
     * @description 返回所有歌单
     * @param
     * @return com.rjkf.music.vo.RespBean
     * @author zhn
     * @date 2022/6/14 16:19
     */
    @GetMapping("/allSongList")
    public RespBean allSongList() {
        List<SongList> songLists = songListService.allSongList();
        return RespBean.success(songLists);
    }

    /**
     * @description 返回所给标题的歌单
     * @param title
     * @return com.rjkf.music.vo.RespBean
     * @author zhn
     * @date 2022/6/14 16:21
     */
    @GetMapping("/songListByTitle")
    public RespBean songListByTitle(@RequestParam String title) {
        List<SongList> songLists = songListService.selectByTitle(title);
        if (songLists.isEmpty()) {
            return RespBean.error(RespBeanEnum.SEARCH_NOT_EXIST);
        } else {
            return RespBean.success(songLists);
        }
    }

    /** 
     * @description 返回所给类型的歌单
     * @param style
     * @return com.rjkf.music.vo.RespBean 
     * @author zhn
     * @date 2022/6/14 16:25
     */ 
    @GetMapping("/songListByStyle")
    public RespBean songListByStyle(@RequestParam String style) {
        List<SongList> songLists = songListService.selectByStyle(style);
        return RespBean.success(songLists);
    }

    /**
     * @description 更新歌单信息
     * @param vo
     * @return com.rjkf.music.vo.RespBean
     * @author zhn
     * @date 2022/6/14 16:28
     */
    @PostMapping ("/songList/updateInfo")
    @RequiresRoles("1")
    public RespBean updateInfo(@RequestBody UpdateSListVo vo) {
        SongList songList = songListService.selectById(vo.getId());
        songList.setTitle(vo.getTitle());
        songList.setIntroduction(vo.getIntroduction());
        songList.setStyle(vo.getStyle());

        if (!songListService.updateInfo(songList)) {
            return RespBean.error(RespBeanEnum.UPDATE_INFO_ERROR);
        } else {
            return RespBean.success(songList);
        }
    }


    /**
     * @description 更新歌单封面
     * @param listFile
     * @param id
     * @return com.rjkf.music.vo.RespBean
     * @author zhn
     * @date 2022/6/14 16:37
     */
    @PostMapping("/songList/updatePic")
    @RequiresRoles("1")
    public RespBean updatePic(@RequestParam("file") MultipartFile listFile, @RequestParam("id") Integer id) {
        String fileName = System.currentTimeMillis() + listFile.getOriginalFilename();
        String filePath = System.getProperty("user.dir") + System.getProperty("file.separator") + "img" + System.getProperty("file.separator") + "songListPic";
        File file = new File(filePath);
        if (!file.exists()) {
            file.mkdirs();
        }

        File dest = new File(filePath + System.getProperty("file.separator") + fileName);
        String pic = "/img/songListPic/" + fileName;

        try {
            listFile.transferTo(dest);
            SongList songList = songListService.selectById(id);
            songList.setPic(pic);
            if (!songListService.updateInfo(songList)) {
                return RespBean.error(RespBeanEnum.UPDATE_AVATAR_ERROR);
            } else {
                return RespBean.success(songList);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return RespBean.error(RespBeanEnum.UPDATE_AVATAR_ERROR);
        }
    }
}
