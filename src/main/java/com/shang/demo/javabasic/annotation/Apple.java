package com.shang.demo.javabasic.annotation;

/**
 * 苹果类
 */
public class Apple {

    @FruitProvider(id=1,name = "红富士集团",address = "陕西省西安市延安路")
    private String appleProvider;

    public String getAppleProvider() {
        return appleProvider;
    }

    public void setAppleProvider(String appleProvider) {
        this.appleProvider = appleProvider;
    }
}
