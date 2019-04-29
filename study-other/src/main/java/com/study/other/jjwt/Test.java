package com.study.other.jjwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.apache.commons.lang3.RandomStringUtils;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;

import java.security.NoSuchAlgorithmException;

import java.time.Instant;
import java.util.Base64;
import java.util.Date;

/**
 * @author cj-ch
 * @date 2018/12/4 下午8:08
 */
public class Test {

    public static void main(String[] args) throws NoSuchAlgorithmException {
        // We need a signing key, so we'll create one just for this example. Usually
// the key would be read from your application configuration instead.
        String seed = "we12zw34we有中文的口味12zw34w";
        SecretKey secretKey = Keys.hmacShaKeyFor(seed.getBytes());
//        SecretKey secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);
        System.out.println(secretKey.getAlgorithm());
        System.out.println(Base64.getEncoder().encodeToString(secretKey.getEncoded()));
        System.out.println(secretKey.getFormat());

        Date from = Date.from(Instant.now().plusSeconds(300));
        String jws = Jwts.builder()
                .setExpiration(from)
                .claim("token","234234")
                .signWith(secretKey)
                .compact();
        System.out.println(jws);

        Jws<Claims> claimsJws = Jwts.parser()
                .requireExpiration(new Date())
                .setSigningKey(secretKey)
                .parseClaimsJws(jws);
        System.out.println(claimsJws);
        Date expiration = claimsJws.getBody().getExpiration();
        System.out.println(from.compareTo(expiration));


    }
}
