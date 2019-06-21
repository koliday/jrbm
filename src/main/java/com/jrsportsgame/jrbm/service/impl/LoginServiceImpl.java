package com.jrsportsgame.jrbm.service.impl;

import com.jrsportsgame.jrbm.mapper.UserAuthorizeMapper;
import com.jrsportsgame.jrbm.mapper.UserMapper;
import com.jrsportsgame.jrbm.model.User;
import com.jrsportsgame.jrbm.model.UserAuthorize;
import com.jrsportsgame.jrbm.model.UserAuthorizeExample;
import com.jrsportsgame.jrbm.model.UserExample;
import com.jrsportsgame.jrbm.service.intf.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LoginServiceImpl implements LoginService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserAuthorizeMapper userAuthorizeMapper;

    public Integer loginByUsername(String username,String password){
        //首先检查用户名是否存在，不存在则返回1
        UserExample userExample=new UserExample();
        userExample.createCriteria().andUsernameEqualTo(username);
        List<User> user = userMapper.selectByExample(userExample);
        if(user.isEmpty())
            return -1;
        //用户名存在，取出uid，到authorize中查询密码是否正确
        Integer uid=user.get(0).getUid();
        UserAuthorizeExample userAuthorizeExample=new UserAuthorizeExample();
        userAuthorizeExample.createCriteria().andPasswordEqualTo(password).andUidEqualTo(uid);
        List<UserAuthorize> userAuthorize=userAuthorizeMapper.selectByExample(userAuthorizeExample);
        if(userAuthorize.isEmpty())
            return -2;
        //如果用户名和密码正确，返回用户uid
        return userAuthorize.get(0).getUid();
    }
}
