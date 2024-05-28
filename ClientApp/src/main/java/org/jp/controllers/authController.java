package org.jp.controllers;

import jakarta.servlet.http.Cookie;
import lombok.RequiredArgsConstructor;
import org.jp.dtos.JwtRequest;
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
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.client.endpoint.JwtBearerGrantRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class authController {
private final UserManagerService userService;
private final JwtsToken jwtsToken;
private final AuthenticationManager authenticationManager;
@Value("${jwt.lifetime}")
private Duration jwtLifetime;
@PostMapping("/auth")// for test jwtToken
    public ResponseEntity<?> createAuthToken(@RequestBody JwtRequest authRequest){
    try {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                authRequest.getUsername(),authRequest.getPassword()
        ));
    }
    catch (BadCredentialsException e){
        return new ResponseEntity<>(new AppError(
                HttpStatus.UNAUTHORIZED.value(),
                "Неправильный логин или пароль"),
                HttpStatus.UNAUTHORIZED);
    }
    UserDetails user=userService.loadUserByUsername(authRequest.getUsername());
    String token = jwtsToken.genereteToken(user.getUsername(),
            user.getAuthorities()
                    .stream()
                    .map(GrantedAuthority::getAuthority)
                    .collect(Collectors.toList()));
    return ResponseEntity.ok(new JwtResponse(token));
}


}
