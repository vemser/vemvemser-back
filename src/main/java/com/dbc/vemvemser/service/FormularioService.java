package com.dbc.vemvemser.service;


import com.dbc.vemvemser.dto.FormularioCreateDto;
import com.dbc.vemvemser.dto.FormularioDto;
import com.dbc.vemvemser.entity.FormularioEntity;
import com.dbc.vemvemser.repository.FormularioRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FormularioService {

    private final FormularioRepository formularioRepository;

    private final ObjectMapper objectMapper;

    public FormularioDto create (FormularioCreateDto formularioCreateDto){

        FormularioEntity formulario = objectMapper.convertValue(formularioCreateDto,FormularioEntity.class);


        formularioRepository.save(formulario);


        FormularioDto formularioDto = objectMapper.convertValue(formularioCreateDto, FormularioDto.class);

        return formularioDto;
    }

    public List<FormularioDto> list(){
        return formularioRepository.findAll().stream()
                .map(formularioEntity -> objectMapper.convertValue(formularioEntity, FormularioDto.class)).toList();
    }
}