package com.shang.demo.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


public class TimeUtils {

    /**
     * 日期格式字符串转换成时间戳
     *
     * @param dateStr 字符串日期
     * @param format   如：yyyy-MM-dd HH:mm:ss
     *
     * @Return
     */
    public static long Date2TimeStamp(String dateStr, String format) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            return sdf.parse(dateStr).getTime() / 1000;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * Java将Unix时间戳转换成指定格式日期字符串
     * @param timestampString 时间戳 如："1473048265";
     * @param formats 要格式化的格式 默认："yyyy-MM-dd HH:mm:ss";
     *
     * @Return 返回结果 如："2016-09-05 16:06:42";
     */
    public static String TimeStamp2Date(String timestampString, String formats) {
        formats = "yyyy-MM-dd HH:mm:ss";
        Long timestamp = Long.parseLong(timestampString) * 1000;
        String date = new SimpleDateFormat(formats, Locale.CHINA).format(new Date(timestamp));
        return date;
    }

    /**
     * Java中获取当前时间
     *
     * @Return 返回结果：当前系统时间yyyy-MM-dd HH:mm:ss
     */

    public static String getNowTime(){
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        return df.format(new Date());
    }

    /**
     * Java中获取当前时间
     *
     * @Return 返回结果：当前系统时间yyyy-MM-dd HH:mm:ss
     */

    /**
     *
     * @param give_time 2019-10-01 10:20:16
     * @param time_format "yyyy-MM-dd HH:mm:ss"
     * @return i
     * @throws ParseException
     */
    public static int compareNowTimeAndGiveTime(String give_time,String time_format) throws ParseException {
        SimpleDateFormat df = new SimpleDateFormat(time_format);//设置日期格式
        String now_time = df.format(new Date());
        DateFormat dateFormat = new SimpleDateFormat(time_format);
        Date nowtime = dateFormat.parse(now_time);
        Date givetime = dateFormat.parse(give_time);
        return nowtime.compareTo(givetime);
    }

    /***
     * Java获取n位随机数
     * @param n n位随机数
     *
     * @Return 返回结果 n位随机数   ?n+1位?
     */
    public static int getRandNumber(int n){
        double x = Math.pow(10,n);
        return (int)((Math.random()*9+1)*x);
    }

    /***
     * 截取指定字符之前的字符
     * @param x 指定字符
     * @param str 被取字符串
     *
     * @Return x 之前的所有字符
     */
    public static String getXBefore(String str,String x){
        return str.substring(0,str.indexOf(x));
    }

    /**
     * 获取md5字符串
     * @param str
     * @return
     */
    public static String getMd5(String str) {

        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        byte[] bs = md5.digest(str.getBytes());
        StringBuilder sb = new StringBuilder(40);
        for(byte x:bs) {
            if((x & 0xff)>>4 == 0) {
                sb.append("0").append(Integer.toHexString(x & 0xff));
            } else {
                sb.append(Integer.toHexString(x & 0xff));
            }
        }
        return sb.toString();
    }

    /**
     * 获取当前时间字符串，格式：yyyyMMddHHmm
     * @return
     */
    public static String getNowTimeString(){
        DateFormat df = new SimpleDateFormat("yyyyMMddHHmm");
        Calendar calendar = Calendar.getInstance();
        return df.format(calendar.getTime());
    }


}
