package com.example.txrole.controller;

import com.example.txrole.entity.Role;
import com.example.txrole.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
public class RoleController {

    @Autowired
    private RoleService roleService;
    @PostMapping
    public String add(@RequestParam(name = "roleName") String roleName) {
        Role role = Role.builder().id(UUID.randomUUID().toString()).roleName(roleName).build();
        String id = roleService.add(role);
        return id;
    }
}
