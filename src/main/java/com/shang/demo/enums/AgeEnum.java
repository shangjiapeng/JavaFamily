package com.shang.demo.enums;

import com.baomidou.mybatisplus.core.enums.IEnum;

/**
 * <p></p>
 *
 * @Author: ShangJiaPeng
 * @Since: 2019-07-09 14:50
 */
public enum AgeEnum implements IEnum<Integer> {
    //枚举属性字段正例:
    ONE(1,"一岁"),
    TWO(2,"二岁"),
    THREE(3,"三岁");
    //枚举通常被当做常量使用,如果枚举中存在公共属性字段或者设置字段的方法,那么这些枚举常量的属性很容易被修改;
    //理想情况下,枚举中的属性字段是私有的,并在私有构造函数中赋值,没有对应的setter()方法,最好加上final修饰符
    private final int value;
    private final String desc;

    AgeEnum(int value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    //没有getter方法
    public Integer getValue() {
        return this.value;
    }
    public String getDesc() {
        return desc;
    }
}
