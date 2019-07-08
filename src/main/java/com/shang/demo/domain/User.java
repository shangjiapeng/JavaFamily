package com.shang.demo.domain;

import lombok.Data;
import lombok.ToString;

/**
 * @Author: 尚家朋
 * @Date: 2019-07-01 16:16
 * @Version 1.0
 */
@Data
@ToString
//@TableName(value = "user")
public class User {
//    @TableId(value = "id", type = IdType.ID_WORKER)
    private Long id;
//    @TableField(value = "name")
//    @Version //乐观锁注解
    private String name;
    private int age;
    private String email;
}
