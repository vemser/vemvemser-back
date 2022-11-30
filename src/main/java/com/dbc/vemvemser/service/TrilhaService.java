package com.dbc.vemvemser.service;

import com.dbc.vemvemser.dto.TrilhaCreateDto;
import com.dbc.vemvemser.dto.TrilhaDto;
import com.dbc.vemvemser.entity.TrilhaEntity;
import com.dbc.vemvemser.exception.RegraDeNegocioException;
import com.dbc.vemvemser.repository.TrilhaRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TrilhaService {
    private final ObjectMapper objectMapper;
    private final TrilhaRepository trilhaRepository;

    public TrilhaDto create(TrilhaCreateDto trilhaCreateDto) {
        TrilhaEntity trilhaEntity = objectMapper.convertValue(trilhaCreateDto, TrilhaEntity.class);
        trilhaRepository.save(trilhaEntity);
        TrilhaDto trilhaDto = convertToDTo(trilhaEntity);
        return trilhaDto;
    }


    public void delete(Integer id) throws RegraDeNegocioException {
        findById(id);
        trilhaRepository.deleteById(id);
    }

    public List<TrilhaDto> list() {
        return trilhaRepository.findAll().stream()
                .map(inscricaoEntity -> convertToDTo(inscricaoEntity))
                .toList();
    }


    private TrilhaEntity findById(Integer idTrilha) throws RegraDeNegocioException {
        return trilhaRepository.findById(idTrilha)
                .orElseThrow(() -> new RegraDeNegocioException("ID_Inscrição inválido"));
    }

    public Set<TrilhaEntity> findListaTrilhas(List<Integer> idTrilhas) throws RegraDeNegocioException {
        List<TrilhaEntity> trilhaEntities = new ArrayList<>();
        for (Integer id : idTrilhas) {
            trilhaEntities.add(findById(id));
        }
        return trilhaEntities.stream().collect(Collectors.toSet());
    }


    private TrilhaDto convertToDTo(TrilhaEntity trilhaEntity) {
        TrilhaDto trilhaDto = objectMapper.convertValue(trilhaEntity, TrilhaDto.class);
        return trilhaDto;
    }


    public Set<TrilhaEntity> convertToEntity(Set<TrilhaDto> trilhas) {
        Set<TrilhaEntity> trilhaEntities = trilhas.stream().map(trilhaDtos -> objectMapper.convertValue(trilhaDtos, TrilhaEntity.class))
                .collect(Collectors.toSet());
        return trilhaEntities;
    }


}
