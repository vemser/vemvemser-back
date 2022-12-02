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
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.util.ReflectionTestUtils;

import javax.validation.constraints.AssertTrue;
import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
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
    public void deveTestarListarPaginado() throws RegraDeNegocioException {
        Integer pagina = 1;
        Integer tamanho = 5;
        String sort = "idAvaliacao";
        Integer order = 1;//DESCENDING
        Sort odernacao = Sort.by(sort).descending();
        PageImpl<AvaliacaoEntity> pageImpl = new PageImpl<>(List.of(AvaliacaoFactory.getAvaliacaoEntityAprovado()),
                PageRequest.of(pagina, tamanho, odernacao), 0);

        when(avaliacaoRepository.findAll(any(Pageable.class))).thenReturn(pageImpl);

        PageDto<AvaliacaoDto> page = avaliacaoService.list(pagina, tamanho, sort, order);

        assertEquals(page.getTamanho(), tamanho);

    }

    @Test
    public void deveTestarFindAvaliacaoPorEmail() throws RegraDeNegocioException {
        // Criar variaveis (SETUP)
        String email = "eduardosedrez@gmail.com";
        List<AvaliacaoDto> avaliacaoDtos = List.of(AvaliacaoFactory.getAvaliacaoDto());
        List<AvaliacaoEntity> avaliacaoEntities = List.of(AvaliacaoFactory.getAvaliacaoEntityReprovado());

        when(avaliacaoRepository.findAvaliacaoEntitiesByInscricao_Candidato_Email(any())).thenReturn(avaliacaoEntities);
        when(gestorService.convertToDto(any())).thenReturn(GestorFactory.getGestorDto());
        when(inscricaoService.converterParaDTO(any())).thenReturn(InscricaoFactory.getInscricaoDto());
        // Ação (ACT)
        List<AvaliacaoDto> avaliacaoDtos1 = avaliacaoService.findAvaliacaoByCanditadoEmail(email);
        // Verificação (ASSERT)
        assertNotNull(avaliacaoDtos1);
        assertEquals(email, avaliacaoDtos1.get(0).getInscricao().getCandidato().getEmail());
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
