package com.linglouyi.blog.utils;

import com.linglouyi.blog.model.JsonModel;

public class OutputJsonUtils {

    public static JsonModel success(){
        return success(null);
    }

    public static JsonModel success(Object o){
        return success("成功",o);
    }

    public static JsonModel success(String msg,Object o){
        JsonModel jsonModel = new JsonModel();
        jsonModel.setCode(0);
        jsonModel.setMsg(msg);
        jsonModel.setData(o);
        return jsonModel;
    }

    public static JsonModel error(String msg,Object o){
        JsonModel jsonModel = new JsonModel();
        jsonModel.setCode(1);
        jsonModel.setMsg(msg);
        jsonModel.setData(o);
        return jsonModel;
    }

    public static JsonModel error(String msg){
        return error(msg,null);
    }

    public static JsonModel error(){
        return error("失败");
    }
}
