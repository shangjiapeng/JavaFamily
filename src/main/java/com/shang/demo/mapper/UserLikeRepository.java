package com.shang.demo.mapper;

import com.shang.demo.pojo.UserLike;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * <p>JpA持久层</p>
 *
 * @Author: ShangJiaPeng
 * @Date: 2019/10/30 10:46
 */
public interface UserLikeRepository extends JpaRepository<UserLike,Integer>, JpaSpecificationExecutor<UserLike> {

    @Query("select ul from UserLike ul where likedUserId=?1 and status =?2 ")
    Page<UserLike> findByLikedUserIdAndStatus(String likedUserId, Integer status, Pageable pageable);

    @Query("select ul from UserLike ul where likedPostId=?1 and status =?2 ")
    Page<UserLike> findByLikedPostIdAndStatus(String likedPostId,Integer status, Pageable pageable) ;

    @Query("select ul from UserLike ul where likedUserId=?1 and likedPostId=?2 ")
    UserLike findByLikedUserIdAndLikedPostId(String likedUserId, String likedPostId);


}
