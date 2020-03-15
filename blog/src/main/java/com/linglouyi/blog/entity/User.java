package com.linglouyi.blog.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
public class User {

    @Id
    private long account;//账号

    private String email;//邮箱

    private String nickname;//昵称

    private String password;//密码

    private String roles;//角色

    private String avatar;//头像

    private int status;//状态

    private int apiKey;//api密钥
}
