package com.linglouyi.blog.model;

import lombok.Data;

import java.util.Date;

/**
 * token模型
 */
@Data
public class TokenModel {

    private String id;//标识

    private String userName;//账号

    private String iss;//签发人

    private Date time;//签发时间

    private String loginIp;//登录ip

    private long exp;//过期时间

    private Date lastTime;//最后操作时间

    private Object user;//登录用户

}
