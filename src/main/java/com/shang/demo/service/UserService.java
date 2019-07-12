package com.shang.demo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.Page;
import com.shang.demo.domain.User;


/**
 * @Author: 尚家朋
 * @Date: 2019-07-03 10:25
 * @Version 1.0
 */
public interface UserService extends IService<User> {

    Page<User> findPage(int page, int pageSize);
}