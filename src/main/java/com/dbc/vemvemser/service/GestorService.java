package com.dbc.vemvemser.service;


import com.dbc.vemvemser.dto.GestorCreateDto;
import com.dbc.vemvemser.dto.GestorDto;
import com.dbc.vemvemser.dto.LoginCreateDto;
import com.dbc.vemvemser.entity.CargoEntity;
import com.dbc.vemvemser.entity.GestorEntity;
import com.dbc.vemvemser.exception.RegraDeNegocioException;
import com.dbc.vemvemser.repository.GestorRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GestorService {

    private final GestorRepository gestorRepository;

    private final CargoService cargoService;

    private final ObjectMapper objectMapper;


    public GestorDto autenticarUsuario(GestorCreateDto gestorCreateDto) throws RegraDeNegocioException {
        GestorEntity gestorEntity = gestorRepository.findGestorEntityByEmailAndAndSenha(gestorCreateDto.getEmail(),gestorCreateDto.getSenha())
                .orElseThrow(()-> new RegraDeNegocioException("Usuario ou senha invalido!"));
        return objectMapper.convertValue(gestorEntity,GestorDto.class);
    }

    public GestorDto cadastrar(GestorCreateDto gestorCreateDto) throws RegraDeNegocioException {
        CargoEntity cargo = cargoService.findById(gestorCreateDto.getTipoCargo());
        GestorEntity gestorEntity = new GestorEntity();
        gestorEntity.setNome(gestorCreateDto.getNome());
        gestorEntity.setCargoEntity(cargo);
        gestorEntity.setEmail(gestorCreateDto.getEmail());
        gestorEntity.setSenha(gestorCreateDto.getSenha());


        return objectMapper.convertValue(gestorRepository.save(gestorEntity), GestorDto.class);
    }

}
