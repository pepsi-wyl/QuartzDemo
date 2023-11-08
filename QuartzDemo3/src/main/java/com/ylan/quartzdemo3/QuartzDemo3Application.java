package com.ylan.quartzdemo3;

import com.ylan.quartzdemo3.mapper.TaskMapper;
import com.ylan.quartzdemo3.model.entity.TaskEntity;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.Date;

@SpringBootApplication
public class QuartzDemo3Application {
    public static void main(String[] args) {
        SpringApplication.run(QuartzDemo3Application.class, args);
    }
}