package com.jrsportsgame.jrbm.service.intf;

import com.jrsportsgame.jrbm.model.User;

import java.util.List;

public interface LoginService {
    Integer loginByUsername(String username,String password);
    //测试redis
    List<User> getAllUsers();
}
