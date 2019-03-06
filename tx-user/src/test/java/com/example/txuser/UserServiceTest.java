package com.example.txuser;

import com.example.txuser.dao.UserDao;
import com.example.txuser.entity.User;
import com.example.txuser.service.UserService;
import org.hibernate.internal.SessionImpl;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

public class UserServiceTest extends TxUserApplicationTests {

    @Autowired
    private UserService userService;



    @Test
    public void addByMqTest() {
        userService.addUserAndRoleByMq();
    }

    @Test
    public void testTxMqSend() {
        for (int i = 0; i<1 ;i++) {
            String msgId = null;
                msgId = userService.tryCommitUser();
                userService.confirmUser(msgId);
                userService.cancelUser(msgId);
        }
    }

}
