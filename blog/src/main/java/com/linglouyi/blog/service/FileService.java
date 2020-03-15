package com.linglouyi.blog.service;

import com.linglouyi.tools.FileTool;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

/**
 * @Date 2020/3/10
 * @Created liyi
 */
@Slf4j
@Service
public class FileService {

    //不做缓存，每次进来都自动扫描
    public List<String> fileList() throws IOException {
//        List<File> files = FileTool.listDirectory(new File("/Users/liyi/"));
        File[] files = new File("/Users/liyi/").listFiles();
        LinkedList<String> fileName = new LinkedList<>();
        for (File file : files) {
            if (file.isDirectory())
                System.out.println("文件夹" + file.getName());
            else
                System.out.println("文件" + file.getName());
            fileName.add(file.getName());
        }
        return fileName;
    }
}
