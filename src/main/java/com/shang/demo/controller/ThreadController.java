package com.shang.demo.controller;


import com.shang.demo.thread.Lock.MyReentrantLockService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/thread")
public class ThreadController {

    @Resource
    private MyReentrantLockService reentrantLockService;


    @RequestMapping("/reentrantLock")
    public void reentrantLock(){
        try {
            reentrantLockService.reentrantLock();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
