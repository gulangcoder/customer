package cn.fintecher.service;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class BaseEurekaServerApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder(BaseEurekaServerApplication.class).web(true).run(args);
    }
}
