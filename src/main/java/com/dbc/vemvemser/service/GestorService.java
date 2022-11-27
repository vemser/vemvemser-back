package com.dbc.vemvemser.service;


import com.dbc.vemvemser.dto.GestorCreateDto;
import com.dbc.vemvemser.dto.GestorDto;
import com.dbc.vemvemser.entity.CargoEntity;
import com.dbc.vemvemser.entity.GestorEntity;
import com.dbc.vemvemser.exception.RegraDeNegocioException;
import com.dbc.vemvemser.repository.GestorRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public List<GestorDto> listar() {
        return gestorRepository.findAll().stream()
                .map(item -> objectMapper.convertValue(item, GestorDto.class))
                .toList();
    }

    public GestorEntity findById(Integer id) throws RegraDeNegocioException {
        return gestorRepository.findById(id)
                .orElseThrow(() -> new RegraDeNegocioException("Usuario não encontrado!"));
    }

    public GestorDto editar(Integer id, GestorCreateDto gestorAtualizar) throws RegraDeNegocioException {
        GestorEntity gestorEncontrado = findById(id);
        gestorEncontrado.setNome(gestorAtualizar.getNome());
        gestorEncontrado.setEmail(gestorAtualizar.getEmail());
        gestorEncontrado.setSenha(gestorAtualizar.getSenha());
        gestorEncontrado.setCargoEntity(cargoService.findById(gestorAtualizar.getTipoCargo()));

        gestorRepository.save(gestorEncontrado);

        GestorDto usuarioDto = objectMapper.convertValue(gestorEncontrado, GestorDto.class);

        return usuarioDto;

    }

    public void remover(Integer id) throws RegraDeNegocioException {
        GestorEntity gestorEntity = findById(id);
        GestorDto gestorDto = objectMapper.convertValue(gestorEntity, GestorDto.class);
        gestorRepository.delete(gestorEntity);

    }
}
