package com.dbc.vemvemser.service;

import com.dbc.vemvemser.dto.LoginCreateDto;
import com.dbc.vemvemser.dto.LoginDto;
import com.dbc.vemvemser.entity.LoginEntity;
import com.dbc.vemvemser.exception.RegraDeNegocioException;
import com.dbc.vemvemser.repository.LoginRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final ObjectMapper objectMapper;
    private final LoginRepository loginRepository;

    public LoginDto autenticarUsuario(LoginCreateDto loginCreateDto) throws RegraDeNegocioException {
        LoginEntity loginEntity = loginRepository.findLoginEntityByEmailAndSenha(loginCreateDto.getEmail(),loginCreateDto.getSenha())
                .orElseThrow(()-> new RegraDeNegocioException("Usuario ou senha invalido!"));
        return objectMapper.convertValue(loginEntity,LoginDto.class);
    }

}
