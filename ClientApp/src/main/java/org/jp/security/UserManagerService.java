package org.jp.security;

import lombok.RequiredArgsConstructor;
import org.jp.entity.Authority;
import org.jp.entity.UserClientEntity;
import org.jp.repository.AuthorityRepository;
import org.jp.repository.UserRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserManagerService implements UserDetailsService {
private final UserRepository userRepository;
private final AuthorityRepository authorityRepository;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return this.userRepository. findByUsername(username)
                .map(user-> User.builder()
                        .username(user.getUsername())
                        .password(user.getPassword())
                        .authorities(user.getAuthorities().stream()
                                .map(Authority::getAuthority)
                                .map(SimpleGrantedAuthority::new)
                                .toList())
                        .build())
                .orElseThrow(()->new UsernameNotFoundException("User %s not found".formatted(username)));
    }


    public void createNewUser(UserClientEntity user){
        user.setAuthorities(List.of(authorityRepository.findByAuthority("ROLE_USER").get()));
        userRepository.save(user);
    }
}
