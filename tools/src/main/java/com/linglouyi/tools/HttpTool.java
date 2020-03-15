package com.linglouyi.tools;


import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Objects;
import java.util.zip.InflaterInputStream;

/**
 * http工具类
 */
@Slf4j
public class HttpTool {
    public static HttpEntity doGet(String url, Map<String, String> headers) {


        try {
            //发送get请求
            RequestConfig globalConfig = RequestConfig.custom().setCookieSpec(CookieSpecs.IGNORE_COOKIES).build();
            CloseableHttpClient client = HttpClients.custom().setDefaultRequestConfig(globalConfig).build();

            HttpGet request = new HttpGet(url);
            if (headers != null) {
                for (Map.Entry<String, String> header : headers.entrySet()) {
                    request.setHeader(header.getKey(), header.getValue());
                }
            }
            HttpResponse response = client.execute(request);

            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                return response.getEntity();
            } else {
                log.error("doGet请求失败，状态码---->{},请求地址--->{}", response.getStatusLine().getStatusCode(),url);
                return null;
            }
        } catch (IOException e) {
            log.error("doGet请求出错--->{}", e.getMessage());
            return null;
        }
    }

    public static String doGet(String url) {
        try {
            return EntityUtils.toString(Objects.requireNonNull(doGet(url, null)));
        } catch (IOException e) {
            log.error("转换实体出错--->{}",e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    public static String doPost(String url, HttpEntity params, Map<String, String> headers) {
        BufferedReader in = null;
        try {
            // 定义HttpClient
            HttpClient client = HttpClientBuilder.create().build();
            // 实例化HTTP方法
            HttpPost request = new HttpPost(url);
            if (headers != null) {
                for (Map.Entry<String, String> header : headers.entrySet()) {
                    request.setHeader(header.getKey(), header.getValue());
                }
            }
            //设置参数
            request.setEntity(params);

            HttpResponse response = client.execute(request);
            int code = response.getStatusLine().getStatusCode();
            if (code == HttpStatus.SC_OK) {    //请求成功
                in = new BufferedReader(new InputStreamReader(response.getEntity()
                        .getContent(), StandardCharsets.UTF_8));
                StringBuilder sb = new StringBuilder("");
                String line = "";
                String NL = System.getProperty("line.separator");
                while ((line = in.readLine()) != null) {
                    sb.append(line).append(NL);
                }
                in.close();
                return sb.toString();
            } else {    //
                log.error("doPost请求失败，状态码---->{}", code);
                return null;
            }
        } catch (Exception e) {
            log.error("doPost请求出错，错误信息---->{}", e.getMessage());
            return null;
        }
    }

}
