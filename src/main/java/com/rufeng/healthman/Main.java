package com.rufeng.healthman;

import java.net.URI;
import java.nio.file.Paths;

/**
 * @author rufeng
 * @time 2022-04-17 12:33
 * @package com.rufeng.healthman
 * @description TODO
 */
public class Main {
    public static void main(String[] args) {
        URI uri = URI.create("https://p1.music.126.net/Y3C5ob6SQjXRijaVNBu4Sw==/109951164400648086.jpg");
        URI uri1 = URI.create("dad/adads");
        System.out.println(uri.getHost());
        System.out.println(uri.getAuthority());
        System.out.println(uri.getScheme());
        System.out.println(uri.getRawSchemeSpecificPart());
        System.out.println(uri1.getScheme());
    }
}
