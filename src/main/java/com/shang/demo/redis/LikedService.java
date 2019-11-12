package com.shang.demo.redis;

import com.shang.demo.pojo.UserLike;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * <p>点赞业务层接口</p>
 *
 * @Author: ShangJiaPeng
 * @Date: 2019/11/6 11:55
 */
public interface LikedService {
    /**
     * 保存点赞记录
     * @param userLike
     * @return
     */
    UserLike save(UserLike userLike);

    /**
     * 批量保存或修改
     * @param list
     */
    List<UserLike> saveAll(List<UserLike> list);


    /**
     * 根据被点赞人的id查询点赞列表（即查询都谁给这个人点赞过）
     * @param likedUserId 被点赞人的id
     * @param page
     * @return
     */
    Page<UserLike> getLikedListByLikedUserId(String likedUserId, int page, int size);

    /**
     * 根据点赞人的id查询点赞列表（即查询这个人都给谁点赞过）
     * @param likedPostId
     * @param page
     * @return
     */
    Page<UserLike> getLikedListByLikedPostId(String likedPostId, int page, int size);

    /**
     * 通过被点赞人和点赞人id查询是否存在点赞记录
     * @param likedUserId
     * @param likedPostId
     * @return
     */
    UserLike getByLikedUserIdAndLikedPostId(String likedUserId, String likedPostId);

    /**
     * 将Redis里的点赞数据存入数据库中
     */
    void transLikedFromRedis2DB();

    /**
     * 将Redis中的点赞数量数据存入数据库
     */
    void transLikedCountFromRedis2DB();
}
