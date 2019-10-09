package com.shang.demo.pojo.result.entity;

import java.util.List;

/**
 * <p>分页结果类</p>
 *
 * @Author: ShangJiaPeng
 * @Since: 2019-07-30 16:01
 */
public class PageResult<T> {
    private Long total;
    private List<T> data;

    public PageResult() {
    }

    public PageResult(Long total, List<T> data) {
        super();
        this.total = total;
        this.data = data;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }
}
