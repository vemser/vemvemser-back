package com.dbc.vemvemser.service;

import com.dbc.vemvemser.dto.CargoDto;
import com.dbc.vemvemser.dto.GestorCreateDto;
import com.dbc.vemvemser.dto.GestorDto;
import com.dbc.vemvemser.entity.CargoEntity;
import com.dbc.vemvemser.entity.GestorEntity;
import com.dbc.vemvemser.exception.RegraDeNegocioException;
import com.dbc.vemvemser.repository.CargoRepository;
import com.dbc.vemvemser.repository.GestorRepository;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import factory.CandidatoFactory;
import factory.CargoFactory;
import factory.GestorFactory;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Optional;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class GestorServiceTest {

    @InjectMocks
    private GestorService gestorService;

    @Mock
    private GestorRepository gestorRepository;

    @Mock
    private CargoService cargoService;

    @Mock
    private CargoRepository cargoRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Before
    public void init() {
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        ReflectionTestUtils.setField(gestorService, "objectMapper", objectMapper);
    }

//    @Test
//    public void deveTestarCadastrarComSucesso() throws RegraDeNegocioException {
//        GestorCreateDto gestorCreateDto = GestorFactory.getGestorCreateDto();
//        GestorEntity gestorEntity = GestorFactory.getGestorEntity();
//
//        CargoEntity cargoEntity =CargoFactory.getCargoEntity();
//        CargoDto cargoDto = CargoFactory.getCargoDto();
//
//        when(cargoService.convertToEntity(any()))
//                .thenReturn(cargoEntity);
//
//        when(gestorService.convertToEntity(any()))
//                .thenReturn(gestorEntity);
//
//        when(cargoRepository.findById(anyInt()))
//                .thenReturn(Optional.of(cargoEntity));
//
//        when(cargoService.findById(anyInt()))
//                .thenReturn(cargoEntity);
//
//        when(cargoService.convertToDto(any()))
//                .thenReturn(cargoDto);
//
//        when(passwordEncoder.encode(any()))
//                .thenReturn(anyString());
//
//        when(gestorRepository.save(any()))
//                .thenReturn(gestorEntity);
//
//        GestorDto gestorDtoRetorno = gestorService.cadastrar(gestorCreateDto);
//
//        Assert.assertNotNull(gestorDtoRetorno);
//
//    }

    @Test(expected = RegraDeNegocioException.class)
    public void deveTestarCadastrarComException() throws RegraDeNegocioException {
        GestorCreateDto gestorCreateDto = GestorFactory.getGestorCreateDto();
        gestorCreateDto.setEmail("gestor@gmail.com");

        gestorService.cadastrar(gestorCreateDto);
    }

    @Test
    public void deveTestarFindDtoByIdComSucesso() throws RegraDeNegocioException {

        GestorEntity gestorEntity = GestorFactory.getGestorEntity();

        when(gestorRepository.findById(any()))
                .thenReturn(Optional.of(gestorEntity));

      GestorDto gestorDtoRetorno= gestorService.findDtoById(1);

      Assert.assertNotNull(gestorDtoRetorno);
    }


}
