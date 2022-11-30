package com.dbc.vemvemser.service;


import com.dbc.vemvemser.dto.CargoDto;
import com.dbc.vemvemser.entity.CargoEntity;
import com.dbc.vemvemser.exception.RegraDeNegocioException;
import com.dbc.vemvemser.repository.CargoRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CargoService {
    private final CargoRepository cargoRepository;
    private final ObjectMapper objectMapper;

    public CargoEntity findById(Integer id) throws RegraDeNegocioException {
        return cargoRepository.findById(id)
                .orElseThrow(() -> new RegraDeNegocioException("Cargo não encontrado"));
    }

    public CargoDto convertToDto(CargoEntity cargo) {
        return objectMapper.convertValue(cargo, CargoDto.class);
    }

    public CargoEntity convertToEntity(CargoDto cargo) {
        return objectMapper.convertValue(cargo, CargoEntity.class);
    }

}
