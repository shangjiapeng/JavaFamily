package com.shang.demo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.shang.demo.pojo.User;
import com.shang.demo.mapper.UserMapper;
import com.shang.demo.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;


/**
 * @Author: 尚家朋
 * @Date: 2019-07-03 10:28
 * @Version 1.0
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Resource
    private UserMapper userMapper;
    /**
     * 使用pageHelper 分页查询
     *
     * @param page     int
     * @param pageSize int
     * @return PageInfo
     */
    @Override
    public Page<User> findPage(int page, int pageSize) {
//        UserMapper userMapper = getBaseMapper();
//        List<User> users = userMapper.selectList(Wrappers.emptyWrapper());
//        if (users!=null&&users.size()>0){
//            return new PageInfo<User>(users);
//        }else {
//            return null;
//        }
        Page<User> page1 = PageHelper.startPage(page, pageSize);
        System.err.println(page1);
        System.err.println(page1.toString());
        List<User> userList = userMapper.findPage();
        return page1;
    }
}
