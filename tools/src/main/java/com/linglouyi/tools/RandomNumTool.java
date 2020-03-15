package com.linglouyi.tools;

import java.text.NumberFormat;
public class RandomNumTool {

    /**
     * 随机数字
     * @param n 位数
     * @return 数字
     */
    public static String randomNum(double n) {
        double s = Math.pow(10, n - 1);
        double e = Math.pow(10, n) - 1;
        double res = Math.random() * (e - s) + s;
        NumberFormat nf = NumberFormat.getInstance();
        nf.setGroupingUsed(false);
        return nf.format(res);
    }


}
