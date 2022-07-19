package com.rjkf.music.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.ToString;

/**
 * @author zhn
 * @version 1.0
 * @description: 公共返回枚举类
 * @date 2022/6/13 10:35
 */
@ToString
@Getter
@AllArgsConstructor
public enum RespBeanEnum {
    //通用
    SUCCESS(200,"SUCCESS"),
    ERROR(500,"服务端异常"),
    //注册
    USER_EXISTS(500110,"用户名已存在"),
    REGISTER_ERROR(500120,"注册失败"),
    //登录
    USER_NOT_EXIST(500210,"用户不存在"),
    PASSWORD_WRONG(500220,"密码或用户名错误"),
    //更新信息
    ADD_ERROR(500310,"添加失败"),
    DELETE_ERROR(500320,"删除失败"),
    UPDATE_INFO_ERROR(500330,"更新信息失败"),
    UPDATE_PWD_ERROR(500340,"更新密码失败"),
    UPDATE_AVATAR_ERROR(500350,"更新图像失败"),
    UPDATE_URL_ERROR(500360,"更新歌曲失败"),
    //歌曲
    SONG_NOT_EXIST(500410,"歌曲不存在"),
    SONG_EXIST(500420,"歌曲已存在"),
    //权限
    ACCESS_DENIED(500510,""),
    //查找
    SEARCH_NOT_EXIST(500610,"查询为空");



    private final Integer code;
    private final String message;
}
