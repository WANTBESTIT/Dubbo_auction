package org.web.auction.service;

import org.web.auction.pojo.User;

public interface UserService {

    public User login(String username, String password);

    public User findUserByusername(String username);
}
