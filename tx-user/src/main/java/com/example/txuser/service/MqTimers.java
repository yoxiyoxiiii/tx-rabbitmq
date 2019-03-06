package com.example.txuser.service;

/**
 * 定时处理发送失败的 消息
 */
public interface MqTimers {

    void sendMsgTimer();
}
