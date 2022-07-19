package com.rjkf.music.rabbitmq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author zhn
 * @version 1.0
 * @description: 生产者
 * @date 2022/6/15 18:52
 */
@Service
@Slf4j
public class MQSender {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * @description 发送消息
     * @param msg
     * @return void
     * @author zhn
     * @date 2022/6/15 20:39
     */
    public void sendMessage(String msg) {
//        log.info("发送消息:" + msg);
        rabbitTemplate.convertAndSend("musicExchange","music.message",msg);
    }
}
