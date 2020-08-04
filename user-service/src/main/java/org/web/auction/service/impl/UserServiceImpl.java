package org.web.auction.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.web.auction.mapper.UserMapper;
import org.web.auction.pojo.User;
import org.web.auction.pojo.UserExample;
import org.web.auction.service.UserService;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public User login(String username, String password) {
        UserExample example = new UserExample();
        UserExample.Criteria criteria = example.createCriteria();

        //拼接username和password
        criteria.andUsernameEqualTo(username);
        criteria.andUserpasswordEqualTo(password);

        List<User> list = userMapper.selectByExample(example);
        if (list != null && list.size() > 0) {
            return list.get(0);
        }
        return null;
    }

    @Override
    public User findUserByusername(String username) {
        UserExample example = new UserExample();
        UserExample.Criteria criteria = example.createCriteria();

        //拼接username和password
        criteria.andUsernameEqualTo(username);
        List<User> list = userMapper.selectByExample(example);
        if (list != null && list.size() > 0) {
            return list.get(0);
        }
        return null;
    }
}
