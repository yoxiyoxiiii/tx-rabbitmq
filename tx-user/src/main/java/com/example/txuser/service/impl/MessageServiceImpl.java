package com.example.txuser.service.impl;

import com.example.txuser.dao.MessageDao;
import com.example.txuser.entity.Message;
import com.example.txuser.service.MessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 84681
 * 消息落库
 */
@Slf4j
@Service
public class MessageServiceImpl implements MessageService {

    @Autowired
    private MessageDao messageDao;

    @Override
    public Message save(Message message) {
        return messageDao.save(message);
    }

    /**
     * msgId 消息队列中的Id
     * @param msgId
     * @return
     */
    @Override
    public Message findById(String msgId) {
        return messageDao.findByMsgId(msgId);
    }

    @Override
    public List<Message> findByStatus(int status) {
        return messageDao.findByStatus(status);
    }
}
