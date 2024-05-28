package org.jp.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class JwtToken {
    @Value("${jwt.secret}")
    private String sectret;
    @Value("${jwt.lifetime}")
    private Duration jwtLifetime;



    public String getUserName(String token){
        return getAllClaimsFromToken(token).getSubject();
    }
    public List<String> getUserRole(String token){
        return getAllClaimsFromToken(token).get("roles",List.class);
    }

    private Claims getAllClaimsFromToken(String token) {
        return (Claims) Jwts.parser()
                .setSigningKey(sectret)
                .build()
                .parse(token)
                .getBody();
    }
}
