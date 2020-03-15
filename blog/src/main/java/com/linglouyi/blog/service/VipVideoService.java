package com.linglouyi.blog.service;

import com.linglouyi.blog.model.JsonModel;
import com.linglouyi.blog.utils.OutputJsonUtils;
import com.linglouyi.tools.HttpTool;
import lombok.extern.slf4j.Slf4j;

/**
 * @Date 2020/2/11
 * @Created liyi
 */
@Slf4j
public class VipVideoService {

    public JsonModel videoUrl(String url){
        String data = HttpTool.doGet(url);
        log.info("{}",data);
        return OutputJsonUtils.error();
    }

}

