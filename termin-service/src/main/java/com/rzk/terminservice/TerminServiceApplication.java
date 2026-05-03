package com.rzk.terminservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class TerminServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(TerminServiceApplication.class, args);
    }

}
