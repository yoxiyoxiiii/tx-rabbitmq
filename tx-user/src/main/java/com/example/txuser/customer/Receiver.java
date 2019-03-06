//package com.example.txuser.customer;
//
//import com.rabbitmq.client.Channel;
//import org.springframework.amqp.core.Message;
//import org.springframework.amqp.core.ReceiveAndReplyCallback;
//import org.springframework.amqp.rabbit.annotation.RabbitHandler;
//import org.springframework.amqp.rabbit.annotation.RabbitListener;
//import org.springframework.amqp.rabbit.core.RabbitTemplate;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//import java.io.IOException;
//
//@Component
//public class Receiver {
//
//    @Autowired
//    private RabbitTemplate rabbitTemplate;
//
//    @RabbitListener(queues = "tx-user")
//    @RabbitHandler
//    public void receiver(String msg, Channel channel, Message message) throws IOException {
//        //业务处理，如果业务处理失败，消息标识为待发送，重发
//        System.out.println("Receiver = " + msg);
//        //手动确认消息 消费 ack，如果手动ack 成功，则标记 消息成功消费，
//        // 如果消息 已经被 业务系统处理掉，但是在手动 ack时，mq 发生异常，避免消息的重复消费，定时对消息表 进行业务查询，并手动ack
//        channel.basicAck(message.getMessageProperties().getDeliveryTag(), true);
//
//    }
//
//    @RabbitListener(queues = "topQueue")
//    @RabbitHandler
//    public void receiverTop(String msg , Channel channel, Message message) throws IOException {
//
//        System.out.println("top =" + msg);
//
//        //拒绝接受该消息，并 设置 false 标识 消息不再进入该队列，如果配置了死信队列，则进入死信队列
//        channel.basicReject(message.getMessageProperties().getDeliveryTag(), false);
//
//        //手动ack
////        channel.basicAck(message.getMessageProperties().getDeliveryTag(), true);
//    }
//
//
//    @RabbitListener(queues = "directQueue")
//    @RabbitHandler
//    public void receiveDirect(String msg, Channel channel, Message message) throws IOException {
//        System.err.println(msg);
////        channel.basicRecover();
////        int a =1/0;
////        channel.basicAck(message.getMessageProperties().getDeliveryTag(), true);
//        //拒绝接受该消息，并 设置 false 标识 消息不再进入该队列，如果配置了死信队列，则进入死信队列
//        channel.basicReject(message.getMessageProperties().getDeliveryTag(), false);
//    }
//
//    @RabbitListener(queues = "fanoutQueue")
//    @RabbitHandler
//    public void receiveFanout(String msg, Channel channel, Message message) throws IOException {
//        System.err.println(msg);
//
//        channel.basicAck(message.getMessageProperties().getDeliveryTag(), true);
//
//    }
//
//    @RabbitListener(queues = "dead-queue")
//    @RabbitHandler
//    public void receiveDeadQueue(String msg, Channel channel, Message message) {
//        System.err.println(msg+"dead");
//    }
//}
