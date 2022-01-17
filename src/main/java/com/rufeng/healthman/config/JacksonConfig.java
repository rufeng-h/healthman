package com.rufeng.healthman.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

/**
 * @author rufeng
 * @time 2022-01-10 19:34
 * @package com.rufeng.healthman.config
 * @description Jackson配置
 */
@Configuration
public class JacksonConfig {
    @Bean
    @Primary
    ObjectMapper jacksonObjectMapper(Jackson2ObjectMapperBuilder builder) {
        return builder.createXmlMapper(false).build();
    }
}
