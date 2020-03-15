package com.linglouyi.blog.controller;

import com.linglouyi.blog.model.JsonModel;
import com.linglouyi.blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@CrossOrigin(allowCredentials = "true")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/registered")
    public JsonModel registered(@RequestParam("email") String email,@RequestParam("password") String password){
        return userService.registered(email,password);
    }
}
