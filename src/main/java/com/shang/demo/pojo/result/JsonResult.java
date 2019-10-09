package com.shang.demo.pojo.result;

/**
 * @Author: 尚家朋
 * @Date: 2019-07-02 16:18
 * @Version 1.0
 */

public class JsonResult<T> {

    private int code;//状态码
    private String msg;//状态信息
    private T data;//相应具体的数据类型

    public JsonResult() {
    }

    public JsonResult(int code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;

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

    @Override
    public String toString() {
        return "JsonResult{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}

