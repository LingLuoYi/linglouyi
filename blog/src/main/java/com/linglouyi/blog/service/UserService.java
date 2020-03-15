package com.linglouyi.blog.service;

import com.linglouyi.blog.config.InitConfig;
import com.linglouyi.blog.dao.UserDao;
import com.linglouyi.blog.entity.User;
import com.linglouyi.blog.model.JsonModel;
import com.linglouyi.blog.utils.OutputJsonUtils;
import com.linglouyi.tools.IdTool;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.Optional;

@Service
@Slf4j
public class UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private InitConfig initConfig;

    //注册(暂时不验证邮箱)
    public JsonModel registered(String email,String password){
        User user = new User();
        IdTool idTool = initConfig.idTool();
        user.setAccount(Long.parseLong(idTool.id()));
        user.setEmail(email);
        user.setPassword(DigestUtils.md5DigestAsHex(password.getBytes()));
        user.setRoles("user");
        user.setStatus(0);
        userDao.save(user);
        return OutputJsonUtils.success(user);
    }

    //注销

    //修改密码

    //找回密码

    //修改头像

    public Optional<User> findByEmail(String email){
        return userDao.findByEmail(email);
    }
}
