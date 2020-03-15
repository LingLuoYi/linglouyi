package com.linglouyi.tools;

import com.alibaba.fastjson.JSON;
import lombok.Cleanup;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import com.qiniu.cdn.CdnManager;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import com.qiniu.util.StringMap;

/**
 * 文件工具
 */
@Slf4j
public class FileTool {


    public static String[] types = {".jpg", ".bmp", ".jpeg", ".png", ".JPG", ".BMP", ".JPEG", ".PNG"};

    private static String accessKey = "ytVy_Ib69COVJetJywLCrSCouC2SR1yceaF87qOV";

    private static String secretKey = "Pn116uBXqfjFAJq5Y9CyxxNjztR79BD8dyLwQNkQ";

    private static String bucket = "test";

    //读取文件加载为字符串
    public static String TemplateLoad(File file) {
        StringBuilder result = new StringBuilder();
        try {
            @Cleanup BufferedReader br = new BufferedReader(new FileReader(file));//构造一个BufferedReader类来读取文件
            String s;
            while ((s = br.readLine()) != null) {//使用readLine方法，一次读一行
                result.append(System.lineSeparator()).append(s);
            }
        } catch (Exception e) {
            log.warn("文件读取异常！");
        }
        return result.toString();
    }

    /**
     * 存文件
     * @param filePath
     * @param content
     * @throws Exception
     */
    public static void contentToTxt(String filePath, String content) throws Exception {
        @Cleanup FileOutputStream fos = new FileOutputStream(new File(filePath), true);
        @Cleanup OutputStreamWriter osw = new OutputStreamWriter(fos, StandardCharsets.UTF_8);
        @Cleanup BufferedWriter bw = new BufferedWriter(osw);
        bw.write(content + "\r\n");
    }

    /**
     * 上传文件
     * @param file
     * @param path
     * @param name
     * @throws IOException
     */
    public static void Upload(byte[] file,String path,String name) throws IOException {
        File fileSourcePath = new File(path);
        if (!fileSourcePath.exists()) {
            fileSourcePath.mkdirs();
        }
        @Cleanup FileOutputStream outs = new FileOutputStream(new File(fileSourcePath, name));
        @Cleanup BufferedOutputStream out = new BufferedOutputStream(outs);
        out.write(file);
    }

    /**
     * 删除文件及文件夹
     * @param path
     * @return
     */
    public static boolean deleteFiles(String path){
        File file = new File(path);
        if(!file.exists()){
            return false;
        }
        if(file.isFile()){
            return file.delete();
        }
        File[] files = file.listFiles();
        for (File f : files) {
            if(f.isFile()){
                if(!f.delete()){
                    log.warn("{} delete error!",f.getAbsolutePath());
                    return false;
                }
            }else{
                if(!FileTool.deleteFiles(f.getAbsolutePath())){
                    return false;
                }
            }
        }
        return file.delete();
    }

    /**
     * 删除单个文件
     * @param fileName
     * @return
     */
    public static boolean deleteFile(String fileName) {
        File file = new File(fileName);
        // 如果文件路径所对应的文件存在，并且是一个文件，则直接删除
        if (file.exists() && file.isFile()) {
            if (file.delete()) {
                return true;
            } else {
                return false;
            }
        } else {
            return true;
        }
    }

    //输出文件
    public static byte[] OutFile(String path) throws IOException {
        File file = new File(path);
        @Cleanup FileInputStream fis = new FileInputStream(file);
        long size = file.length();
        byte[] b = new byte[(int) size];
        fis.read(b, 0, (int) size);
        return b;
    }

    //上传到七牛云
    public static String upload(byte[] file,String name) throws IOException {
        Configuration cfg = new Configuration(Zone.zone0());
        UploadManager uploadManager = new UploadManager(cfg);
        Auth auth = Auth.create(accessKey, secretKey);
        String upToken = auth.uploadToken(bucket,name,3600,new StringMap().put("insertOnly", 0));
        Response response = uploadManager.put(file, name, upToken);
        String host = "";
        if (!"".equals(response.bodyString())) {
            DefaultPutRet putRet = JSON.parseObject(response.bodyString(), DefaultPutRet.class);
            CdnManager c = new CdnManager(auth);
            c.refreshUrls(new String[]{ putRet.key});
            return putRet.key;
        }
        return "-1";
    }

    //删除七牛云文件
    public static void del(String key) throws QiniuException {
        Configuration cfg = new Configuration(Zone.zone0());
        Auth auth = Auth.create(accessKey, secretKey);
        BucketManager bucketManager = new BucketManager(auth, cfg);
        bucketManager.delete(bucket, key);
    }

    //遍历当前文件夹下所有文件
    public static List<File> listDirectory(File dir)throws IOException {
        if(!dir.exists())
            throw new IllegalArgumentException("目录："+dir+"不存在.");
        if(!dir.isDirectory()){
            throw new IllegalArgumentException(dir+"不是目录。");
        }
        List<File> list = new ArrayList<>();
        //如果要遍历子目录下的内容就需要构造File对象做递归操作，File提供了直接返回File对象的API
        File[] files=dir.listFiles();
        if(files!=null&&files.length>0){
            for(File file:files){
                if(file.isDirectory())
                    //递归
                    listDirectory(file);
                else
                    list.add(file);
            }
        }
        return list;
    }
}
