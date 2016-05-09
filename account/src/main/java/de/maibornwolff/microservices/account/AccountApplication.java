package de.maibornwolff.microservices.account;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
public class AccountApplication {
    public static void main(String[] args) {
        ApplicationContext ctx = SpringApplication.run(AccountApplication.class, args);
    }
}