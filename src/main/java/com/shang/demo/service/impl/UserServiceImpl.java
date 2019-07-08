package com.shang.demo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shang.demo.domain.User;
import com.shang.demo.mapper.UserMapper;
import com.shang.demo.service.UserService;
import org.springframework.stereotype.Service;


/**
 * @Author: 尚家朋
 * @Date: 2019-07-03 10:28
 * @Version 1.0
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

}
