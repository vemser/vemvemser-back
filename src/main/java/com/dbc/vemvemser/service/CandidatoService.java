package com.dbc.vemvemser.service;

import com.dbc.vemvemser.dto.CandidatoCreateDto;
import com.dbc.vemvemser.dto.CandidatoDto;
import com.dbc.vemvemser.dto.FormularioDto;
import com.dbc.vemvemser.dto.PageDto;
import com.dbc.vemvemser.entity.CandidatoEntity;
import com.dbc.vemvemser.entity.FormularioEntity;
import com.dbc.vemvemser.enums.TipoMarcacao;
import com.dbc.vemvemser.exception.RegraDeNegocioException;
import com.dbc.vemvemser.repository.CandidatoRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CandidatoService {

    private static final int DESCENDING = 1;
    private final CandidatoRepository candidatoRepository;
    private final FormularioService formularioService;
    private final ObjectMapper objectMapper;


    public CandidatoDto cadastro(CandidatoCreateDto candidatoCreateDto) throws RegraDeNegocioException {
        if(!candidatoRepository.findCandidatoEntitiesByFormulario_IdFormulario(candidatoCreateDto.getIdFormulario()).isEmpty()){
            throw new RegraDeNegocioException("Formulario cadastrado para outro candidato");
        }
        CandidatoEntity candidatoEntity = convertToEntity(candidatoCreateDto);
        CandidatoDto candidatoDto = convertToDto(candidatoRepository.save(candidatoEntity));
        candidatoDto.setFormulario(formularioService.convertToDto(candidatoEntity.getFormulario()));
        return candidatoDto;
    }


    public void deleteById(Integer idCandidato) throws RegraDeNegocioException {
        findById(idCandidato);
        candidatoRepository.deleteById(idCandidato);
    }

    public CandidatoDto update(Integer idCandidato, CandidatoCreateDto candidatoCreateDto) throws RegraDeNegocioException {
        findById(idCandidato);
        CandidatoEntity candidatoEntity = convertToEntity(candidatoCreateDto);
        candidatoEntity.setIdCandidato(idCandidato);
        CandidatoEntity candidatoEntity1 = candidatoRepository.save(candidatoEntity);
        return convertToDto(candidatoEntity1);
    }

    public CandidatoEntity findById(Integer idCandidato) throws RegraDeNegocioException {
        return candidatoRepository.findById(idCandidato)
                .orElseThrow(() -> new RegraDeNegocioException("Erro ao buscar candidato!"));
    }

    public CandidatoDto findDtoById(Integer idCandidato) throws RegraDeNegocioException {
        return convertToDto(findById(idCandidato));
    }

    public CandidatoDto findCandidatoDtoByEmail(String email) throws RegraDeNegocioException {
       Optional candidato= candidatoRepository.findCandidatoEntitiesByEmail(email);
       if(candidato.isEmpty()) {
        throw new RegraDeNegocioException("Candidato não encontrado!");
       }
        CandidatoDto candidatoDto = objectMapper.convertValue(candidato, CandidatoDto.class);
        CandidatoDto candidatoDtoRetorno = findDtoById(candidatoDto.getIdCandidato());

        return candidatoDtoRetorno;
    }

    public PageDto<CandidatoDto> listaAllPaginado(Integer pagina, Integer tamanho, String sort, int order) {
        Sort ordenacao = Sort.by(sort).ascending();
        if (order == DESCENDING) {
            ordenacao = Sort.by(sort).descending();
        }
        PageRequest pageRequest = PageRequest.of(pagina, tamanho, ordenacao);
        Page<CandidatoEntity> paginaCandidatoEntity = candidatoRepository.findAll(pageRequest);

        List<CandidatoDto> candidatoDtos = paginaCandidatoEntity.getContent().stream()
                .map(candidatoEntity -> {
                    CandidatoDto candidatoDto = convertToDto(candidatoEntity);
                    candidatoDto.setFormulario(formularioService.convertToDto(candidatoEntity.getFormulario()));
                    return candidatoDto;
                }).toList();

        return new PageDto<>(paginaCandidatoEntity.getTotalElements(),
                paginaCandidatoEntity.getTotalPages(),
                pagina,
                tamanho,
                candidatoDtos);
    }


    public CandidatoDto convertToDto(CandidatoEntity candidatoEntity) {
        CandidatoDto candidatoDto = objectMapper.convertValue(candidatoEntity, CandidatoDto.class);
        candidatoDto.setFormulario(formularioService.convertToDto(candidatoEntity.getFormulario()));
        return candidatoDto;
    }

    private CandidatoEntity convertToEntity(CandidatoCreateDto candidatoCreateDto) throws RegraDeNegocioException {
        CandidatoEntity candidatoEntity = objectMapper.convertValue(candidatoCreateDto, CandidatoEntity.class);
        candidatoEntity.setPcd(candidatoCreateDto.isPcdboolean() ? TipoMarcacao.T : TipoMarcacao.F);
        candidatoEntity.setFormulario(formularioService.convertToEntity(formularioService.findDtoById(candidatoCreateDto.getIdFormulario())));
        return candidatoEntity;
    }

    public CandidatoEntity convertToEntity(CandidatoDto candidatoDto) {
        CandidatoEntity candidatoEntity = objectMapper.convertValue(candidatoDto, CandidatoEntity.class);
        candidatoEntity.setFormulario(formularioService.convertToEntity(candidatoDto.getFormulario()));
        return candidatoEntity;
    }


}

