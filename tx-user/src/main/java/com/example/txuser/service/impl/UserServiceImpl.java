package com.example.txuser.service.impl;

import com.example.txuser.dao.UserDao;
import com.example.txuser.entity.Message;
import com.example.txuser.entity.User;
import com.example.txuser.service.IRoleService;
import com.example.txuser.service.MessageService;
import com.example.txuser.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.UUID;

@Slf4j
@Service
public class UserServiceImpl implements UserService, RabbitTemplate.ConfirmCallback, RabbitTemplate.ReturnCallback{

    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private UserDao userDao;

    @Autowired
    private IRoleService iRoleService;

    //一个 rabbitTemplate 只能注册 一次 ConfirmCallback 和 ReturnCallback
   @PostConstruct
    public void init(){
        rabbitTemplate.setConfirmCallback(this);
        rabbitTemplate.setReturnCallback(this);
    }


    /**
     * 服务直接 调用 会发生数据不一致的问题。
     * 使用 消息中间件 解耦
     *
     * @return
     */
    @Transactional
    @Override
    public String addUserAndRole() {
        String id = iRoleService.add("admin");
//        int x = 1/0;
        User user = userDao.save(User.builder().id(UUID.randomUUID().toString()).userName("zs").build());
        return user.getUserName();
    }


    @Autowired
    private AmqpTemplate amqpTemplate;

    /**
     * 使用 mq 解耦 直接使用mq 发送消息 会有分布式事务问题
     *
     * @return
     */
    @Override
    public String addUserAndRoleByMq() {
        String msg = "superAdmin";
        amqpTemplate.convertAndSend("tx-user", msg);
        User user = userDao.save(User.builder().id(UUID.randomUUID().toString()).userName("zsMq").build());
        return user.getUserName();
    }

    /**
     * tcc 提交事务第一阶段
     * try
     * @return
     */
    @Autowired
    private MessageService messageService;
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    @Override
    public String tryCommitUser() {
        String msg = "super-admin";
        Message build = Message
                .builder()
                .id(UUID.randomUUID().toString())
                .msg(msg)
                .msgId(UUID.randomUUID().toString())
                //消息待发送
                .status(1)
                .build();
        //消息和 业务数据 利用本地事务的特点 首先持久化
        //如果 消息 持久化 或者 业务 处理发生异常，本地事务会首先回滚，保证数据的一致性
        Message save = messageService.save(build);
        //保存业务数据（保存一个用户消息）
        User user = User.builder()
                .id(UUID.randomUUID().toString())
                .userName("张三")
                .build();
        this.userDao.save(user);
        return save.getMsgId();
    }

    /**
     * 进行消息投递
     * @return
     */
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    @Override
    public String confirmUser(String msgId) {
        Message messagedb = this.messageService.findById(msgId);
        CorrelationData correlationData = new CorrelationData();
        correlationData.setId(messagedb.getMsgId());
        //投递消息
        rabbitTemplate.convertAndSend("tx-user-direct","users",messagedb.getMsg(),correlationData);
        return null;
    }

    /**
     * 事务补偿
     * @param msgId
     * @return
     */
    @Override
    public String cancelUser(String msgId) {
        return null;
    }


    /**
     * 消息 投递exchange 时回调
     * @param correlationData
     * @param ack 是否成功投递到 exchange
     * @param cause
     */
    @Override
    public void confirm(@Nullable CorrelationData correlationData, boolean ack, @Nullable String cause) {
        Message messagedb = this.messageService.findById(correlationData.getId());
        if (ack) {
            //这里更新数据库中的消息状态 也可能发生异常，
            //如果mq 发送成功了，但是 修改数据库中的消息状态失败，如果有定时任务在重发消息，则可能导致消息的重发。
            //解决方案 ：提供可查询操作，如果该消息被消费者成功消费，这时的消息状态一定是 已消费，
            // 如果消息被重复投递，消费者使用 msgId 去消息消息表中查询消息状态，如果为已消费，则丢弃该消息。
            System.err.println("消息发送成功!");
            messagedb.setStatus(2);
            messageService.save(messagedb);
        }else {
            System.err.println("消息发送失败!");
            messagedb.setStatus(1);
            messageService.save(messagedb);
        }
    }

    /**
     * 消息没有正确 的 投递到 任何队列
     * @param message
     * @param replyCode
     * @param replyText
     * @param exchange
     * @param routingKey
     */
    @Override
    public void returnedMessage(org.springframework.amqp.core.Message message, int replyCode, String replyText, String exchange, String routingKey) {
        Message messagedb = this.messageService.findById(message.getMessageProperties().getMessageId());
            messagedb.setStatus(1);
            messageService.save(messagedb);
            //这里如果更新消息状态失败，也没关系，因为如果消息没有投递成功，消息状态也还是 待发送 1
        }
    }
