package com.shang.demo.jdbc;

import com.shang.demo.pojo.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>JdbcTemplate的使用方法</p>
 *
 * @Author: ShangJiaPeng
 * @Date: 2019/10/30 10:11
 */
@Service
public class TestJDBC {
    @Resource
    private JdbcTemplate jdbcTemplate;

    public User selectUserById(int id){
        String sql ="select * from user where id = ? ";
        User user = jdbcTemplate.queryForObject(sql, User.class, id);
        return user;
    }

    public boolean inserUser(String name,int age,String email){
        String sql ="insert into user values(null,?,?,?)";
        int result = jdbcTemplate.update(sql, name, age, email);
        System.out.println("result:"+result);
        return result > 0;
    }

}
