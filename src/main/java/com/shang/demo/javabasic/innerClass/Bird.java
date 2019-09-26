package com.shang.demo.javabasic.innerClass;

/**
 * 动物鸟实体类
 */
public abstract class Bird {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public abstract int fly();
}
