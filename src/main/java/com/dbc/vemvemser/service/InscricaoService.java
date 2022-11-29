package com.dbc.vemvemser.service;

import com.dbc.vemvemser.dto.GestorDto;
import com.dbc.vemvemser.dto.InscricaoCreateDto;
import com.dbc.vemvemser.dto.InscricaoDto;
import com.dbc.vemvemser.dto.PageDto;
import com.dbc.vemvemser.entity.GestorEntity;
import com.dbc.vemvemser.entity.InscricaoEntity;
import com.dbc.vemvemser.exception.RegraDeNegocioException;
import com.dbc.vemvemser.repository.InscricaoRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class InscricaoService {

    private static final int ASCENDING = 0;
    private static final int DESCENDING = 1;
    private final InscricaoRepository inscricaoRepository;
    private final ObjectMapper objectMapper;

    public InscricaoDto create(InscricaoCreateDto inscricaoCreateDto) {

        InscricaoEntity inscricaoEntity = objectMapper.convertValue(inscricaoCreateDto, InscricaoEntity.class);
        inscricaoEntity.setDataInscricao(LocalDate.now());
        inscricaoRepository.save(inscricaoEntity);

        InscricaoDto inscricaoDto = converterParaDTO(inscricaoEntity);

        return inscricaoDto;
    }

//    public InscricaoDto update(Integer idInscricao, InscricaoCreateDto inscricaoCreateDto) throws RegraDeNegocioException {
//
//        InscricaoEntity inscricaoEntity = findById(idInscricao);
//        if (inscricaoEntity == null) {
//            throw new RegraDeNegocioException("");
//        }
//        inscricaoEntity.setAvaliado(inscricaoCreateDto.getAvaliacao());
//
//        InscricaoDto inscricaoDto = converterParaDTO(inscricaoEntity);
//
//        return inscricaoDto;
//    }

    public PageDto<InscricaoDto> listar(Integer pagina, Integer tamanho, String sort, int order){
        Sort ordenacao = Sort.by(sort).ascending();
        if (order == DESCENDING) {
            ordenacao = Sort.by(sort).descending();
        }
        PageRequest pageRequest = PageRequest.of(pagina, tamanho, ordenacao);
        Page<InscricaoEntity> paginaInscricaoEntities = inscricaoRepository.findAll(pageRequest);

        List<InscricaoDto> inscricaoDtos = paginaInscricaoEntities.getContent().stream()
                .map(inscricaoEntity -> objectMapper.convertValue(inscricaoEntity, InscricaoDto.class)).toList();

        return new PageDto<>(paginaInscricaoEntities.getTotalElements(),
                paginaInscricaoEntities.getTotalPages(),
                pagina,
                tamanho,
                inscricaoDtos);
    }

    public InscricaoDto findDtoByid(Integer idInscricao) throws RegraDeNegocioException {
        InscricaoEntity inscricaoEntity = findById(idInscricao);

        InscricaoDto inscricaoDto = converterParaDTO(inscricaoEntity);

        return inscricaoDto;
    }

    public void delete(Integer id) throws RegraDeNegocioException {
        InscricaoEntity inscricaoEntity = findById(id);
        inscricaoRepository.deleteById(id);
    }


    private InscricaoEntity findById(Integer idInscricao) throws RegraDeNegocioException {
        InscricaoEntity inscricaoEntity = inscricaoRepository.findByIdInscricao(idInscricao);

        if (inscricaoEntity == null) {
            throw new RegraDeNegocioException("ID_Inscrição inválido");
        }

        return inscricaoEntity;
    }

    private InscricaoDto converterParaDTO(InscricaoEntity inscricaoEntity) {
        InscricaoDto inscricaoDto = objectMapper.convertValue(inscricaoEntity, InscricaoDto.class);
        return inscricaoDto;
    }

}
