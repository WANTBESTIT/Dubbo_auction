package org.web.auction;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
@MapperScan("org.web.auction.mapper")
public class AppAuction {
    public static void main(String[] args) {
        new SpringApplicationBuilder(AppAuction.class).web(WebApplicationType.NONE).run(args);
    }
}
