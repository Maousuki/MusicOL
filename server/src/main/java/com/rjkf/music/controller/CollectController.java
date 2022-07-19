package com.rjkf.music.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.rjkf.music.pojo.Collect;
import com.rjkf.music.service.CollectService;
import com.rjkf.music.vo.CollectVo;
import com.rjkf.music.vo.RespBean;
import com.rjkf.music.vo.RespBeanEnum;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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
@RequestMapping("/collect")
public class CollectController {

    @Autowired
    private CollectService collectService;

    /**
     * @description 添加收藏
     * @param collectVo
     * @return com.rjkf.music.vo.RespBean
     * @author zhn
     * @date 2022/6/15 10:01
     */
    @PostMapping("/add")
    public RespBean add(@RequestBody CollectVo collectVo) {
        Collect collect = new Collect();
        collect.setUserId(collectVo.getUserId());
        collect.setCreateTime(LocalDateTime.now());
        Integer type = collectVo.getType();
        if (type == 0) {
            collect.setSongId(collectVo.getSongId());
        } else {
            collect.setSongListId(collectVo.getSongListId());
        }
        collect.setType(type);

        if (!collectService.add(collect)) {
            return RespBean.error(RespBeanEnum.ADD_ERROR);
        } else {
            return RespBean.success(collect);
        }
    }


    /**
     * @description 删除歌曲收藏
     * @param userId
     * @param songId
     * @return com.rjkf.music.vo.RespBean
     * @author zhn
     * @date 2022/6/15 10:11
     */
    @DeleteMapping("/delete")
    public RespBean delete(@RequestParam String userId, @RequestParam String songId) {
        if (!collectService.deleteSong(Integer.parseInt(userId),Integer.parseInt(songId))) {
            return RespBean.error(RespBeanEnum.DELETE_ERROR);
        } else {
            return RespBean.success();
        }
    }

    /**
     * @description 歌曲收藏状态
     * @param body
     * @return com.rjkf.music.vo.RespBean
     * @author zhn
     * @date 2022/6/16 9:59
     */
    @PostMapping("/songStatus")
    public RespBean isSongCollection(@RequestBody String body) {
        JSONObject jsonObject = JSONObject.parseObject(body);
        Integer userId = jsonObject.getInteger("userId");
        Integer songId = jsonObject.getInteger("songId");
        boolean status = collectService.existSong(userId, songId);
        return RespBean.success(status);
    }


    /**
     * @description 返回指定用户的歌曲收藏列表
     * @param userId
     * @return com.rjkf.music.vo.RespBean
     * @author zhn
     * @date 2022/6/16 10:03
     */
    @GetMapping("/songList")
    public RespBean collectList(@RequestParam String userId) {
        List<Collect> collects = collectService.collectSongList(Integer.parseInt(userId));
        return RespBean.success(collects);
    }

}
