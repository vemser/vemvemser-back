package com.dbc.vemvemser.service;

import com.dbc.vemvemser.dto.CandidatoCreateDto;
import com.dbc.vemvemser.dto.CandidatoDto;
import com.dbc.vemvemser.dto.FormularioDto;
import com.dbc.vemvemser.dto.PageDto;
import com.dbc.vemvemser.entity.CandidatoEntity;
import com.dbc.vemvemser.exception.RegraDeNegocioException;
import com.dbc.vemvemser.repository.CandidatoRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CandidatoService {

    private final CandidatoRepository candidatoRepository;

    private final FormularioService formularioService;

    private final ObjectMapper objectMapper;


    public CandidatoDto cadastro(CandidatoCreateDto candidatoCreateDto) throws RegraDeNegocioException {
        CandidatoEntity candidatoEntity = objectMapper.convertValue(candidatoCreateDto, CandidatoEntity.class);
        candidatoEntity.setFormulario(formularioService.findById(candidatoCreateDto.getIdFormulario()));
        return objectMapper.convertValue(candidatoRepository.save(candidatoEntity), CandidatoDto.class);
    }

    public List<CandidatoDto> listAll() {
        return candidatoRepository.findAll().stream()
                .map(candidatoEntity -> {
                  CandidatoDto candidatoDto = objectMapper.convertValue(candidatoEntity, CandidatoDto.class);
                  candidatoDto.setFormulario(objectMapper.convertValue(candidatoEntity.getFormulario(), FormularioDto.class));
                    return candidatoDto;
                })
                .toList();
    }

    public void deleteById(Integer idCandidato) throws RegraDeNegocioException{
        findById(idCandidato);
        candidatoRepository.deleteById(idCandidato);
    }

    public CandidatoDto update(Integer idCandidato,CandidatoCreateDto candidatoCreateDto) throws RegraDeNegocioException{
        findById(idCandidato);
        CandidatoEntity candidatoEntity = objectMapper.convertValue(candidatoCreateDto,CandidatoEntity.class);
        candidatoEntity.setIdCandidato(idCandidato);
        CandidatoEntity candidatoEntity1 = candidatoRepository.save(candidatoEntity);
        return objectMapper.convertValue(candidatoEntity1,CandidatoDto.class);
    }

    private CandidatoEntity findById(Integer idCandidato) throws RegraDeNegocioException {
        return candidatoRepository.findById(idCandidato)
                .orElseThrow(() -> new RegraDeNegocioException("Erro ao buscar candidato!"));
    }

    public PageDto<CandidatoDto> listPessoaIndicacaoPaginada(Integer pagina, Integer tamanho) {
        PageRequest pageRequest = PageRequest.of(pagina, tamanho);
        Page<CandidatoEntity> paginaCandidatoEntity = candidatoRepository.findAll(pageRequest);

        List<CandidatoDto> candidatoDtos = paginaCandidatoEntity.getContent().stream()
                .map(candidatoEntity -> {
                    CandidatoDto candidatoDto = objectMapper.convertValue(candidatoEntity, CandidatoDto.class);
                    candidatoDto.setNome(candidatoEntity.getNome());
                    return candidatoDto;
                }).toList();

        return new PageDto<>(paginaCandidatoEntity.getTotalElements(),
                paginaCandidatoEntity.getTotalPages(),
                pagina,
                tamanho,
                candidatoDtos);
    }

}

