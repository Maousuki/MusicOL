package com.rjkf.music.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author zhn
 * @version 1.0
 * @description: TODO
 * @date 2022/6/13 10:34
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RespBean {
    private long code;
    private String message;
    private String time;
    private long timestamp;
    private Object data;

    static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    /**
     * @description: 成功返回结果
     * @param:
     * @return: com.rjkf.music.vo.RespBean
     * @author zhn
     * @date: 2022/6/13 10:35
     */
    public static RespBean success() {
        return new RespBean(RespBeanEnum.SUCCESS.getCode(), RespBeanEnum.SUCCESS.getMessage(), LocalDateTime.now().format(formatter), System.currentTimeMillis(), null);
    }

    /**
     * @description: 成功返回结果
     * @param: obj
     * @return: com.rjkf.music.vo.RespBean
     * @author zhn
     * @date: 2022/6/13 10:35
     */
    public static RespBean success(Object obj) {
        return new RespBean(RespBeanEnum.SUCCESS.getCode(), RespBeanEnum.SUCCESS.getMessage(), LocalDateTime.now().format(formatter), System.currentTimeMillis(), obj);
    }

    /**
     * @description: 失败返回结果
     * @param: respBeanEnum
     * @return: com.rjkf.music.vo.RespBean
     * @author zhn
     * @date: 2022/6/13 10:35
     */
    public static RespBean error(RespBeanEnum respBeanEnum) {
        return new RespBean(respBeanEnum.getCode(),respBeanEnum.getMessage(), LocalDateTime.now().format(formatter), System.currentTimeMillis(), null);
    }

    /**
     * @description: 失败返回结果
     * @param: respBeanEnum
     * @return: com.rjkf.music.vo.RespBean
     * @author zhn
     * @date: 2022/6/13 10:35
     */
    public static RespBean error(RespBeanEnum respBeanEnum, Object obj) {
        return new RespBean(respBeanEnum.getCode(),respBeanEnum.getMessage(), LocalDateTime.now().format(formatter), System.currentTimeMillis(), obj);
    }

}
