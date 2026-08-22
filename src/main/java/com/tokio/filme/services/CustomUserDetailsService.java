package com.tokio.filme.services;

import com.tokio.filme.entities.User;
import com.tokio.filme.repositories.UserRepository;
import com.tokio.filme.security.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("Tentando autenticar usuario: {}", username);
        User user = userRepository.findByUsername(username)
                .orElseThrow(()-> {
                    log.warn("Usuario nao encontrado: {}", username);
                    return new UsernameNotFoundException("Usuário nao encontrado");
                });

        return new CustomUserDetails(user);
    }
}
