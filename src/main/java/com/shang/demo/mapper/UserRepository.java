package com.shang.demo.mapper;

import com.shang.demo.pojo.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * <p>JpA持久层</p>
 *
 * @Author: ShangJiaPeng
 * @Date: 2019/10/30 10:46
 */
public interface UserRepository extends JpaRepository<User,Integer> {

}
