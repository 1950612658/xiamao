package com.fuchengbang.cms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

/**
 * @Auther : coach
 * @create : 2020/12/3 0003 21:14
 */
@SpringBootApplication
@ComponentScan({"com.fuchengbang"}) //指定扫描位置
@EnableDiscoveryClient
public class CmsApplication {
    public static void main(String[] args) {
        SpringApplication.run(CmsApplication.class, args);
    }
}
