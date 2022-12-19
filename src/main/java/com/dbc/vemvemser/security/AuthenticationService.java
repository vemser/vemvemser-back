package com.dbc.vemvemser.security;

import com.dbc.vemvemser.entity.GestorEntity;
import com.dbc.vemvemser.exception.RegraDeNegocioException;
import com.dbc.vemvemser.service.GestorService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService implements UserDetailsService {

    private final GestorService gestorService;

    // FIXME pode retornar direto a var
    @Override
    public UserDetails loadUserByUsername(String loginUsername) throws UsernameNotFoundException {
        try {
            GestorEntity usuarioOptional = gestorService.findByEmail(loginUsername);
            return usuarioOptional;
        } catch (RegraDeNegocioException e) {
            throw new UsernameNotFoundException(e.getMessage());
        }
    }
}
