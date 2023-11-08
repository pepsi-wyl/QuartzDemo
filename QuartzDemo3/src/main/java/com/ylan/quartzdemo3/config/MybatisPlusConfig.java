package com.ylan.quartzdemo3.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author by pepsi-wyl
 * @date 2023-11-08 15:22
 * @description MP 配置类
 */

@Configuration
@MapperScan("com.ylan.quartzdemo3.mapper")
@EnableTransactionManagement
public class MybatisPlusConfig {

}