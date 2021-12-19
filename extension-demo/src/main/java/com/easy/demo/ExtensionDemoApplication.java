package com.easy.demo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.easy.demo.mapper")
public class ExtensionDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(ExtensionDemoApplication.class, args);
    }

}
