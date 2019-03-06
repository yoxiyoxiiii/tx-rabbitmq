package com.example.txuser.service;

import org.springframework.amqp.rabbit.core.RabbitTemplate;

public interface RabbitMqService extends RabbitTemplate.ConfirmCallback,RabbitTemplate.ReturnCallback{


    void sendDirect(String msg);


}
