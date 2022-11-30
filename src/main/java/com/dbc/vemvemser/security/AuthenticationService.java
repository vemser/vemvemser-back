package com.dbc.vemvemser.security;

import com.dbc.vemvemser.entity.GestorEntity;
import com.dbc.vemvemser.service.GestorService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthenticationService implements UserDetailsService {

    private final GestorService gestorService;

    @Override
    public UserDetails loadUserByUsername(String loginUsername) throws UsernameNotFoundException {
        Optional<GestorEntity> usuarioOptional = gestorService.findByEmail(loginUsername);
        return usuarioOptional
                .orElseThrow(() -> new UsernameNotFoundException("Usuário inválido"));
    }
}
