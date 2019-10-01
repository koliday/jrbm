package com.jrsportsgame.jrbm.service.impl;

import com.jrsportsgame.jrbm.entity.UserAuthEntity;
import com.jrsportsgame.jrbm.entity.UserEntity;
import com.jrsportsgame.jrbm.entity.WXUserEntity;
import com.jrsportsgame.jrbm.mapper.UserAuthMapper;
import com.jrsportsgame.jrbm.mapper.UserMapper;
import com.jrsportsgame.jrbm.service.intf.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginServiceImpl implements LoginService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserAuthMapper userAuthMapper;
    @Override
    public Long loginByUsername(String username, String password){
        //首先检查用户名是否存在，不存在则返回-1
        UserEntity user=userMapper.getUserByUsername(username);
        if(user==null)
            return -1L;
        //检查密码是否正确，不正确返回-2
        if(!password.equals(user.getPassword()))
            return -2L;
        Long userId=user.getUserId();
        //如果用户名和密码正确，返回用户uid
        return userId;
    }

    /**
     * 微信登录
     * @param wxUser
     * @return
     */
    @Override
    public Long loginByWeixin(WXUserEntity wxUser) {
        UserAuthEntity user = userAuthMapper.getWXUserByOpenId(wxUser.getOpenid());
        if(user==null){
            return -1L;
        }
        return user.getUserId();
    }
}
