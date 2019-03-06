package com.example.txuser.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tx_message")
public class Message implements Serializable {

    @Id
    private String id;

    /**
     * 消息
     */
    private String msg;

    /**
     * 队列名
     */
    private String queueName;


    /**
     * 消息状态
     * 1 待发送
     * 2 已发送
     * 3 待消费
     * 4 已消费
     *
     * 2和3 可以合并成一种状态
     */
    private Integer status;

    /**
     * 是否为死信
     * 1 为死信
     * 2 正常消息
     */
    private Integer isDead;

    /**
     * 消息ID
     */
    private String msgId;

}
