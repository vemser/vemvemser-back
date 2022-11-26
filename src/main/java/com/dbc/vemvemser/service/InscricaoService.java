package com.dbc.vemvemser.service;

import com.dbc.vemvemser.dto.InscricaoCreateDto;
import com.dbc.vemvemser.dto.InscricaoDto;
import com.dbc.vemvemser.entity.InscricaoEntity;
import com.dbc.vemvemser.exception.RegraDeNegocioException;
import com.dbc.vemvemser.repository.InscricaoRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InscricaoService {

    private final InscricaoRepository inscricaoRepository;
    private final ObjectMapper objectMapper;

    public InscricaoDto create (InscricaoCreateDto inscricaoCreateDto){

        InscricaoEntity inscricaoEntity = objectMapper.convertValue(inscricaoCreateDto, InscricaoEntity.class);
        inscricaoRepository.save(inscricaoEntity);

        InscricaoDto inscricaoDto = converterParaDTO(inscricaoEntity);

        return inscricaoDto;
    }


        public InscricaoDto update (Integer idInscricao ,InscricaoCreateDto inscricaoCreateDto) throws RegraDeNegocioException {

        InscricaoEntity inscricaoEntity = findById(idInscricao);
        if (inscricaoEntity == null) {
            throw new RegraDeNegocioException("");
        }
        inscricaoEntity.setAvaliacao(inscricaoCreateDto.getAvaliacao());

       InscricaoDto inscricaoDto = converterParaDTO(inscricaoEntity);

        return inscricaoDto;
    }

    public InscricaoDto listById (Integer idInscricao) throws RegraDeNegocioException {
       InscricaoEntity inscricaoEntity = findById(idInscricao);

       InscricaoDto inscricaoDto = converterParaDTO(inscricaoEntity);

       return inscricaoDto;
    }

    public void delete(Integer id) throws RegraDeNegocioException {
        InscricaoEntity inscricaoEntity = findById(id);
        inscricaoRepository.deleteById(id);
    }


    private InscricaoEntity findById(Integer idInscricao) throws RegraDeNegocioException{
        InscricaoEntity inscricaoEntity = inscricaoRepository.findByIdInscricao(idInscricao);

        if (inscricaoEntity == null){
            throw new RegraDeNegocioException("ID_Inscrição inválido");
        }

        return  inscricaoEntity;
    }

    private InscricaoDto converterParaDTO (InscricaoEntity inscricaoEntity){
        InscricaoDto inscricaoDto = objectMapper.convertValue(inscricaoEntity, InscricaoDto.class);
        return inscricaoDto;
    }

}
