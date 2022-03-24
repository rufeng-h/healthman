package com.rufeng.healthman;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;


/**
 * @author rufeng
 */
@SpringBootApplication
@EnableAspectJAutoProxy(exposeProxy = true)
public class HealthmanApplication {
    public static void main(String[] args) {
        SpringApplication.run(HealthmanApplication.class, args);
    }
}
