package com.dbc.vemvemser.service;


import com.dbc.vemvemser.dto.FormularioCreateDto;
import com.dbc.vemvemser.dto.FormularioDto;
import com.dbc.vemvemser.entity.FormularioEntity;
import com.dbc.vemvemser.exception.RegraDeNegocioException;
import com.dbc.vemvemser.repository.FormularioRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FormularioService {

    private final FormularioRepository formularioRepository;
    private final ObjectMapper objectMapper;

    private final CandidatoService candidatoService;

    public FormularioDto create(FormularioCreateDto formularioCreateDto) {
        FormularioEntity formulario = objectMapper.convertValue(formularioCreateDto, FormularioEntity.class);
        FormularioEntity formularioEntity1 = formularioRepository.save(formulario);
        FormularioDto formularioDto = objectMapper.convertValue(formularioEntity1, FormularioDto.class);
        return formularioDto;
    }

    public List<FormularioDto> list() {
        return formularioRepository.findAll().stream()
                .map(formularioEntity -> objectMapper.convertValue(formularioEntity, FormularioDto.class)).toList();
    }

    private FormularioEntity findById(Integer idFormulario) throws RegraDeNegocioException {
        return formularioRepository.findById(idFormulario)
                .orElseThrow(() -> new RegraDeNegocioException("Erro ao buscar Formulario"));
    }

    public void deleteById(Integer idFormulario) throws RegraDeNegocioException {
        findById(idFormulario);
        formularioRepository.deleteById(idFormulario);
    }

    public FormularioDto update(Integer idFormulario, FormularioCreateDto formularioCreateDto) throws RegraDeNegocioException {
        FormularioEntity formulario = findById(idFormulario);
        FormularioEntity formulario1 = objectMapper.convertValue(formularioCreateDto, FormularioEntity.class);
        formulario1.setIdFormulario(idFormulario);
        FormularioEntity formularioEntity = formularioRepository.save(formulario1);
        return objectMapper.convertValue(formularioEntity, FormularioDto.class);
    }

    public FormularioDto updateCurriculo(File file, Integer idFormulario) throws RegraDeNegocioException {
        FormularioEntity formulario = findById(idFormulario);

        formulario.setCurriculo(file);

        FormularioEntity formularioRetorno = formularioRepository.save(formulario);

        FormularioDto formularioDto = objectMapper.convertValue(formularioRetorno, FormularioDto.class);

        return formularioDto;
    }

}