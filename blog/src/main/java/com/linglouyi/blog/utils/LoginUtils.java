package com.linglouyi.blog.utils;

import com.linglouyi.blog.BlogApplication;
import com.linglouyi.blog.config.InitConfig;
import com.linglouyi.blog.entity.User;
import com.linglouyi.blog.model.TokenModel;
import com.linglouyi.tools.ClassTool;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.Optional;

@Slf4j
public class LoginUtils {

    private static String tokenName = BlogApplication.ac.getBean(InitConfig.class).tokenName();

    private static Map<String, TokenModel> loginUserMap = BlogApplication.ac.getBean(InitConfig.class).loginUser();

    public static Optional<User> getLoginUser() {
        Object object = RequestContextHolder.getRequestAttributes();
        if (object == null)
            return Optional.empty();
        HttpServletRequest request = ClassTool.change(object, ServletRequestAttributes.class).getRequest();
        //先取请求头中的token
        String toke = request.getHeader(tokenName);
        if (toke == null) {
            //头中没有则去session中取
            toke = String.valueOf(request.getSession().getAttribute(tokenName));
            if (toke == null)
                return Optional.empty();
        }
        if (!loginUserMap.containsKey(toke))
            return Optional.empty();
        return Optional.ofNullable(ClassTool.change(loginUserMap.get(toke).getUser(), User.class));
    }

    public static void setLoginUser(TokenModel tokenModel){
        loginUserMap.put(tokenModel.getId(),tokenModel);
        Object object = RequestContextHolder.getRequestAttributes();
        if (object == null)
            return;
        HttpServletRequest request = ClassTool.change(object, ServletRequestAttributes.class).getRequest();
        request.getSession().setAttribute(tokenName,tokenModel.getId());
    }

    public static Optional<TokenModel> getToken(String id){
        return Optional.ofNullable(loginUserMap.get(id));
    }

    public static void removeToken(String id){
        loginUserMap.remove(id);
    }

    public static void seanToken(TokenModel tokenModel){
        loginUserMap.put(tokenModel.getId(),tokenModel);
    }
}
