package com.example.txuser.dao;

import com.example.txuser.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author 84681
 */
@Repository
public interface MessageDao extends JpaRepository<Message, String> {
    Message findByMsgId(String msgId);


    List<Message> findByStatus(int status);
}
