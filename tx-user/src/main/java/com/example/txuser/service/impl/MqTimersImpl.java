package com.example.txuser.service.impl;

import com.example.txuser.entity.Message;
import com.example.txuser.service.MessageService;
import com.example.txuser.service.MqTimers;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 84681
 */
@Slf4j
@Service
public class MqTimersImpl implements MqTimers {

    @Autowired
    private MessageService messageService;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    //一分钟执行一次
    @Scheduled(fixedRate = 60*1000)
    @Override
    public void sendMsgTimer() {
        //获取发送失败的消息集合，这里查询条数可以优化（如果发送失败的数据非常多，正常情况下不会很多）
        //最简单 就是 查询 消息状态
        //复杂一点 就是 消息发送方 提供  业务的可查询操作，结合消息状态来 判断 这条消息是否需要重发
        // 前提： 消息表中有这条消息：
        // 如 添加 user(id =1 ,name ="zhangs") -->作为消息体，拿到user 对象 去 user 表中获取这条记录,如果条记录存在，消息表中消息待发送
        //这时 就重发该条消息
        List<Message> messagedbList = messageService.findByStatus(1);
        messagedbList.forEach(message -> {
            log.error("message --> {}",message.getMsgId());
            CorrelationData correlationData = new CorrelationData();
            correlationData.setId(message.getMsgId());
            rabbitTemplate.convertAndSend("tx-user-direct","users",message.getMsg(), correlationData);
        });
    }
}
