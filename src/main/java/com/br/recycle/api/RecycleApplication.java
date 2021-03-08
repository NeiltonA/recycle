package com.br.recycle.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan("com.br.recycle.api")
@SpringBootApplication
@EnableFeignClients
public class RecycleApplication {

    public static void main(String[] args) {
        SpringApplication.run(RecycleApplication.class, args);
    }

}
