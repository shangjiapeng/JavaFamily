package com.shang.demo.config;

import com.baomidou.mybatisplus.extension.incrementer.OracleKeyGenerator;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @Author: 尚家朋
 * @Date: 2019-07-03 12:12
 * @Version 1.0
 */
@EnableTransactionManagement
@Configuration
@MapperScan("com.shang.demo.service.*.mapper*")
public class MybatisPlusConfig {

    /**
     * 分页插件配置类
     */
    @Bean
    public PaginationInterceptor paginationInterceptor(){
        return new PaginationInterceptor();
    }

//    /**
//     * Sequence 主键
//     */
//    @Bean
//    public OracleKeyGenerator oracleKeyGenerator(){
//        return new OracleKeyGenerator();
//    }
//

}
