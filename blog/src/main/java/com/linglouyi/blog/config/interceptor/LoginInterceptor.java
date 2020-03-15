package com.linglouyi.blog.config.interceptor;

import com.alibaba.fastjson.JSONObject;
import com.linglouyi.blog.model.TokenModel;
import com.linglouyi.blog.utils.LoginUtils;
import com.linglouyi.blog.utils.OutputJsonUtils;
import com.linglouyi.tools.TimeTools;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Date;
import java.util.Optional;

@Slf4j
public class LoginInterceptor implements HandlerInterceptor {


    @Value("${system.token}")
    private String tokenName = "x-token";


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader(this.tokenName);
        if (token == null || "".equals(token)){
            if ("yes".equals(request.getHeader("session-token"))){
                HttpSession session = request.getSession();
                token = String.valueOf(session.getAttribute(this.tokenName));
            }else {
                failure(request,response);
                return false;
            }
        }
        Optional<TokenModel> optionalTokenModel = LoginUtils.getToken(token);
        if (!optionalTokenModel.isPresent()){
            failure(request,response);
            LoginUtils.removeToken(token);
            return false;
        }
        TokenModel tokenModel = optionalTokenModel.get();
        //验证过期
        if (!TimeTools.timeOut(tokenModel.getLastTime(),tokenModel.getExp())){
            failure(request,response);
            LoginUtils.removeToken(token);
            return false;
        }
        response.addHeader(this.tokenName,tokenModel.getId());
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {
        //更新token
        Optional<TokenModel> optionalTokenModel = LoginUtils.getToken(request.getHeader(tokenName));
        if (optionalTokenModel.isPresent()){
            TokenModel tokenModel = optionalTokenModel.get();
            tokenModel.setLastTime(new Date());
            LoginUtils.seanToken(tokenModel);
        }else {
            optionalTokenModel = LoginUtils.getToken(String.valueOf(request.getSession().getAttribute(tokenName)));
            if (optionalTokenModel.isPresent()){
                TokenModel tokenModel = optionalTokenModel.get();
                tokenModel.setLastTime(new Date());
                LoginUtils.seanToken(tokenModel);
            }else {
                log.warn("更新token失败");
            }
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
    }

    private void failure(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setHeader("Content-type", "application/json;charset=UTF-8");
        response.setStatus(401);
        response.getWriter().write(JSONObject.toJSONString(OutputJsonUtils.error("没有登录")));
    }

}
