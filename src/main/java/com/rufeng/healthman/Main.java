package com.rufeng.healthman;

import com.rufeng.healthman.security.context.SecurityContextHolder;
import org.springframework.util.DigestUtils;

import java.nio.charset.StandardCharsets;

/**
 * @author rufeng
 * @time 2022-04-19 18:40
 * @package com.rufeng.healthman
 * @description TODO
 */
public class Main {
    public static void main(String[] args) {
        System.out.println(DigestUtils.md5DigestAsHex("123456".getBytes(StandardCharsets.UTF_8)));
    }
}
