package com.rufeng.healthman;

import com.rufeng.healthman.config.properties.HealthmanProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.transaction.annotation.EnableTransactionManagement;


/**
 * @author rufeng
 */
@SpringBootApplication
@EnableAspectJAutoProxy(exposeProxy = true)
@EnableTransactionManagement
@EnableConfigurationProperties(HealthmanProperties.class)
public class HealthmanApplication{
    public static void main(String[] args) {
        SpringApplication.run(HealthmanApplication.class, args);
    }
}
