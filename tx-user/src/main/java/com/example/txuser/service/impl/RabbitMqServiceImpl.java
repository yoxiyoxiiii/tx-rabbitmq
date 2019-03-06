//package com.example.txuser.service.impl;
//
//import com.example.txuser.service.RabbitMqService;
//import org.springframework.amqp.core.Message;
//import org.springframework.amqp.rabbit.connection.CorrelationData;
//import org.springframework.amqp.rabbit.core.RabbitTemplate;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.lang.Nullable;
//import org.springframework.stereotype.Service;
//
//import javax.annotation.PostConstruct;
//
//@Service
//public class RabbitMqServiceImpl implements RabbitMqService {
//
//
//    @Autowired
//    private RabbitTemplate rabbitTemplate;
//
//    /**
//     * 设置回调
//     */
//    @PostConstruct
//    public void init() {
//        //只确认消息是否正确到达 Exchange 中
//        rabbitTemplate.setConfirmCallback(this);
//        //  消息没有正确到达队列时触发回调，如果正确到达队列不执行
//        rabbitTemplate.setReturnCallback(this);
//    }
//
//
//
//    /**
//     * 消息投递到exchange 由回调
//     * @param correlationData
//     * @param ack ack 确认消息是否投递到 exchange
//     * #消息投递确认confirms 模式
//       publisher-confirms: true
//     * @param cause
//     */
//    @Override
//    public void confirm(@Nullable CorrelationData correlationData, boolean ack, @Nullable String cause) {
//
//        //消息成功投递到 exchange ，落库，消息状态 发送成功
//        // 只确认消息是否正确到达 Exchange 中
//        if (ack) {
//            System.out.println("消息发送成功到exchange:" + correlationData);
//        } else {
//            //失败，落库，消息状态 ，发送失败！，定时任务重发
//            System.out.println("消息发送到exchange 失败！:" + correlationData);
//        }
//}
//
//
//    /**
//     *  消息没有正确到达队列时触发回调，如果正确到达队列不执行
//     * 如果exchange 没有 将消息成功投递到任何队列
//     * 该方法被回调
//     * # 消息投递失败 返回开启
//       publisher-returns: true
//     * @param message
//     * @param replyCode
//     * @param replyText
//     * @param exchange
//     * @param routingKey
//     */
//    @Override
//    public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
//        //消息没有投递到任何队列，消息待重发
//        System.out.println("exchange 投递失败的消息:" + new String(message.getBody()));
//    }
//
//
//    @Override
//    public void sendDirect(String msg) {
//        //routing key 不支持通配符，完全匹配
//        rabbitTemplate.convertAndSend("tx-user-direct","users",msg);
//    }
//
//}
