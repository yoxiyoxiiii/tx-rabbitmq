package com.example.txuser.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


@Component
@FeignClient(value = "txrole")
public interface IRoleService {

    @RequestMapping(method = RequestMethod.POST)
    String add(@RequestParam(name = "roleName") String roleName);
}
