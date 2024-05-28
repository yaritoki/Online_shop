package org.jp.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.jp.dtos.JwtResponse;
import org.jp.exceptions.AppError;
import org.jp.security.UserManagerService;
import org.jp.utils.JwtsToken;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.time.Duration;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor

public class JwtTokenFilter extends OncePerRequestFilter {
    private final JwtsToken jwtsToken;





    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String jwtToken = generateJwtToken();

        // Установка JWT токена в куки
        Cookie cookie = new Cookie("JWT_TOKEN", jwtToken);
        response.addCookie(cookie);

        filterChain.doFilter(request, response);
    }

    private String generateJwtToken() {
        String token=null;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            System.out.println( authentication.getAuthorities());
            token = jwtsToken.genereteToken(authentication.getName(), authentication.getAuthorities()
                    .stream()
                    .map(GrantedAuthority::getAuthority).collect(Collectors.toList()));

        }
        return token;
    }
}
