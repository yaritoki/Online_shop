package org.jp.config;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.jp.utils.JwtToken;
import org.springframework.aop.scope.ScopedProxyUtils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.net.http.HttpRequest;
import java.security.SignatureException;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class JwtRequstFilter extends OncePerRequestFilter{

    private final JwtToken jwtToken;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
            String authHeader=request.getHeader("Authorization");
            String username=null;
            String jwt=null;

            if (authHeader!=null && authHeader.startsWith("Bearer ")){
                jwt=authHeader.substring(7);
                try{
                     username=jwtToken.getUserName(jwt);
                } catch (ExpiredJwtException e){
                    System.out.println("Время жизни вышло");
                }

            }
            if (username!=null && SecurityContextHolder.getContext().getAuthentication()==null){
                UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                        username,
                        null,
                        jwtToken.getUserRole(jwt).stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList())
                );
                SecurityContextHolder.getContext().setAuthentication(token);
            }
        filterChain.doFilter(request,response);
    }
}
