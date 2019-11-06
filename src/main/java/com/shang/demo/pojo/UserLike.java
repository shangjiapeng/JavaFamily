package com.shang.demo.pojo;

import com.shang.demo.enums.LikedStatusEnum;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * <p>用户点赞表</p>
 *
 * @Author: ShangJiaPeng
 * @Date: 2019/11/6 12:06
 */
@Entity
@Data
public class UserLike {

    //主键
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    //点赞用户的ID
    private String likedPostId;
    //被点赞用户的ID
    private String likedUserId;
    //点赞的状态
    private Integer status = LikedStatusEnum.UNLIKE.getCode();

    public UserLike() {

    }

    public UserLike(String likedPostId, String likedUserId, Integer status) {
        this.likedPostId = likedPostId;
        this.likedUserId = likedUserId;
        this.status = status;
    }
}
