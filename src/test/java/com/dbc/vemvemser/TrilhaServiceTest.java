package com.dbc.vemvemser;


import com.dbc.vemvemser.dto.TrilhaCreateDto;
import com.dbc.vemvemser.dto.TrilhaDto;
import com.dbc.vemvemser.entity.TrilhaEntity;
import com.dbc.vemvemser.exception.RegraDeNegocioException;
import com.dbc.vemvemser.repository.TrilhaRepository;
import com.dbc.vemvemser.service.TrilhaService;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.hibernate.mapping.Collection;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;

import javax.validation.constraints.AssertTrue;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class TrilhaServiceTest {

    @InjectMocks
    private TrilhaService trilhaService;
    private ObjectMapper objectMapper = new ObjectMapper();
    @Mock
    private TrilhaRepository trilhaRepository;


    @Before
    public void init() {
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        ReflectionTestUtils.setField(trilhaService, "objectMapper", objectMapper);
    }


    @Test
    public void deveTestarCreateComSucesso() {
        TrilhaCreateDto trilhaCreateDto = getTrilhaCreateDto();
        TrilhaEntity trilhaEntity = getTrilhaEntity();
        TrilhaDto trilhaDto = getTrilhaDto();

        when(trilhaRepository.save(any())).thenReturn(trilhaEntity);

        TrilhaDto trilhaDto1 = trilhaService.create(trilhaCreateDto);
        assertEquals(trilhaDto1.getNome(), trilhaCreateDto.getNome());
    }


    @Test
    public void deveTestarDeleteComSucesso() throws RegraDeNegocioException {
        TrilhaCreateDto trilhaCreateDto = getTrilhaCreateDto();
        TrilhaEntity trilhaEntity = getTrilhaEntity();
        TrilhaDto trilhaDto = getTrilhaDto();

        when(trilhaRepository.findById(anyInt())).thenReturn(Optional.of(trilhaEntity));

        trilhaService.delete(1);

        verify(trilhaRepository, times(1)).deleteById(anyInt());
    }


    @Test
    public void deveTestarListComSucesso(){
        List<TrilhaEntity> trilhaEntity = List.of(getTrilhaEntity());

        when(trilhaRepository.findAll()).thenReturn(trilhaEntity);

        List<TrilhaDto> trilhaDtos = trilhaService.list();

        assertEquals(trilhaDtos.size(), trilhaEntity.size());
    }


    @Test
    public void deveTestarFindListaTrilhasComSucesso() throws RegraDeNegocioException{
        List<Integer> trilhas = List.of(1);
        TrilhaEntity trilhaEntity = getTrilhaEntity();

        when(trilhaRepository.findById(anyInt())).thenReturn(Optional.of(trilhaEntity));

        Set<TrilhaEntity> setTrilha = trilhaService.findListaTrilhas(trilhas);

        assertEquals(setTrilha.size(),1);
        assertTrue(setTrilha.contains(trilhaEntity));
    }
    @Test
    public void deveTestarConvertToEntityComSucesso(){
        TrilhaDto trilhaDto = getTrilhaDto();

        Set<TrilhaEntity> trilhaEntity1 = trilhaService.convertToEntity(Set.of(trilhaDto));
        assertEquals(trilhaEntity1.size(),1);
        assertEquals(trilhaEntity1.iterator().next().getIdTrilha(),1);

    }
    private static TrilhaCreateDto getTrilhaCreateDto() {
        TrilhaCreateDto trilhaCreateDto = new TrilhaCreateDto();
        trilhaCreateDto.setNome("ROLE_GERENTE");
        return trilhaCreateDto;
    }

    private static TrilhaEntity getTrilhaEntity() {
        TrilhaEntity trilhaEntity = new TrilhaEntity();
        trilhaEntity.setIdTrilha(1);
        trilhaEntity.setNome("ROLE_GERENTE");
        return trilhaEntity;
    }

    private static TrilhaDto getTrilhaDto() {
        TrilhaDto trilhaDto = new TrilhaDto();
        trilhaDto.setIdTrilha(1);
        trilhaDto.setNome("ROLE_GERENTE");
        return trilhaDto;
    }

}
