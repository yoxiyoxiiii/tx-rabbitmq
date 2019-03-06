package com.example.txuser.service;

public interface UserService {

    String addUserAndRole();

    String addUserAndRoleByMq();


    /**
     *tcc 第一阶段
     * try 消息和 业务数据持久化
     * @return
     */
    String tryCommitUser();

    /**
     * tcc 第二阶段
     * confirm 确保消息成功投递到队列
     * @return
     */
    String confirmUser(String msgId);


    /**
     * tcc 第三阶段
     * cancel 消息投递失败，进行业务补偿或者标注消息状态为待发送
     * @return
     */
    String cancelUser(String msgId);


}
