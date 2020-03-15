package com.linglouyi.blog.controller;

import com.linglouyi.blog.service.DanMuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin(allowCredentials = "true", maxAge = 1800)
public class DanMuController {

    @Autowired
    private DanMuService danMuService;

    @RequestMapping("/danMu/aiQiYi/v3")
    public Map DPlayer(String id) throws Exception {
        return danMuService.aiQiYi(id);
    }
}
