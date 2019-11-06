package com.shang.demo.redis.impl;

import com.shang.demo.enums.LikedStatusEnum;
import com.shang.demo.mapper.UserLikeRepository;
import com.shang.demo.pojo.User;
import com.shang.demo.pojo.UserLike;
import com.shang.demo.pojo.dto.LikedCountDTO;
import com.shang.demo.redis.LikedService;
import com.shang.demo.redis.RedisService;
import com.shang.demo.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>点赞数据库业务 service 层</p>
 *
 * @Author: ShangJiaPeng
 * @Date: 2019/11/6 12:17
 */
@Service
public class LikedServiceImpl implements LikedService {

    @Resource
    private UserLikeRepository likeRepository;
    @Resource
    private RedisService redisService;
    @Resource
    private UserService userService;


    @Override
    @Transactional
    public UserLike save(UserLike userLike) {
        return likeRepository.save(userLike);
    }

    @Override
    @Transactional
    public List<UserLike> saveAll(List<UserLike> list) {
        return likeRepository.saveAll(list);
    }

    @Override
    public Page<UserLike> getLikedListByLikedUserId(String likedUserId, Pageable pageable) {
        return likeRepository.findByLikedUserIdAndStatus(likedUserId, LikedStatusEnum.LIKE.getCode(), pageable);
    }

    @Override
    public Page<UserLike> getLikedListByLikedPostId(String likedPostId, Pageable pageable) {
        return likeRepository.findByLikedPostIdAndStatus(likedPostId, LikedStatusEnum.LIKE.getCode(), pageable);
    }

    @Override
    public UserLike getByLikedUserIdAndLikedPostId(String likedUserId, String likedPostId) {
        return likeRepository.findByLikedUserIdAndLikedPostId(likedUserId, likedPostId);
    }

    @Override
    @Transactional
    public void transLikedFromRedis2DB() {
        List<UserLike> list = redisService.getLikedDataFromRedis();
        for (UserLike like : list) {
            UserLike ul = getByLikedUserIdAndLikedPostId(like.getLikedUserId(), like.getLikedPostId());
            if (ul == null){
                //没有记录，直接存入
                save(like);
            }else{
                //有记录，需要更新
                ul.setStatus(like.getStatus());
                save(ul);
            }
        }
    }

    @Override
    @Transactional
    public void transLikedCountFromRedis2DB() {
        List<LikedCountDTO> list = redisService.getLikedCountFromRedis();
        for (LikedCountDTO dto : list) {
            User user = userService.getById(dto.getId());
            //点赞数量属于无关紧要的操作，出错无需抛异常
            if (user != null){
                Integer likeNum = user.getLikeNumber() + dto.getCount();
                user.setLikeNumber(likeNum);
                //更新点赞数量
                userService.updateById(user);
            }
        }
    }

}
