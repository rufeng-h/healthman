package com.rufeng.healthman;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;

/**
 * @author rufeng
 * @time 2022-03-05 17:36
 * @package com.rufeng.healthman
 * @description 测试jwt
 */
public class Main {
    private static final String SECRET = "SyI/8w+X528KqE2S8JKU8Sv1Bb+coixCZrU/fYYKDqc=";
    public static void main(String[] args) {
        SecretKey secretKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(SECRET));
        String jws = Jwts.builder()
                .setSubject("Joe")
                .signWith(secretKey).compact();
        System.out.println(jws);

        Jws<Claims> claimsJws = Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(jws);
        Claims jwsBody = claimsJws.getBody();
        System.out.println(jwsBody.getSubject());

    }
}
