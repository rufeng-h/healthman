package com.rufeng.healthman.service.test;

import com.github.pagehelper.PageHelper;
import com.rufeng.healthman.common.ApiPage;
import com.rufeng.healthman.domain.User;
import com.rufeng.healthman.mapper.test.TestMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author rufeng
 * @time 2022-01-10 15:29
 * @package com.rufeng.healthman.service.test
 * @description 测试
 */
@Service
public class TestService {
    private final TestMapper testMapper;

    public TestService(TestMapper testMapper) {
        this.testMapper = testMapper;
    }

    public List<Map<String, Object>> getAll() {
        return testMapper.getAll();
    }

    public ApiPage<User> selectPage(Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        return ApiPage.convert(testMapper.selectPage());
    }
}
