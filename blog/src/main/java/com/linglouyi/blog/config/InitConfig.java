package com.linglouyi.blog.config;

import com.linglouyi.blog.model.TokenModel;
import com.linglouyi.tools.IdTool;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class InitConfig {

    @Value("${system.token}")
    private String token = "x-token";

    @Bean
    public IdTool idTool(){
        return new IdTool("yyMMddHHmmss");
    }

    //创建一个初始空间存登录用户
    @Bean
    public Map<String, TokenModel> loginUser(){
        return new HashMap<>();
    }

    @Bean
    public String tokenName(){
        return token;
    }
}
