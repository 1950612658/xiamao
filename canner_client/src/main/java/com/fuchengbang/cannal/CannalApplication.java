package com.fuchengbang.cannal;

import com.fuchengbang.cannal.client.CanalClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @Auther : coach
 * @create : 2020/12/11 0011 14:51
 */
@SpringBootApplication
public class CannalApplication implements CommandLineRunner {

    @Autowired
    private CanalClient canalClient;

    public static void main(String[] args){
        SpringApplication.run(CannalApplication.class,args);
    }

    @Override
    public void run(String... args) throws Exception {
    //项目启动，执行canal客户端监听
        canalClient.run();
    }
}
