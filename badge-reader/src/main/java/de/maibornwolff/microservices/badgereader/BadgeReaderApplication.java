package de.maibornwolff.microservices.badgereader;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
@EnableFeignClients
public class BadgeReaderApplication {
    public static void main(String[] args) {
        ApplicationContext ctx = SpringApplication
                .run(BadgeReaderApplication.class, args);
    }
}