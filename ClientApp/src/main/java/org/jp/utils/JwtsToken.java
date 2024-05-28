package org.jp.utils;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import java.time.Duration;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
@Component
public class JwtsToken {
    @Value("${jwt.secret}")
    private String sectret;
    @Value("${jwt.lifetime}")
    private Duration jwtLifetime;

    public String genereteToken(String username,List<String> authorities){
        Map<String,Object> claims=new HashMap<>();
        //List<String> roleList=authorities;//user.getAuthorities().stream()
             //   .map(GrantedAuthority::getAuthority).collect(Collectors.toList());// GrantedAuthority преобразует роли в строки
        claims.put("roles",authorities);
        Date issuedDate =new Date();
        Date expiretDate=new Date(issuedDate.getTime()+jwtLifetime.toMillis());
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(username)
                .setIssuedAt(issuedDate)
                .setExpiration(expiretDate)
                .signWith(SignatureAlgorithm.HS256,sectret)
                .compact();
    }
}
