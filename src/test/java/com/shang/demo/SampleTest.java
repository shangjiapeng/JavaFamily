package com.shang.demo;

import com.shang.demo.domain.User;
import com.shang.demo.mapper.UserMapper;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author: 尚家朋
 * @Date: 2019-07-01 17:06
 * @Version 1.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class SampleTest {
    @Resource
    private UserMapper userMapper;

    @Test
    public void TestSelect(){

        System.out.println(("-------selectAll method test-------"));
        List<User> userList = userMapper.selectList(null);
        Assert.assertEquals(5,userList.size());
        userList.forEach(System.out::println);
    }

}
