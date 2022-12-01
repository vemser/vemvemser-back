package com.dbc.vemvemser.service;


import com.dbc.vemvemser.dto.*;
import com.dbc.vemvemser.entity.*;
import com.dbc.vemvemser.enums.TipoEmail;
import com.dbc.vemvemser.enums.TipoMarcacao;
import com.dbc.vemvemser.enums.TipoTurno;
import com.dbc.vemvemser.exception.RegraDeNegocioException;
import com.dbc.vemvemser.repository.AvaliacaoRepository;
import com.dbc.vemvemser.service.*;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import factory.AvaliacaoFactory;
import factory.GestorFactory;
import factory.InscricaoFactory;
import io.jsonwebtoken.lang.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDate;
import java.util.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class AvaliacaoServiceTest {

    @InjectMocks
    private AvaliacaoService avaliacaoService;

    @Mock
    private InscricaoService inscricaoService;

    @Mock
    private EmailService emailService;
    @Mock
    private GestorService gestorService;

    @Mock
    private FormularioService formularioService;

    @Mock
    private CargoService cargoService;

    @Mock
    private CandidatoService candidatoService;
    @Mock
    private AvaliacaoRepository avaliacaoRepository;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Before
    public void init() {
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        ReflectionTestUtils.setField(avaliacaoService, "objectMapper", objectMapper);
    }

    @Test
    public void deveTestarCreateComSucesso() throws RegraDeNegocioException {
        AvaliacaoCreateDto avaliacaoCreateDto = AvaliacaoFactory.getAvaliacaoCreateDto();
        when(avaliacaoRepository.findAvaliacaoEntitiesByInscricao_IdInscricao(anyInt())).thenReturn(Optional.empty());
        when(inscricaoService.findDtoByid(anyInt())).thenReturn(InscricaoFactory.getInscricaoDto());
        when(inscricaoService.convertToEntity(any())).thenReturn(InscricaoFactory.getInscricaoEntity());
        when(gestorService.findDtoById(anyInt())).thenReturn(GestorFactory.getGestorDto());
        when(gestorService.convertToEntity(any())).thenReturn(GestorFactory.getGestorEntity());
        when(gestorService.convertToDto(any())).thenReturn(GestorFactory.getGestorDto());
        when(avaliacaoRepository.save(any())).thenReturn(AvaliacaoFactory.getAvaliacaoEntity());

        AvaliacaoDto avaliacaoDtoRetorno = avaliacaoService.create(avaliacaoCreateDto);

        Assert.notNull(avaliacaoDtoRetorno);
        verify(emailService, times(1)).sendEmail(any(), any());

    }

    @Test(expected = RegraDeNegocioException.class)
    public void deveTestarCreateComExcepetion() throws RegraDeNegocioException {
        AvaliacaoCreateDto avaliacaoCreateDto = new AvaliacaoCreateDto();
        avaliacaoCreateDto.setAprovadoBoolean(true);
        avaliacaoCreateDto.setIdInscricao(1);


        AvaliacaoEntity avaliacaoEntity = new AvaliacaoEntity();
        avaliacaoEntity.setAprovado(TipoMarcacao.T);

        Optional avaliacao = Optional.of(avaliacaoEntity);

        when(avaliacaoRepository.findAvaliacaoEntitiesByInscricao_IdInscricao(anyInt())).thenReturn(avaliacao);

        avaliacaoService.create(avaliacaoCreateDto);

        verify(avaliacaoRepository, times(1)).save(any());
    }

    @Test
    public void deveTestarListComSucesso() {
        AvaliacaoEntity avaliacaoEntity = AvaliacaoFactory.getAvaliacaoEntity();

        when(avaliacaoRepository.findAll()).thenReturn(List.of(avaliacaoEntity));

        avaliacaoService.list();

        verify(avaliacaoRepository, times(1)).findAll();
    }

//    @Test
//    public void deveTestarUpdateComSucesso() throws RegraDeNegocioException {
//        AvaliacaoEntity avaliacaoEntity = getAvaliacaoEntity();
//        Optional<AvaliacaoEntity> avaliacao = Optional.of(avaliacaoEntity);
//
//        AvaliacaoDto avaliacaoDto = getAvaliacaoDto();
//
//        AvaliacaoCreateDto avaliacaoCreateDto = getAvaliacaoCreateDto();
//
//        GestorDto gestorDto = getGestorDto();
//
//        InscricaoDto inscricaoDto = getInscricaoDto();
//
//        CargoDto cargoDto =getCargoDto();
//
//        CandidatoDto candidatoDto = getCandidatoDto();
//
//        FormularioDto formularioDto = getFormularioDto();
//
//        when(avaliacaoRepository.findById(anyInt())).thenReturn(avaliacao);
//        when(avaliacaoRepository.save(any())).thenReturn(avaliacaoEntity);
//        when(cargoService.convertToDto(any())).thenReturn(cargoDto);
//        when(formularioService.convertToDto(any())).thenReturn(formularioDto);
//        when(candidatoService.convertToDto(any())).thenReturn(candidatoDto);
//        when(inscricaoService.converterParaDTO(any())).thenReturn(inscricaoDto);
//        when(gestorService.convertToDto(any())).thenReturn(gestorDto);
//        when(avaliacaoService.convertToDto(any())).thenReturn(avaliacaoDto);
//
//
//        AvaliacaoDto avaliacaoRetorno = avaliacaoService.update(1, avaliacaoCreateDto);
//
//        Assert.notNull(avaliacaoRetorno);
//    }



    @Test
    public void deveTestarDeleteByIdComSucesso() throws RegraDeNegocioException {

        AvaliacaoEntity avaliacaoEntity= AvaliacaoFactory.getAvaliacaoEntity();

        when(avaliacaoRepository.findById(anyInt())).thenReturn(Optional.of(avaliacaoEntity));

        avaliacaoService.deleteById(10);
    }


}
