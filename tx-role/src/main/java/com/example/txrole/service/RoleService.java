package com.example.txrole.service;

import com.example.txrole.entity.Role;

public interface RoleService {

    String add(Role role);

    void addRoleByMq(String msg);
}
