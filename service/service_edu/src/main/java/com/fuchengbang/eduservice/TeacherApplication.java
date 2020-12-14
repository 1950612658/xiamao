package com.fuchengbang.eduservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * @Auther : coach
 * @create : 2020/11/26 0026 11:16
 */
@SpringBootApplication
@ComponentScan(basePackages = "com.fuchengbang")
@EnableDiscoveryClient //服务发现
@EnableFeignClients(basePackages = "com.fuchengbang.eduservice.feignclient")
public class TeacherApplication {
    public static void main(String[] args) {
        SpringApplication.run(TeacherApplication.class, args);
    }
}
