package com.jrsportsgame.jrbm.service.impl;

import com.jrsportsgame.jrbm.entity.UserEntity;
import com.jrsportsgame.jrbm.mapper.UserMapper;
import com.jrsportsgame.jrbm.service.intf.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginServiceImpl implements LoginService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public Long loginByUsername(String username, String password){
        //首先检查用户名是否存在，不存在则返回-1
        UserEntity user=userMapper.getUserByUsername(username);
        if(user==null)
            return -1l;
        //检查密码是否正确，不正确返回-2
        if(!password.equals(user.getPassword()))
            return -2l;
        Long userId=user.getUserId();
        //如果用户名和密码正确，返回用户uid
        return userId;
    }

}
