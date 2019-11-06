package com.shang.demo.util;

/**
 * <p> 用于根据一定规则生成 key</p>
 *
 * @Author: ShangJiaPeng
 * @Date: 2019/11/6 11:56
 */
public class RedisKeyUtils {

    //保存用户点赞数据的 key
    public static final String MAP_KEY_USER_LIKED="MAP_USER_LIKED";
    //保存用户被点赞数量的 key
    public static final String MAP_KEY__USER_LIKED_COUNT="MAP_USER_LIKED_COUNT";

    /**
     * 拼接被点赞的用户id和点赞的人的id作为key。格式 222222::333333
     * @param likedUserId 被点赞的人id
     * @param likedPostId 点赞的人的id
     * @return
     */
    public static String getLikedKey(String likedUserId, String likedPostId){
        StringBuilder builder = new StringBuilder();
        builder.append(likedUserId);
        builder.append("::");
        builder.append(likedPostId);
        return builder.toString();
    }
}
