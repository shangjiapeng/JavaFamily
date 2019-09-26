package com.shang.demo.javabasic.fanxing;

/**
 * 泛型类
 */
public class Box<T> {
    private T t;

    public T getT() {
        return t;
    }

    public void setT(T t) {
        this.t = t;
    }
}
