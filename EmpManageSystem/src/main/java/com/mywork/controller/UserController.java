package com.mywork.controller;

import com.mywork.pojo.Result;
import com.mywork.service.UserService;
import com.mywork.pojo.User;
import com.mywork.utils.JwtUtil;
import com.mywork.utils.ThreadLocalUtil;
import jakarta.validation.constraints.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/user")
@Validated
public class UserController {
    @Autowired
    private UserService userService;


    @PostMapping("/register")
    public Result register(@Pattern(regexp="^\\S{2,10}$") String username,@Pattern(regexp="^\\S{6,16}$") String password){
        User u= userService.findByUserName(username);

        if(u==null) {
            //没有占用
            //注册
            userService.register(username, password);
            return Result.success();
        }else {
            return Result.error("用户名已被占用");
        }
    }
    @PostMapping("/login")
    public Result login(@Pattern(regexp = "^\\S{2,10}$") String username,@Pattern(regexp="^\\S{6,16}$") String password){
        //查询用户是否存在
        User LoginUser= userService.findByUserName(username);
        if(LoginUser==null) {
            return Result.error("用户名不存在");
        }
        //判断密码是否正确
        if(LoginUser.getPassword().equals(password)) {
            //登录成功
            Map<String,Object> claims = new HashMap<>();
            claims.put("id",LoginUser.getId());
            claims.put("username",LoginUser.getUsername());
            String token= JwtUtil.genToken(claims);
            return Result.success(token);
        }
        return Result.error("密码错误");
    }
    @GetMapping("/userInfo")
    public Result<User> userInfo(/*@RequestHeader(name = "Authorization") String token*/){

        Map<String,Object> map = ThreadLocalUtil.get();
        String username=(String) map.get("username");
        User u=userService.findByUserName(username);

        return Result.success(u);
    }

    /*
    @PutMapping("/update")
    public Result update(@RequestBody User user){

        userService.update(user);
        return Result.success();

    }*/
    @PatchMapping("/updatePwd")
    public Result updatePwd(@RequestBody Map<String,String> map){
        String oldPwd=map.get("oldPwd");
        String newPwd=map.get("newPwd");
        String renewPwd=map.get("renewPwd");



        Map<String,Object> map1=ThreadLocalUtil.get();
        String username=(String) map1.get("username");
        String password=userService.findByUserName(username).getPassword();


        if(!StringUtils.hasLength(oldPwd)||!StringUtils.hasLength(newPwd)||!StringUtils.hasLength(renewPwd)) {
            return Result.error("输入不能为空！");
        }
        if(!oldPwd.equals(password)) {
            return Result.error("输入原密码错误！你到底是谁！");
        }
        if(newPwd.equals(password)) {
            return Result.error("新密码不能和原密码相同！");
        }

        if(!newPwd.equals(renewPwd)) {
            return Result.error("两次输入的密码不相同！");
        }
        //return Result.error("oldPwd:"+oldPwd);

        userService.updatePwd(newPwd);


        return Result.success("修改密码成功");
    }

}
