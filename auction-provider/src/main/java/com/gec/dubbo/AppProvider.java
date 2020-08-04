package com.gec.dubbo;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class AppProvider {
    public static void main(String[] args) {
//        SpringApplication.run(AppProvider.class,args);   // 以web的方法启动
        // 以非web方式启动
       new SpringApplicationBuilder(AppProvider.class).web(WebApplicationType.NONE).run(args);
    }
}
