package com.mywork.service.impl;

import com.mywork.mapper.UserMapper;
import com.mywork.pojo.User;
import com.mywork.service.UserService;
import com.mywork.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

import java.util.Map;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public User findByUserName(String username){
        User u=userMapper.findByUserName(username);

        return u;
    }

    @Override
    public void register(String username,String password){

        userMapper.add(username,password);
    }
    @Override
    public void login(String username,String password){}

    @Override
    public void update(User user){
        user.setUpdateTime(LocalDateTime.now());
        userMapper.update(user);

    }
    public void updatePwd(String newPwd){
        Map<String,Object> map= ThreadLocalUtil.get();

        String id1=(String)map.get("id");
        Integer id=Integer.parseInt(id1);
        userMapper.updatePwd(newPwd,id);
    }
}
