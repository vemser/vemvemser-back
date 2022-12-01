package com.dbc.vemvemser.service;


import com.dbc.vemvemser.dto.*;
import com.dbc.vemvemser.entity.*;
import com.dbc.vemvemser.enums.TipoEmail;
import com.dbc.vemvemser.enums.TipoMarcacao;
import com.dbc.vemvemser.enums.TipoTurno;
import com.dbc.vemvemser.exception.RegraDeNegocioException;
import com.dbc.vemvemser.repository.AvaliacaoRepository;
import com.dbc.vemvemser.repository.InscricaoRepository;
import com.dbc.vemvemser.service.*;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import factory.*;
import io.jsonwebtoken.lang.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;

import javax.validation.constraints.AssertTrue;
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
    private InscricaoRepository inscricaoRepository;

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
    public void deveTestarCreateComSucessoAprovado() throws RegraDeNegocioException {
        AvaliacaoCreateDto avaliacaoCreateDto = AvaliacaoFactory.getAvaliacaoCreateDto();
        when(avaliacaoRepository.findAvaliacaoEntitiesByInscricao_IdInscricao(anyInt())).thenReturn(Optional.empty());
        when(inscricaoService.findDtoByid(anyInt())).thenReturn(InscricaoFactory.getInscricaoDto());
        when(inscricaoService.convertToEntity(any())).thenReturn(InscricaoFactory.getInscricaoEntity());
        when(inscricaoService.converterParaDTO((any()))).thenReturn(InscricaoFactory.getInscricaoDto());
        when(gestorService.findDtoById(anyInt())).thenReturn(GestorFactory.getGestorDto());
        when(gestorService.convertToEntity(any())).thenReturn(GestorFactory.getGestorEntity());
        when(gestorService.convertToDto(any())).thenReturn(GestorFactory.getGestorDto());
        when(avaliacaoRepository.save(any())).thenReturn(AvaliacaoFactory.getAvaliacaoEntityAprovado());


        AvaliacaoDto avaliacaoDtoRetorno = avaliacaoService.create(avaliacaoCreateDto);

        Assert.notNull(avaliacaoDtoRetorno);
        Assertions.assertEquals(avaliacaoDtoRetorno.getAprovado(), TipoMarcacao.T);
        verify(emailService, times(1)).sendEmail(any(), any());


    }

    @Test
    public void deveTestarCreateComSucessoReprovado() throws RegraDeNegocioException {
        AvaliacaoCreateDto avaliacaoCreateDto = AvaliacaoFactory.getAvaliacaoCreateDto();

        when(avaliacaoRepository.findAvaliacaoEntitiesByInscricao_IdInscricao(anyInt())).thenReturn(Optional.empty());
        when(inscricaoService.findDtoByid(anyInt())).thenReturn(InscricaoFactory.getInscricaoDto());
        when(inscricaoService.convertToEntity(any())).thenReturn(InscricaoFactory.getInscricaoEntity());
        when(inscricaoService.converterParaDTO((any()))).thenReturn(InscricaoFactory.getInscricaoDto());
        when(gestorService.findDtoById(anyInt())).thenReturn(GestorFactory.getGestorDto());
        when(gestorService.convertToEntity(any())).thenReturn(GestorFactory.getGestorEntity());
        when(gestorService.convertToDto(any())).thenReturn(GestorFactory.getGestorDto());
        when(avaliacaoRepository.save(any())).thenReturn(AvaliacaoFactory.getAvaliacaoEntityReprovado());

        AvaliacaoDto avaliacaoDtoRetorno = avaliacaoService.create(avaliacaoCreateDto);

        Assert.notNull(avaliacaoDtoRetorno);
        Assertions.assertEquals(avaliacaoDtoRetorno.getAprovado(), TipoMarcacao.F);
        verify(emailService, times(1)).sendEmail(any(), any());

    }

    @Test(expected = RegraDeNegocioException.class)
    public void deveTestarCreateComException() throws RegraDeNegocioException {
        AvaliacaoCreateDto avaliacaoCreateDto = AvaliacaoFactory.getAvaliacaoCreateDto();
        AvaliacaoEntity avaliacaoEntity = AvaliacaoFactory.getAvaliacaoEntityAprovado();

        when(avaliacaoRepository.findAvaliacaoEntitiesByInscricao_IdInscricao(anyInt())).thenReturn(Optional.of(avaliacaoEntity));

        avaliacaoService.create(avaliacaoCreateDto);

        verify(avaliacaoRepository, times(1)).save(any());
    }

    @Test
    public void deveTestarListComSucesso() {
        AvaliacaoEntity avaliacaoEntity = AvaliacaoFactory.getAvaliacaoEntityAprovado();
        when(avaliacaoRepository.findAll()).thenReturn(List.of(avaliacaoEntity));
        avaliacaoService.list();
        verify(avaliacaoRepository, times(1)).findAll();
    }

    @Test
    public void deveTestarUpdateComSucesso() throws RegraDeNegocioException {
        when(avaliacaoRepository.findById(anyInt())).thenReturn(Optional.of(AvaliacaoFactory.getAvaliacaoEntityAprovado()));
        when(avaliacaoRepository.save(any())).thenReturn(AvaliacaoFactory.getAvaliacaoEntityAprovado());
        when(gestorService.convertToDto(any())).thenReturn(GestorFactory.getGestorDto());
        when(inscricaoService.converterParaDTO(any())).thenReturn(InscricaoFactory.getInscricaoDto());
        AvaliacaoDto avaliacaoRetorno = avaliacaoService.update(1, AvaliacaoFactory.getAvaliacaoCreateDto());

        Assert.notNull(avaliacaoRetorno);
    }


    @Test
    public void deveTestarDeleteByIdComSucesso() throws RegraDeNegocioException {

        AvaliacaoEntity avaliacaoEntity = AvaliacaoFactory.getAvaliacaoEntityAprovado();

        when(avaliacaoRepository.findById(anyInt())).thenReturn(Optional.of(avaliacaoEntity));

        avaliacaoService.deleteById(10);
    }


}
