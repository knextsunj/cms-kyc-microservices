package com.github.knextsunj.cms.login;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.knextsunj.cms.login.service.impl.JwtService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.security.Key;
import java.util.Scanner;

public class PasswordEncode {

    public static void main(String[] args) throws IOException {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String encodedPwd = bCryptPasswordEncoder.encode("ab1$78");
        System.out.println(encodedPwd);


        JwtService jwtService = new JwtService();
        String token = jwtService.getToken("messi");
        System.out.println(token);
        //eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJtZXNzaSIsImV4cCI6MTY5ODE4MjAxN30.letUZq9j6T2G2gR-YB8RabTR5H6LHctc9FZ-9VALTRU

        FileOutputStream fileOutputStream = new FileOutputStream("/home/kk/cm1.key");
//        fileOutputStream.write(JwtService.getKey().getEncoded());
//
//
//
//        File file = new File("keys/cms.key");
//        byte[] bytes = new byte[(int) file.length()];
//        try(FileInputStream fis = new FileInputStream(file)) {
//            fis.read(bytes);
//        }
//        SecretKeySpec secretKeySpec = new SecretKeySpec(bytes,SignatureAlgorithm.HS256.getJcaName());
//        String user = Jwts.parserBuilder()
//                .setSigningKey(jwtKey)
//                .setSigningKey(secretKeySpec)
//                .build()
//                .parseClaimsJws(token.replace("Bearer", ""))
//                .getBody()
//                .getSubject();
//
//        System.out.println(user);

    }
}
