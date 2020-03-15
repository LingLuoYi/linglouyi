package com.linglouyi.tools;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.dom4j.DocumentException;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.InflaterInputStream;

@Slf4j
public class DanMuTool {

    public static List<String> getDanMu(String tvId) {
        // 根据tvId获取电影或者电视剧的详细信息。
        String infoUrl = "http://mixer.video.iqiyi.com/jp/mixin/videos/" + tvId;
        String info = HttpTool.doGet(infoUrl);
        info = info.replace("var tvInfoJs=", "");
        JSONObject jsonObject = JSONObject.parseObject(info);
        // 电影或电视剧专辑
        String albumId = jsonObject.getString("albumId");
        // 电影或电视剧频道
        String channelId = jsonObject.getString("channelId");
        // 电影或电视剧时长
        int duration = jsonObject.getIntValue("duration");
        return getDanMu(tvId, albumId, channelId, duration);
    }

    private static List<String> getDanMu(String tvId, String albumId, String channelId, int duration) {
        // 弹幕下载地址
        String IQIYI_GET_DANMU_URL = "http://cmts.iqiyi.com/bullet/%s/%s/%s_300_%s.z?rn=%s&business=danmu&is_iqiyi=true&is_video_page=true&tvid=%s&albumid=%s&categoryid=%s&qypid=01010021010000000000";
        List<String> danmus = new ArrayList<String>();
        // 根据电影或电视剧的时长计算出需要多少个批量弹幕请求，爱奇艺平均5分钟请求一次加载弹幕
        int page = duration / (60 * 5) + 1;
        for (int i = 1; i <= page; i++) {
            try {
                String t = "0000" + tvId;
                int length = t.length();
                String first = t.substring(length - 4, length - 2);
                String second = t.substring(length - 2);
                String rn = "0." + RandomNumTool.randomNum(16);
                String danmuUrl = String.format(IQIYI_GET_DANMU_URL, first, second, tvId, i, rn, tvId, albumId,
                        channelId);
                String res = new String(decompress(Objects.requireNonNull(HttpTool.doGet(danmuUrl, null)).getContent()));
                danmus.add(res);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return danmus;
    }

    private static byte[] decompress(InputStream is) {
        InflaterInputStream iis = new InflaterInputStream(is);
        ByteArrayOutputStream o = new ByteArrayOutputStream(1024);
        try {
            int i = 1024;
            byte[] buf = new byte[i];
            while ((i = iis.read(buf, 0, i)) > 0) {
                o.write(buf, 0, i);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return o.toByteArray();
    }

}
