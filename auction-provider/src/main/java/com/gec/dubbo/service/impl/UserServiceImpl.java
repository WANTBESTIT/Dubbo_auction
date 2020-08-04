package com.gec.dubbo.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.gec.dubbo.service.UserService;

@Service
public class UserServiceImpl implements UserService {



    @Override
    public String getHello(String name) {
        return "hi" + name;
    }
}
