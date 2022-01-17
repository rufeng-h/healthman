package com.rufeng.healthman;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.rufeng.healthman.common.ApiPage;
import com.rufeng.healthman.domain.User;
import com.rufeng.healthman.mapper.test.TestMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class HealthmanApplicationTests {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private TestMapper testMapper;

    @Test
    void contextLoads() {
    }

    @Test
    public void testPage() throws JsonProcessingException {
        PageHelper.startPage(1, 3);
        Page<User> page = testMapper.selectPage();
        System.out.println(page.getTotal());
        System.out.println(objectMapper.writeValueAsString(ApiPage.convert(page)));
    }

}
