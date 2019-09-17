package com.shang.demo.clendar;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

/**
 * <p></p>
 *
 * @Author: ShangJiaPeng
 * @Since: 2019-07-18 11:37
 */
public class CalendarTest {
    public static void main(String[] args) {
     //测试使用日历类 :Clender
        Calendar calendar = Calendar.getInstance();
        System.err.println(calendar);
        Date nowDate = new Date();
        System.err.println("nowDate"+nowDate);
        calendar.setTime(nowDate);
        calendar.add(Calendar.DATE,2); //num为增加的天数
        Date newDate = calendar.getTime();
        System.err.println("newDate"+newDate);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String valid_time = dateFormat.format(newDate);
        System.err.println("valid_time"+valid_time);


        UUID uuid = UUID.randomUUID();
        String s = uuid.toString();
        String s1 = s.replaceAll("-", "");
    }
}
