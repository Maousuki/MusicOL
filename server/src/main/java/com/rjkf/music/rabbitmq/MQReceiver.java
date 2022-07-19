package com.rjkf.music.rabbitmq;

import com.alibaba.fastjson.JSONObject;
import com.rjkf.music.pojo.LikeMessage;
import com.rjkf.music.service.CommentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author zhn
 * @version 1.0
 * @description: 消费者
 * @date 2022/6/15 18:51
 */
@Service
@Slf4j
public class MQReceiver {

    @Autowired
    private CommentService commentService;

    @RabbitListener(queues = "musicQueue")
    public void receive(String msg) {
//        log.info("接收到的消息:" + msg);
        LikeMessage likeMessage = JSONObject.parseObject(msg, LikeMessage.class);
        Integer commentId = likeMessage.getCommentId();
        Long likeCount = likeMessage.getLikeCount();
        commentService.updateLikeCount(commentId,likeCount);
    }
}
