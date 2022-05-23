package com.rufeng.healthman;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;

@SpringBootTest
class HealthmanApplicationTests {
    void contextLoads() {
    }

    @Test
    public void test() throws IOException {
        RestTemplate restTemplate = new RestTemplate();
        String base64 = restTemplate.getForObject("http://127.0.0.1:8888", String.class);
        assert base64 != null;
        int i = base64.indexOf(',');
        String data = base64.substring(i);
        byte[] bytes = Base64.getDecoder().decode(data);
        Files.write(Paths.get("./test.svg"), bytes);
    }

}
