package com.example.projekt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
public class ProjektApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProjektApplication.class, args);
    }

}
