package com.example.txrole.service;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;

import java.io.IOException;

/**
 *
 * 分布式事务测试
 */
public interface RoleMqService {

    void addRole(String msg, Channel channel, Message message) throws IOException;
}
