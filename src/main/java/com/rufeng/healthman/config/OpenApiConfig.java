package com.rufeng.healthman.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

/**
 * @author rufeng
 * @time 2022-02-24 10:09
 * @package com.rufeng.healthman.config
 * @description Api文档配置
 */
@Profile("dev")
@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI openApi() {
        return new OpenAPI()
                .info(new Info()
                        .contact(new Contact().email("chunfengh163@163.com").name("rufeng").url("http://windcf.com"))
                        .description("兴趣所至")
                        .title("体质健康系统")
                        .license(new License().url("https://opensource.org/licenses/MIT")).version("v1.0"));
    }
}
