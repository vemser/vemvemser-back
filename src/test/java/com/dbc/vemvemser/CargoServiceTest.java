package com.dbc.vemvemser;

import com.dbc.vemvemser.dto.CargoDto;
import com.dbc.vemvemser.entity.CargoEntity;
import com.dbc.vemvemser.entity.GestorEntity;
import com.dbc.vemvemser.enums.TipoMarcacao;
import com.dbc.vemvemser.exception.RegraDeNegocioException;
import com.dbc.vemvemser.repository.CargoRepository;
import com.dbc.vemvemser.service.CargoService;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Optional;
import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CargoServiceTest {

    @InjectMocks
    private CargoService cargoService;

    @Mock
    private CargoRepository cargoRepository;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Before
    public void init() {
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        ReflectionTestUtils.setField(cargoService, "objectMapper", objectMapper);
    }

    @Test
    public void deveTestarFindByIdComSucesso() throws RegraDeNegocioException {
        CargoEntity cargoEntity = new CargoEntity();

        cargoEntity.setIdCargo(1);
        cargoEntity.setNome("BACKEND");
        Optional cargo = Optional.of(cargoEntity);

        when(cargoRepository.findById(anyInt())).thenReturn(cargo);


        CargoEntity cargoRetorno = cargoService.findById(1);

        Assert.assertNotNull(cargoRetorno);

    }

    @Test(expected = RegraDeNegocioException.class)
    public void deveTestarFindByIdComException() throws RegraDeNegocioException {

        Optional cargo = Optional.empty();

        when(cargoRepository.findById(anyInt())).thenReturn(cargo);


        CargoEntity cargoRetorno = cargoService.findById(1);

        Assert.assertNotNull(cargoRetorno);

    }

    @Test
    public void deveTestarConvertToDtoComSucesso(){

        CargoEntity cargoEntity = new CargoEntity();
        cargoEntity.setNome("BACKEND");
        cargoEntity.setIdCargo(1);

        CargoDto cargoDto = new CargoDto();
        cargoEntity.setNome("BACKEND");
        cargoDto.setIdCargo(1);

        CargoDto cargoDtoRetorno=cargoService.convertToDto(cargoEntity);

        Assert.assertEquals(cargoEntity.getIdCargo(),cargoDtoRetorno.getIdCargo());
    }

    @Test
    public void deveTestarConvertToEntityComSucesso(){

        CargoEntity cargoEntity = new CargoEntity();
        cargoEntity.setNome("BACKEND");
        cargoEntity.setIdCargo(1);

        CargoDto cargoDto = new CargoDto();
        cargoEntity.setNome("BACKEND");
        cargoDto.setIdCargo(1);

        CargoEntity cargoEntityRetorno=cargoService.convertToEntity(cargoDto);

        Assert.assertEquals(cargoDto.getIdCargo(),cargoEntityRetorno.getIdCargo());
    }
}
