package com.fuchengbang.ucenter;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

/**
 * @Auther : coach
 * @create : 2020/12/7 0007 13:35
 */
@ComponentScan({"com.fuchengbang"})
@SpringBootApplication
@EnableDiscoveryClient
@MapperScan("com.fuchengbang.ucenter.mapper")
public class ServiceUcApplication {
    public static void main(String[] args) {
        SpringApplication.run(ServiceUcApplication.class, args);
    }
}
