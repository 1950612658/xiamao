package com.fuchengbang.util;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Auther : coach
 * @create : 2020/12/9 0009 13:52
 */
public class ShowFile {
    public static void main(String[] args) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        System.out.println(sdf.format(date));
    }
}
