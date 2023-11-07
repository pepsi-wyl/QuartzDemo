package com.example.quartzdemo2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author by pepsi-wyl
 * @date 2023-11-07 22:09
 */
@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        System.setProperty("server.port", "0");
        SpringApplication.run(Application.class, args);
    }
}
