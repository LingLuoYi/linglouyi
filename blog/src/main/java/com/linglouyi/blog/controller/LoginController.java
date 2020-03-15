package com.linglouyi.blog.controller;

import com.linglouyi.blog.model.JsonModel;
import com.linglouyi.blog.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.UnknownHostException;

@RestController
@CrossOrigin(allowCredentials = "true")
public class LoginController {

    @Autowired
    private LoginService loginService;

    @RequestMapping("/login")
    public JsonModel login(String email, String password) throws UnknownHostException {
        return loginService.login(email,password);
    }
}
