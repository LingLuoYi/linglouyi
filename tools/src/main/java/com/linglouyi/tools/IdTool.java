package com.linglouyi.tools;


import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.text.SimpleDateFormat;
import java.util.Date;

@Slf4j
public class IdTool {

    public IdTool(){}

    public IdTool(String timeFormat,int amount,String prefix,boolean aligned){
        this.timeFormat = timeFormat;
        this.amount = amount;
        this.prefix = prefix;
        this.aligned = aligned;
    }

    public IdTool(String timeFormat){
        this.timeFormat = timeFormat;
    }

    private String timeFormat = "yyyyMMddHHmmss";

    private SimpleDateFormat formatter = new SimpleDateFormat(timeFormat);

    @Setter @Getter
    private String prefix = "";//默认前缀

    @Setter @Getter
    private int amount = 1000;//默认每秒并发

    private long time = System.currentTimeMillis()/1000;//当前时间

    private int i = 1;

    private boolean aligned = false;//对齐id

    //时间顺序

    public synchronized String id(){
        String id = prefix + formatter.format(new Date());
//        log.info("{}",time);
//        log.info("{}",System.currentTimeMillis());
        if (time == System.currentTimeMillis()/1000){//同一时间
            if (i < amount){
                if (aligned){
                    id = id + String.format("%0"+(String.valueOf(amount).length()+1)+"d",i);
                }else {
                    id = id + i;
                }
                i++;
            }
        }else {
            time = System.currentTimeMillis()/1000;
            i = 1;
            if (aligned)
                id = id + String.format("%0"+(String.valueOf(amount).length()+1)+"d",i);
            else
                id = id + i;

        }
        return id;
    }

}
