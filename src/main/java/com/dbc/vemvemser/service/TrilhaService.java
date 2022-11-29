package com.dbc.vemvemser.service;

import com.dbc.vemvemser.dto.TrilhaCreateDto;
import com.dbc.vemvemser.dto.TrilhaDto;
import com.dbc.vemvemser.entity.TrilhaEntity;
import com.dbc.vemvemser.exception.RegraDeNegocioException;
import com.dbc.vemvemser.repository.TrilhaRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TrilhaService {

    private final ObjectMapper objectMapper;
    private final TrilhaRepository trilhaRepository;

    public TrilhaDto create(TrilhaCreateDto trilhaCreateDto) {

        TrilhaEntity trilhaEntity = objectMapper.convertValue(trilhaCreateDto, TrilhaEntity.class);
        trilhaRepository.save(trilhaEntity);

        TrilhaDto trilhaDto = converterParaDTO(trilhaEntity);

        return trilhaDto;
    }


    public List<TrilhaDto> list() {
        return trilhaRepository.findAll().stream()
                .map(inscricaoEntity -> converterParaDTO(inscricaoEntity))
                .toList();
    }

    public TrilhaDto findDtoByid(Integer idTrilha) throws RegraDeNegocioException {
        TrilhaEntity trilhaEntity = findById(idTrilha);

        TrilhaDto trilhaDto = converterParaDTO(trilhaEntity);

        return trilhaDto;
    }

    public void delete(Integer id) throws RegraDeNegocioException {
        TrilhaEntity trilhaEntity = findById(id);
        trilhaRepository.deleteById(id);
    }


    private TrilhaEntity findById(Integer idTrilha) throws RegraDeNegocioException {
        return trilhaRepository.findById(idTrilha)
                .orElseThrow(() -> new RegraDeNegocioException("ID_Inscrição inválido"));

    }

    public Set<TrilhaEntity> findListaTrilhas(List<Integer> idTrilhas) throws RegraDeNegocioException {
        List<TrilhaEntity> trilhaEntities = new ArrayList<>();
        for(Integer id:idTrilhas){
            trilhaEntities.add(findById(id));
        }
        return trilhaEntities.stream().collect(Collectors.toSet());
    }


    private TrilhaDto converterParaDTO(TrilhaEntity trilhaEntity) {
        TrilhaDto trilhaDto = objectMapper.convertValue(trilhaEntity, TrilhaDto.class);
        return trilhaDto;
    }

}
