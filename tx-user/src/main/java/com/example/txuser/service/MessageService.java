package com.example.txuser.service;

import com.example.txuser.entity.Message;

import java.util.List;

public interface MessageService {

    /**
     * 保存消息
     * @param message
     * @return
     */
    Message save(Message message);

    Message findById(String id);

    List<Message> findByStatus(int i);
}
