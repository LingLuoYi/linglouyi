package com.linglouyi.blog.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.linglouyi.tools.FileTool;
import com.linglouyi.tools.HttpTool;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Objects;

import static com.sun.javafx.tools.resource.DeployResource.Type.data;

/**
 * @Date 2020/2/19
 * @Created liyi
 */
@Slf4j
@Service
public class StockService {

    //获取列表
    public JSONArray stockList(){
        String data = null;
        JSONArray jsonArray = new JSONArray();
        int j = 2;
        for (int i = 1; i < j; i++) {
            String url  = "http://vip.stock.finance.sina.com.cn/quotes_service/api/json_v2.php/Market_Center.getHQNodeData?page="+i+"&num=100&sort=symbol&asc=1&node=hs_a&symbol=&_s_r_a=init";
            data = HttpTool.doGet(url);
            if (data != null && !"null".equals(data)) {
                log.debug("{}",data);
                j++;
                jsonArray.addAll(JSONArray.parseArray(data));
            }
        }
        return jsonArray;
    }


    //获取历史数据
    public void stockOld(List<String> list) throws Exception {
        String url = "";
        for (String code:list) {
            String key = code.substring(0,2);
            if ("sh".equals(key)){
                url = "http://quotes.money.163.com/service/chddata.html?code="+0+""+code.substring(2)+"&start=20120720&end=20191225&fields=TCLOSE;HIGH;LOW;TOPEN;PCHG;VOTURNOVER;VATURNOVER";
            }else {
                url = "http://quotes.money.163.com/service/chddata.html?code="+1+""+code.substring(2)+"&start=20120720&end=20191225&fields=TCLOSE;HIGH;LOW;TOPEN;PCHG;VOTURNOVER;VATURNOVER";
            }
            StringBuilder data = new StringBuilder();
            BufferedReader in = new BufferedReader(new InputStreamReader(Objects.requireNonNull(HttpTool.doGet(url, null)).getContent(),"gbk"));
            while ((in.readLine()) != null && !"null".equals(in.readLine())) {
                data.append(in.readLine());
            }
            String path = System.getProperty("user.dir")+ "/data/stock/" + code +".txt";
            FileTool.contentToTxt(path, data.toString());
            log.debug("{}",data);
        }
    }
}
