package com.gec.dubbo.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.gec.dubbo.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Reference    // 当前是远程代理接口，意味着它是服务的消费者
    private UserService userService;

    @GetMapping("/getHello/{name}")
    public String getHello(@PathVariable String name){
        return userService.getHello(name);
    }
}
