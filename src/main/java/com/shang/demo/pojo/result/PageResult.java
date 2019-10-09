package com.shang.demo.pojo.result;

/**
 * <p></p>
 *
 * @Author: ShangJiaPeng
 * @Since: 2019-07-04 14:29
 */

public class PageResult<T> {
    private int code;//状态码
    private String msg;//错误码
    private T data;//相应具体的数据类型
    private long total;//数据总条数

    public PageResult() {
    }

    public PageResult(int code, String msg, T data, long total) {
        this.code = code;
        this.msg = msg;
        this.data = data;
        this.total = total;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }
}
