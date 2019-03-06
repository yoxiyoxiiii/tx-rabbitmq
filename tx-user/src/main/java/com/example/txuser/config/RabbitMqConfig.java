package com.example.txuser.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class RabbitMqConfig {

    /**
     * 声明一个直连exchange
     * 队列和exchange 通过 routing key 直接绑定，不支持通配符
     * routing key 完全匹配
     * @return
     */
    @Bean
    public DirectExchange directExchange() {
        return new DirectExchange("tx-user-direct");
    }

    /**
     * 创建一个 exchange 作为 死信对列的交换机
     * 死信交换机
     * @return
     */
    @Bean
    public DirectExchange deadExchange() {
        return new DirectExchange("dead-exchange");
    }

    /**
     * 创建一个死信队列
     * 消息在什么时候 会变成死信（被mq server 丢弃）：
     * 1 被消费则 拒绝消费 消息被拒绝（basic.reject 或者 basic.nack），并且requeue=false;
     * 2 消息的过期时间到 （消息配置了过期时间），指消息 并没有 投递到 消费者，而是一直在队列中存储。
     * 3 队列长度超过限制，前面的消息就会被丢弃
     *  如果这时 该队列 配置了 死信 exchange ，并且 该 exchange 成功绑定队列，则该队列称为死信队列，
     *  存放 被 mq 丢弃的 消息，其实就是普通队列。
     *  如果 该队列 没有 绑定 死信exchange 或者 死信 exchange 没有队列与之绑定，则消息会被丢弃，无法找回。
     * @return
     */
    @Bean
    public Queue deadQueue() {
        return new Queue("dead-queue");
    }

    /**
     * 将死信队列和死信exchange 绑定
     * @return
     */
    @Bean
    public Binding deadBinding(Queue deadQueue,
                               DirectExchange deadExchange) {
       return BindingBuilder.bind(deadQueue).to(deadExchange).with("deadKey");
    }

    @Bean
    public Queue directQueue() {
        Map map = new HashMap();
        //为该队列 设置 死信交换机
        map.put("x-dead-letter-exchange","dead-exchange");
        map.put("x-dead-letter-routing-key","deadKey");
        return new Queue("directQueue",false,false,false,map);
    }

    @Bean
    public Binding directBinding(Queue directQueue,
                                 DirectExchange directExchange) {
        /**
         * 将队列绑定到 exchange ，并设置 routing key
         */
        return BindingBuilder
                .bind(directQueue)
                .to(directExchange)
                .with("users");
    }
}
