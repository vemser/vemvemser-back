package com.dbc.vemvemser.service;


import com.dbc.vemvemser.dto.CandidatoCreateDto;
import com.dbc.vemvemser.dto.CandidatoDto;
import com.dbc.vemvemser.dto.GestorCreateDto;
import com.dbc.vemvemser.dto.GestorDto;
import com.dbc.vemvemser.entity.CandidatoEntity;
import com.dbc.vemvemser.entity.GestorEntity;
import com.dbc.vemvemser.repository.GestorRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GestorService {

    private final GestorRepository gestorRepository;

    private final ObjectMapper objectMapper;

    public GestorDto cadastro(GestorCreateDto gestorCreateDto) {
        GestorEntity gestorEntity = objectMapper.convertValue(gestorCreateDto, GestorEntity.class);

        return objectMapper.convertValue(gestorRepository.save(gestorEntity), GestorDto.class);
    }
}
