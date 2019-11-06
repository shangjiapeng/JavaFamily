package com.shang.demo.pojo.dto;

import lombok.Data;
import lombok.ToString;

/**
 * <p>点赞计数 DTO</p>
 *
 * @Author: ShangJiaPeng
 * @Date: 2019/11/6 12:19
 */
@Data
@ToString
public class LikedCountDTO {

    private Integer id;   //主键
    private String likedUserId; //被点赞人的 ID
    private int count;//被点赞的次数

    public LikedCountDTO() {
    }

    public LikedCountDTO(String likedUserId, int count) {
        this.likedUserId = likedUserId;
        this.count = count;
    }
}
