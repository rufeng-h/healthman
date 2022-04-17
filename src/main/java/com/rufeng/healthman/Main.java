package com.rufeng.healthman;

import java.util.HashSet;

/**
 * @author rufeng
 * @time 2022-04-17 12:33
 * @package com.rufeng.healthman
 * @description TODO
 */
public class Main {
    public static void main(String[] args) {
        HashSet<Integer> hashSet = new HashSet<>();
        hashSet.add(1);
        hashSet.add(2);
        hashSet.add(3);
        hashSet.add(4);
        HashSet<Integer> s1 = new HashSet<>();
        s1.addAll(hashSet);

        HashSet<Integer> s2 = new HashSet<>();
        s2.add(2);
        s2.add(3);
        s2.add(5);

        s1.removeAll(s2);
        System.out.println(s1);

        s1.clear();
        s1.addAll(hashSet);
        s2.removeAll(s1);
        System.out.println(s2);
    }
}
