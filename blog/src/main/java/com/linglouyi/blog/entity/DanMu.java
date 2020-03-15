package com.linglouyi.blog.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Entity
public class DanMu {

    @Id
    private String id;

    private String danMuAddress;
}
