package com.example.txrole.service.impl;

import com.example.txrole.dao.RoleDao;
import com.example.txrole.entity.Role;
import com.example.txrole.service.RoleService;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

/**
 * @author 84681
 */
@Service
@RabbitListener(queues = "tx-user")
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleDao roleDao;

    @Transactional
    @Override
    public String add(Role role) {
        Role save = roleDao.save(role);
        return save.getId();
    }



    @RabbitHandler
    @Override
    public void addRoleByMq(String mag) {
        Role role = Role.builder().roleName(mag).id(UUID.randomUUID().toString()).build();
        Role save = this.roleDao.save(role);
    }
}
