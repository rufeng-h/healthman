package com.rufeng.healthman;

import java.nio.file.Paths;

/**
 * @author rufeng
 * @time 2022-04-17 12:33
 * @package com.rufeng.healthman
 * @description TODO
 */
public class Main {
    public static void main(String[] args) {

        System.out.println(System.getProperty("user.home"));
        System.out.println(Paths.get(System.getProperty("user.dir")).getFileName().toString());
    }
}
