package com.rjkf.music.controller;


import com.alibaba.fastjson.JSONObject;
import com.rjkf.music.pojo.Song;
import com.rjkf.music.service.SongService;
import com.rjkf.music.vo.RespBean;
import com.rjkf.music.vo.RespBeanEnum;
import com.rjkf.music.vo.UpdateSongVo;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
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
public class SongController {

    @Autowired
    private SongService songService;

    /**
     * @description 添加歌曲
     * @param songFile
     * @param singerId
     * @param name
     * @param introduction
     * @param lyric
     * @return com.rjkf.music.vo.RespBean
     * @author zhn
     * @date 2022/6/14 10:59
     */
    @PostMapping("/song/add")
    @RequiresRoles("1")
    public RespBean addSong(@RequestParam("file") MultipartFile songFile, @RequestParam("singerId") String singerId,
                            @RequestParam("name") String name, @RequestParam("introduction") String introduction,
                            @RequestParam("lyric") String lyric) {

        String pic = "/img/songPic/song.jpg";

        String fileName = songFile.getOriginalFilename();
        String filePath = System.getProperty("user.dir") + System.getProperty("file.separator") + "songs";
        String url = "/songs/" + fileName;

        File file = new File(filePath);
        if (!file.exists()) {
            file.mkdirs();
        }

        File dest = new File(filePath + System.getProperty("file.separator") + fileName);

        try {
            songFile.transferTo(dest);
            Song song = new Song();
            song.setSingerId(Integer.parseInt(singerId));
            song.setName(name);
            song.setIntroduction(introduction);
            song.setLyric(lyric);
            song.setPic(pic);
            song.setCreateTime(LocalDateTime.now());
            song.setUpdateTime(LocalDateTime.now());
            song.setUrl(url);

            if (!songService.addSong(song)) {
                return RespBean.error(RespBeanEnum.ADD_ERROR);
            } else {
                return RespBean.success(song);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return RespBean.error(RespBeanEnum.ADD_ERROR);
        }
    }

    /**
     * @description 删除歌曲
     * @param id
     * @return com.rjkf.music.vo.RespBean
     * @author zhn
     * @date 2022/6/14 11:02
     */
    @DeleteMapping("/song/delete")
    @RequiresRoles("1")
    public RespBean deleteSong(@RequestParam String id) {
        Song song = songService.selectById(id);
        if (song == null) {
            return RespBean.error(RespBeanEnum.SONG_NOT_EXIST);
        }
        if (!songService.deleteSong(id)) {
            return RespBean.error(RespBeanEnum.DELETE_ERROR);
        } else {
            return RespBean.success();
        }
    }


    /**
     * @description 返回所有歌曲
     * @param
     * @return com.rjkf.music.vo.RespBean
     * @author zhn
     * @date 2022/6/14 11:06
     */
    @GetMapping("/allSong")
    public RespBean allSong() {
        List<Song> songs = songService.allSong();
        return RespBean.success(songs);
    }

    /**
     * @description 根据歌曲id查找歌曲
     * @param id
     * @return com.rjkf.music.vo.RespBean
     * @author zhn
     * @date 2022/6/14 11:20
     */
    @GetMapping("/songById")
    public RespBean songById(@RequestParam String id) {
        Song song = songService.selectById(id);
        if (song == null) {
            return RespBean.error(RespBeanEnum.SONG_NOT_EXIST);
        } else {
            return RespBean.success(song);
        }
    }

    /**
     * @description 返回指定歌手id的歌曲
     * @param singerId
     * @return com.rjkf.music.vo.RespBean
     * @author zhn
     * @date 2022/6/14 11:23
     */
    @GetMapping("/songBySingerId")
    public RespBean songBySingerId(@RequestParam String singerId) {
        List<Song> songs = songService.selectBySingerId(singerId);
        return RespBean.success(songs);
    }

    /**
     * @description 返回指定歌手名的歌曲
     * @param name
     * @return com.rjkf.music.vo.RespBean
     * @author zhn
     * @date 2022/6/14 11:37
     */
    @GetMapping("/songBySingerName")
    public RespBean songBySingerName(@RequestParam String name) {
        List<Song> songs = songService.selectBySingerName(name);
        if (songs.isEmpty()) {
            return RespBean.error(RespBeanEnum.SEARCH_NOT_EXIST);
        } else {
            return RespBean.success(songs);
        }
    }

    /**
     * @description 返回指定歌手名的歌曲
     * @param name
     * @return com.rjkf.music.vo.RespBean
     * @author zhn
     * @date 2022/6/21 15:12
     */
    @GetMapping("/songBySongName")
    public RespBean songBySongName(@RequestParam String name) {
        List<Song> songs = songService.selectBySongName(name);
        if (songs.isEmpty()) {
            return RespBean.error(RespBeanEnum.SEARCH_NOT_EXIST);
        } else {
            return RespBean.success(songs);
        }
    }

    /**
     * @description 更新歌曲信息
     * @param vo
     * @return com.rjkf.music.vo.RespBean
     * @author zhn
     * @date 2022/6/14 14:43
     */
    @PostMapping("/song/updateInfo")
    @RequiresRoles("1")
    public RespBean updateSongInfo(@RequestBody UpdateSongVo vo) {
        Song song = songService.selectById(vo.getId() + "");
        song.setSingerId(vo.getSingerId());
        song.setName(vo.getName());
        song.setIntroduction(vo.getIntroduction());
        song.setUpdateTime(LocalDateTime.now());
        song.setLyric(vo.getLyric());

        if (!songService.updateSongInfo(song)) {
            return RespBean.error(RespBeanEnum.UPDATE_INFO_ERROR);
        } else {
            return RespBean.success(song);
        }
    }

    /**
     * @description 更新歌曲封面
     * @param songPic
     * @param id
     * @return com.rjkf.music.vo.RespBean
     * @author zhn
     * @date 2022/6/14 14:51
     */
    @PostMapping("/song/updatePic")
    @RequiresRoles("1")
    public RespBean songPic(@RequestParam("file") MultipartFile songPic, @RequestParam("id") Integer id) {
        String fileName = System.currentTimeMillis() + songPic.getOriginalFilename();
        String filePath =System.getProperty("user.dir") + System.getProperty("file.separator")
                + "img" + System.getProperty("file.separator") + "songPic";

        File file = new File(filePath);
        if (!file.exists()) {
            file.mkdirs();
        }
        File dest = new File(filePath + System.getProperty("file.separator") + fileName);
        String pic = "/img/songPic/" + fileName;
        try {
            songPic.transferTo(dest);
            Song song = songService.selectById(id + "");
            song.setUpdateTime(LocalDateTime.now());
            song.setPic(pic);
            if (!songService.updateSongInfo(song)) {
                return RespBean.error(RespBeanEnum.UPDATE_AVATAR_ERROR);
            } else {
                return RespBean.success(song);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return RespBean.error(RespBeanEnum.UPDATE_AVATAR_ERROR);
        }
    }


    /**
     * @description 更新歌曲
     * @param songUrl
     * @param id
     * @return com.rjkf.music.vo.RespBean
     * @author zhn
     * @date 2022/6/14 15:07
     */
    @PostMapping("/song/updateUrl")
    @RequiresRoles("1")
    public RespBean updateSongUrl(@RequestParam("file") MultipartFile songUrl, @RequestParam("id") Integer id) {
        String fileName = songUrl.getOriginalFilename();
        String filePath = System.getProperty("user.dir") + System.getProperty("file.separator") + "songs";
        File file = new File(filePath);
        if (!file.exists()) {
            file.mkdirs();
        }

        File dest = new File(filePath + System.getProperty("file.separator") + fileName);
        String url = "/songs/" + fileName;

        try {
            songUrl.transferTo(dest);
            Song song = songService.selectById(id + "");
            song.setUpdateTime(LocalDateTime.now());
            song.setUrl(url);
            if (!songService.updateSongInfo(song)) {
                return RespBean.error(RespBeanEnum.UPDATE_URL_ERROR);
            } else {
                return RespBean.success(song);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return RespBean.error(RespBeanEnum.UPDATE_URL_ERROR);
        }
    }
}
