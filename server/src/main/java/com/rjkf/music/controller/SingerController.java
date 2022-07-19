package com.rjkf.music.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.rjkf.music.pojo.Singer;
import com.rjkf.music.service.SingerService;
import com.rjkf.music.vo.*;
import org.apache.ibatis.annotations.Delete;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.tomcat.util.http.fileupload.impl.FileSizeLimitExceededException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
public class SingerController {

    @Autowired
    private SingerService singerService;

    DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    /**
     * @description 添加歌手
     * @param singerVo
     * @return com.rjkf.music.vo.RespBean
     * @author zhn
     * @date 2022/6/13 21:12
     */
    @PostMapping("/singer/add")
    @RequiresRoles("1")
    public RespBean addSinger(@RequestBody SingerVo singerVo) {
        String pic = "/img/avatarImages/user.jpg";
        Singer singer = new Singer();
        singer.setName(singerVo.getName());
        singer.setSex(singerVo.getSex());
        if (singerVo.getBirth().equals("")) {
            singer.setBirth(null);
        } else {
            LocalDate birth = LocalDate.parse(singerVo.getBirth(), format);
            singer.setBirth(birth);
        }
        singer.setLocation(singerVo.getLocation());
        singer.setIntroduction(singerVo.getIntroduction());
        singer.setPic(pic);

        if (!singerService.addSinger(singer)) {
            return RespBean.error(RespBeanEnum.ADD_ERROR);
        } else {
            return RespBean.success(singer);
        }
    }

    /**
     * @description 删除歌手
     * @param id
     * @return com.rjkf.music.vo.RespBean
     * @author zhn
     * @date 2022/6/13 21:13
     */
    @DeleteMapping("/singer/delete")
    @RequiresRoles("1")
    public RespBean deleteSinger(@RequestParam String id) {
        if (!singerService.deleteSinger(id)) {
            return RespBean.error(RespBeanEnum.DELETE_ERROR);
        } else {
            return RespBean.success();
        }
    }

    /**
     * @description 获取所有歌手
     * @param
     * @return com.rjkf.music.vo.RespBean
     * @author zhn
     * @date 2022/6/13 21:24
     */
    @GetMapping("/allSinger")
    public RespBean allSinger() {
        List<Singer> singers = singerService.allSinger();
        return RespBean.success(singers);
    }

    /**
     * @description 根据名字查找歌手
     * @param name
     * @return com.rjkf.music.vo.RespBean
     * @author zhn
     * @date 2022/6/13 21:28
     */
    @GetMapping("/singerByName")
    public RespBean singerByName(@RequestParam String name) {
        List<Singer> singers = singerService.selectByName(name);
        return RespBean.success(singers);
    }

    /**
     * @description 根据性别查找歌手
     * @param sex
     * @return com.rjkf.music.vo.RespBean
     * @author zhn
     * @date 2022/6/13 21:34
     */
    @GetMapping("/singerBySex")
    public RespBean singerBySex(@RequestParam String sex) {
        List<Singer> singers = singerService.selectBySex(sex);
        return RespBean.success(singers);
    }


    /**
     * @description 更新歌手信息
     * @param vo
     * @return com.rjkf.music.vo.RespBean
     * @author zhn
     * @date 2022/6/13 21:37
     */
    @PostMapping("/singer/update")
    @RequiresRoles("1")
    public RespBean updateSingerInfo(@RequestBody UpdateSingerVo vo) {
        Singer singer = singerService.selectById(vo.getId() + "");
        singer.setName(vo.getName());
        singer.setSex(vo.getSex());
        singer.setLocation(vo.getLocation());
        singer.setIntroduction(vo.getIntroduction());
        if (vo.getBirth().equals("")) {
            singer.setBirth(null);
        } else {
            LocalDate birth = LocalDate.parse(vo.getBirth(), format);
            singer.setBirth(birth);
        }
        if (!singerService.updateSingerInfo(singer)) {
            return RespBean.error(RespBeanEnum.UPDATE_INFO_ERROR);
        } else {
            return RespBean.success(singer);
        }
    }


    /**
     * @description 更新歌手头像
     * @param avatarFile
     * @param id
     * @return com.rjkf.music.vo.RespBean
     * @author zhn
     * @date 2022/6/13 21:56
     */
    @PostMapping("/singer/avatar")
    @RequiresRoles("1")
    public RespBean updateSingerAvatar(@RequestParam("file") MultipartFile avatarFile, @RequestParam("id") String id) {
        String fileName = System.currentTimeMillis() + avatarFile.getOriginalFilename();
        String filePath = System.getProperty("user.dir") + System.getProperty("file.separator") + "img"
                + System.getProperty("file.separator") + "singerPic";
        File file = new File(filePath);
        if (!file.exists()) {
            file.mkdirs();
        }
        File dest = new File(filePath + System.getProperty("file.separator") + fileName);
        String imgPath = "/img/singerPic/" + fileName;
        try {
            avatarFile.transferTo(dest);
            Singer singer = singerService.selectById(id);
            singer.setPic(imgPath);

            if (!singerService.updatePic(singer)) {
                return RespBean.error(RespBeanEnum.UPDATE_AVATAR_ERROR);
            } else {
                return RespBean.success(imgPath);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return RespBean.error(RespBeanEnum.UPDATE_AVATAR_ERROR);
        }
    }
}
