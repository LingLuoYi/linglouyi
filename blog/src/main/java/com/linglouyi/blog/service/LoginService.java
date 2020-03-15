package com.linglouyi.blog.service;

import com.linglouyi.blog.entity.User;
import com.linglouyi.blog.model.JsonModel;
import com.linglouyi.blog.model.TokenModel;
import com.linglouyi.blog.utils.IPUtil;
import com.linglouyi.blog.utils.LoginUtils;
import com.linglouyi.blog.utils.OutputJsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.Optional;

@Service
public class LoginService {

    @Autowired
    private UserService userService;

    public JsonModel login(String email,String password) throws UnknownHostException {
        Optional<User> optionalUser = userService.findByEmail(email);
        if (!optionalUser.isPresent())
            return OutputJsonUtils.error("当前用户不存在");
        User user = optionalUser.get();
        if (!DigestUtils.md5DigestAsHex(password.getBytes()).equals(password))
            return OutputJsonUtils.error("密码不正确");
        TokenModel tokenModel = new TokenModel();
        tokenModel.setId(DigestUtils.md5DigestAsHex((email+System.currentTimeMillis()).getBytes()));
        tokenModel.setUserName(user.getEmail());
        tokenModel.setIss(InetAddress.getLocalHost().getHostName());
        tokenModel.setTime(new Date());
        tokenModel.setLoginIp(IPUtil.getRealIP());
        tokenModel.setExp(30 * 60 * 1000);
        tokenModel.setLastTime(new Date());
        tokenModel.setUser(user);
        LoginUtils.setLoginUser(tokenModel);
        return OutputJsonUtils.success("登录成功",tokenModel.getId());
    }
}
