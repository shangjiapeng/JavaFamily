package com.shang.demo.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.ToString;

import javax.persistence.*;

/**
 * @Author: 尚家朋
 * @Date: 2019-07-01 16:16
 * @Version 1.0
 */
@Data
@ToString
@TableName(value = "user")
@Entity
public class User {
    //    @TableId(value = "id", type = IdType.ID_WORKER)
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "age")
    private Integer age;
    @Column(name = "email")
    private String email;

    private Integer likeNumber;
}
