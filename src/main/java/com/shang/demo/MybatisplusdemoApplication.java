package com.shang.demo;

import com.shang.demo.util.IdWorker;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication()
@EnableTransactionManagement
@MapperScan("com.shang.demo.mapper")
@EntityScan("com.shang.demo.pojo")
@ComponentScan("com.shang.demo.controller")
@ComponentScan("com.shang.demo.service")
@ComponentScan("com.shang.demo.aop")
@Configuration
@EnableScheduling
public class MybatisplusdemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(MybatisplusdemoApplication.class, args);
    }

    @Bean
    public IdWorker idWorker(){
        return new IdWorker(1,1);
    }

}
