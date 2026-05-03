package com.rzk.edukacijaservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class EdukacijaServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(EdukacijaServiceApplication.class, args);
    }

}
