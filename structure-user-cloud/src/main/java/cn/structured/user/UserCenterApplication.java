package cn.structured.user;

import cn.structure.starter.web.restful.annotation.EnableSwagger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * 用户服务启动入口
 *
 * @author chuck
 * @since JDK1.8
 */
@EnableSwagger
@EnableFeignClients
@EnableDiscoveryClient
@SpringBootApplication
public class UserCenterApplication {
    public static void main(String[] args) {
        SpringApplication.run(UserCenterApplication.class, args);
    }
}
