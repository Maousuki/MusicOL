package com.rjkf.music.exception;

import com.rjkf.music.vo.RespBean;
import com.rjkf.music.vo.RespBeanEnum;
import org.apache.shiro.authz.AuthorizationException;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * @author zhn
 * @version 1.0
 * @description: TODO
 * @date 2022/6/15 16:04
 */
@RestControllerAdvice
public class MyExceptionHandler {

    @ExceptionHandler(value = AuthorizationException.class)
    public RespBean AuthHandler(AuthorizationException e) {
        String message = e.getMessage();
        String msg = message.substring(message.indexOf("[")+1,message.indexOf("]"));
        if (message.contains("role")) {
            RespBean error = RespBean.error(RespBeanEnum.ACCESS_DENIED);
            error.setMessage("没有管理员权限，无法访问");
            return error;
        } else if (message.contains("permission")) {
            RespBean error = RespBean.error(RespBeanEnum.ACCESS_DENIED);
            error.setMessage("没有" + msg + "权限，无法访问");
            return error;
        } else {
            RespBean error = RespBean.error(RespBeanEnum.ACCESS_DENIED);
            error.setMessage("权限有误");
            return error;
        }
    }
}
