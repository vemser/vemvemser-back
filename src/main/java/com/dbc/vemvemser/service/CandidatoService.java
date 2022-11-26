package com.dbc.vemvemser.service;

import com.dbc.vemvemser.dto.CandidatoCreateDto;
import com.dbc.vemvemser.dto.CandidatoDto;
import com.dbc.vemvemser.entity.CandidatoEntity;
import com.dbc.vemvemser.repository.CandidatoRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CandidatoService {

    private final CandidatoRepository candidatoRepository;

    private final ObjectMapper objectMapper;


    public CandidatoDto cadastro(CandidatoCreateDto candidatoCreateDto) {

        CandidatoEntity candidatoEntity = objectMapper.convertValue(candidatoCreateDto, CandidatoEntity.class);

        return objectMapper.convertValue(candidatoRepository.save(candidatoEntity), CandidatoDto.class);
    }


}
