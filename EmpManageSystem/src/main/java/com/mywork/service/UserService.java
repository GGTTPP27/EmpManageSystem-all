package com.mywork.service;

import com.mywork.pojo.User;

public interface UserService {


    //根据用户名查找用户
    User findByUserName(String username);
    //注册
    void register(String username,String password);

    void login(String username,String password);


    void update(User user);

    void updatePwd(String newPwd);
}
