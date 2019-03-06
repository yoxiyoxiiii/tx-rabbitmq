package com.example.txrole.service.impl;

import com.example.txrole.service.RoleMqService;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;

@Slf4j
@Service
public class RoleMqServiceImpl implements RoleMqService {
    @RabbitListener(queues = "directQueue")
    @RabbitHandler
    @Override
    public void addRole(String msg, Channel channel, Message message) throws IOException {
        System.out.println(msg);
        MessageProperties messageProperties = message.getMessageProperties();
        Map<String, Object> headers = messageProperties.getHeaders();
        //获取 msgId 队列中的消息唯一
        String msgId = (String) headers.get("spring_returned_message_correlation");
        //查询消息的状态
        //查询业务的状态
        //消费消息，处理业务逻辑
        //设置ack
        //如果消费消息异常，或者是在操作数据库 发生异常，消息状态不改变，或者 拒绝ack ，将消息转发到 死信队列。
        //否则 确认ack
        channel.basicAck(messageProperties.getDeliveryTag(), true);
    }
}
