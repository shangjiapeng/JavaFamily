package com.shang.demo.task;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * <p>任务调度</p>
 *
 * @Author: ShangJiaPeng
 * @Date: 2019/10/30 11:05
 */
@Component
public class ScheduleTask {

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    /**
     * 任务调度,每隔2秒执行一次
     */

    @Scheduled(fixedRate = 10000)  //单位是毫秒
    public void reportCurrentTime(){
        System.err.println("现在时间: "+dateFormat.format(new Date()));
    }

}
