package com.fuchengbang.staticservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @Auther : coach
 * @create : 2020/12/10 0010 13:24
 */
@SpringBootApplication
@ComponentScan(basePackages = "com.fuchengbang")
@EnableFeignClients
@EnableDiscoveryClient
@EnableScheduling //自动开启定时器任务
public class StaticApplication {
    public static void main(String[] args) {
        SpringApplication.run(StaticApplication.class, args);
    }
}
