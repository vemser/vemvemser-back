package com.dbc.vemvemser.service;

import com.dbc.vemvemser.dto.CandidatoDto;
import com.dbc.vemvemser.dto.FormularioDto;
import com.dbc.vemvemser.dto.InscricaoCreateDto;
import com.dbc.vemvemser.dto.InscricaoDto;
import com.dbc.vemvemser.entity.CandidatoEntity;
import com.dbc.vemvemser.entity.FormularioEntity;
import com.dbc.vemvemser.entity.InscricaoEntity;
import com.dbc.vemvemser.entity.TrilhaEntity;
import com.dbc.vemvemser.exception.RegraDeNegocioException;
import com.dbc.vemvemser.repository.InscricaoRepository;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import factory.CandidatoFactory;
import factory.FormularioFactory;
import factory.InscricaoFactory;
import factory.TrilhaFactory;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class InscricaoServiceTest {

    @InjectMocks
    private InscricaoService inscricaoService;

    @Mock
    private InscricaoRepository inscricaoRepository;

    @Mock
    private CandidatoService candidatoService;

    @Mock
    private FormularioService formularioService;

    @Mock
    private TrilhaService trilhaService;

    @Mock
    private EmailService emailService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Before
    public void init() {
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        ReflectionTestUtils.setField(inscricaoService, "objectMapper", objectMapper);
    }

    @Test
    public void deveTestarCreateComSucesso() throws RegraDeNegocioException {
        CandidatoEntity candidatoEntity = CandidatoFactory.getCandidatoEntity();

        CandidatoDto candidatoDto = CandidatoFactory.getCandidatoDto();

        FormularioEntity formularioEntity = FormularioFactory.getFormularioEntity();

        InscricaoCreateDto inscricaoCreateDto = InscricaoFactory.getInscricaoCreateDto();
        when(inscricaoRepository.findInscricaoEntitiesByCandidato_IdCandidato(anyInt()))
                .thenReturn(Optional.empty());

        when(candidatoService.findDtoById(any()))
                .thenReturn(candidatoDto);

        when(candidatoService.convertToEntity(any()))
                .thenReturn(candidatoEntity);

        inscricaoService.create(inscricaoCreateDto);

        verify(inscricaoRepository, times(1)).save(any());
    }

    @Test(expected = RegraDeNegocioException.class)
    public void deveTestarCreateComException() throws RegraDeNegocioException {

        InscricaoEntity inscricaoEntity = new InscricaoEntity();
        inscricaoEntity.setIdInscricao(1);


        InscricaoCreateDto inscricaoCreateDto = InscricaoFactory.getInscricaoCreateDto();

        when(inscricaoRepository.findInscricaoEntitiesByCandidato_IdCandidato(anyInt()))
                .thenReturn(Optional.of(inscricaoEntity));

        inscricaoService.create(inscricaoCreateDto);


        verify(inscricaoRepository, times(1)).save(any());
    }

    @Test
    public void deveTestarSetAvaliadoComSucesso() throws RegraDeNegocioException {

        CandidatoDto candidatoDto = CandidatoFactory.getCandidatoDto();

        FormularioDto formularioDto = FormularioFactory.getFormularioDto();

        InscricaoEntity inscricaoEntity = InscricaoFactory.getInscricaoEntity();

        when(inscricaoRepository.save(any()))
                .thenReturn(inscricaoEntity);
        when(inscricaoRepository.findById(anyInt()))
                .thenReturn(Optional.of(inscricaoEntity));

        InscricaoEntity inscricao = inscricaoService.setAvaliado(1);

        Assert.assertNotNull(inscricao);
    }

    @Test
    public void deveTestarFindInscricaoPorEmail() throws RegraDeNegocioException {
        // Criar variaveis (SETUP)
        String email = "eduardosedrez@gmail.com";
        CandidatoDto candidatoDto = CandidatoFactory.getCandidatoDto();

        InscricaoEntity inscricaoEntity = InscricaoFactory.getInscricaoEntity();

        inscricaoRepository.findInscricaoEntitiesByCandidato_Email(email);

        // Ação (ACT)
        List<InscricaoDto> inscricaoRecuperada = inscricaoService.findInscricaoPorEmail(email);
        // Verificação (ASSERT)
        assertNotNull(inscricaoRecuperada);
        assertEquals(email, inscricaoEntity.getCandidato().getEmail());
    }

    @Test
    public void deveTestarFindDtoByIdComSucesso() throws RegraDeNegocioException {

        InscricaoEntity inscricaoEntity = InscricaoFactory.getInscricaoEntity();

        CandidatoDto candidatoDto = CandidatoFactory.getCandidatoDto();
        FormularioDto formularioDto = FormularioFactory.getFormularioDto();

        when(inscricaoRepository.findById(anyInt()))
                .thenReturn(Optional.of(inscricaoEntity));
        when(candidatoService.convertToDto(any()))
                .thenReturn(candidatoDto);

        InscricaoDto inscricaoDtoRetorno = inscricaoService.findDtoByid(1);

        Assert.assertNotNull(inscricaoDtoRetorno);
    }

    @Test
    public void deveTestarDeleteComSucesso() throws RegraDeNegocioException {

        InscricaoEntity inscricaoEntity = InscricaoFactory.getInscricaoEntity();

        when(inscricaoRepository.findById(anyInt())).thenReturn(Optional.of(inscricaoEntity));

        inscricaoService.delete(1);

        verify(inscricaoRepository, times(1)).deleteById(anyInt());
    }

    @Test
    public void deveTestarConvertToEntity() {

        InscricaoDto inscricaoDto = InscricaoFactory.getInscricaoDto();
        CandidatoEntity candidatoEntity = CandidatoFactory.getCandidatoEntity();
        FormularioEntity formularioEntity = FormularioFactory.getFormularioEntity();
        TrilhaEntity trilhaEntity = TrilhaFactory.getTrilhaEntity();
        Set<TrilhaEntity> trilhas = new HashSet<>();
        trilhas.add(trilhaEntity);

        when(candidatoService.convertToEntity(any()))
                .thenReturn(candidatoEntity);

        InscricaoEntity inscricaoEntityRetorno = inscricaoService.convertToEntity(inscricaoDto);

        Assert.assertNotNull(inscricaoEntityRetorno);
    }


}
