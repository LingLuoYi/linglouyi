package com.linglouyi.blog.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.linglouyi.blog.dao.DanMuDao;
import com.linglouyi.blog.entity.DanMu;
import com.linglouyi.tools.DanMuTool;
import com.linglouyi.tools.FileTool;
import com.linglouyi.tools.HttpTool;
import com.linglouyi.tools.XMLTool;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.math.BigInteger;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@Slf4j
public class DanMuService {

    @Autowired
    private DanMuDao danMuDao;


    public Map aiQiYi(String url) throws Exception {
        String res = HttpTool.doGet(url);
        String REGEX = "\"tvId\":(.+?),";
        Pattern p = Pattern.compile(REGEX);
        if (res == null)
            return null;
        Matcher m = p.matcher(res);
        if (m.find()) {
            Optional<DanMu> optionalDanMu = danMuDao.findById(m.group(1));
            if (!optionalDanMu.isPresent()) {
                List<String> strings = DanMuTool.getDanMu(m.group(1));
                List<Object> danMu = new ArrayList<>();
                for (String s : strings) {
                    JSONObject jsonObject = XMLTool.xmlToJson(s);
                    JSONObject data = jsonObject.getJSONObject("data");
                    JSONArray entry = data.getJSONArray("entry");
                    for (int i = 0; i < entry.size(); i++) {
                        JSONObject list = entry.getJSONObject(i);
                        JSONObject jsonObject1 = list.getJSONObject("list");
                        JSONArray bulletInfo = jsonObject1.getJSONArray("bulletInfo");
                        for (int j = 0; j < bulletInfo.size(); j++) {
                            JSONObject dan = bulletInfo.getJSONObject(j);
                            List<Object> list1 = new ArrayList<>();
                            list1.add(Double.parseDouble(dan.getString("showTime")));//展示时间，秒
                            list1.add(0);//滚动类型
                            list1.add(new BigInteger(dan.getString("color"), 16).intValue());//颜色，十进制
                            list1.add(dan.getJSONObject("userInfo").getString("name"));//发送人
                            list1.add(dan.getString("content"));//内容
                            danMu.add(list1);
                        }
                    }
                }
                //用文件的存下来
                String path = System.getProperty("user.dir")+ "/data/danMu/" + m.group(1);
                FileTool.contentToTxt(path, JSON.toJSONString(danMu));
                DanMu danMu1 = new DanMu();
                danMu1.setId(m.group(1));
                danMu1.setDanMuAddress(path);
                danMuDao.save(danMu1);
                return new HashMap<String, Object>() {{
                    put("code", 0);
                    put("data", danMu);
                }};
            }else {
                DanMu danMu = optionalDanMu.get();
                List list = JSON.parseArray(FileTool.TemplateLoad(new File(danMu.getDanMuAddress())));
                return new HashMap<String, Object>() {{
                    put("code", 0);
                    put("data", list);
                }};
            }
        }else {
            return new HashMap<String, Object>() {{
                put("code", 0);
                put("msg","tvId为空");
                put("data", null);
            }};
        }
    }
}
