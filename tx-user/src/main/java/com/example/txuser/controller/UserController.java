package com.example.txuser.controller;

import com.example.txuser.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    private UserService userService;
    @PostMapping
    public String add() {
        String addUserAndRole = userService.addUserAndRole();
        return addUserAndRole;
    }
}
