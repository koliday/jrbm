package com.jrsportsgame.jrbm.service.impl;

import com.jrsportsgame.jrbm.mapper.UserAuthorizeMapper;
import com.jrsportsgame.jrbm.mapper.UserMapper;
import com.jrsportsgame.jrbm.model.User;
import com.jrsportsgame.jrbm.model.UserAuthorize;
import com.jrsportsgame.jrbm.model.UserAuthorizeExample;
import com.jrsportsgame.jrbm.model.UserExample;
import com.jrsportsgame.jrbm.service.intf.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LoginServiceImpl implements LoginService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserAuthorizeMapper userAuthorizeMapper;
    @Autowired
    private RedisTemplate<Object,Object> redisTemplate;

    @Override
    public Integer loginByUsername(String username, String password){
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

    @Override
    public List<User> getAllUsers() {
        RedisSerializer redisKeySerializer=new StringRedisSerializer();
        RedisSerializer redisValueSerializer=new Jackson2JsonRedisSerializer(Object.class);
        redisTemplate.setKeySerializer(redisKeySerializer);
        redisTemplate.setValueSerializer(redisValueSerializer);
        List<User> users= (List<User>) redisTemplate.opsForValue().get("user");
        if(users==null){
            UserExample userExample=new UserExample();
            userExample.createCriteria();
            users= userMapper.selectByExample(userExample);
            redisTemplate.opsForValue().set("user", users);
        }
        return users;
    }
}
