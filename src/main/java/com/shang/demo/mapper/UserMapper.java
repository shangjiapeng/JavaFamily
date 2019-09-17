package com.shang.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.shang.demo.domain.User;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @Author: 尚家朋
 * @Date: 2019-07-01 16:18
 * @Version 1.0
 */

/** User 对应的 Mapper 接口 */
public interface UserMapper extends BaseMapper<User> {

    @Select("select * from user ")
    List<User> findPage();

    @Select("select * from user limit #{start},#{pageSize}")
    List<User> findPage(int start,int pageSize);


    User findById(int userId);

}
