package com.shang.demo.domain;

import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.Version;
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
//@KeySequence("SEQ_TEST")//类注解
public class User {
//    @TableId(value = "id", type = IdType.ID_WORKER)
  //  @Version //乐观锁注解(支持的数据类型只有:int,Integer,long,Long,Date,Timestamp,LocalDateTime)
    private Long id;
//    @TableField(value = "name")
    private String name;
    private int age;
//    @TableLogic
    private String email;
}
