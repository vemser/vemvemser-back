package com.dbc.vemvemser.service;

import com.dbc.vemvemser.dto.AvaliacaoCreateDto;
import com.dbc.vemvemser.dto.CandidatoCreateDto;
import com.dbc.vemvemser.dto.CandidatoDto;
import com.dbc.vemvemser.dto.PageDto;
import com.dbc.vemvemser.entity.AvaliacaoEntity;
import com.dbc.vemvemser.entity.CandidatoEntity;
import com.dbc.vemvemser.entity.FormularioEntity;
import com.dbc.vemvemser.entity.InscricaoEntity;
import com.dbc.vemvemser.enums.TipoMarcacao;
import com.dbc.vemvemser.exception.RegraDeNegocioException;
import com.dbc.vemvemser.repository.CandidatoRepository;
import com.dbc.vemvemser.service.CandidatoService;
import com.dbc.vemvemser.service.FormularioService;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import factory.CandidatoFactory;
import factory.FormularioFactory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class CandidatoServiceTest {

    @InjectMocks
    private CandidatoService candidatoService;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Mock
    private CandidatoRepository candidatoRepository;

    @Mock
    private FormularioService formularioService;

    @Before
    public void init() {
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        ReflectionTestUtils.setField(candidatoService, "objectMapper", objectMapper);
    }


    @Test
    public void deveTestarCadastrarComSucesso() throws RegraDeNegocioException {
        // Criar variaveis (SETUP)
        CandidatoCreateDto candidatoCreateDto = CandidatoFactory.getCandidatoCreateDto();

        CandidatoEntity candidatoEntity = CandidatoFactory.getCandidatoEntity();

        candidatoEntity.setIdCandidato(1);
        when(candidatoRepository.save(any())).thenReturn(candidatoEntity);

        // Ação (ACT)
        CandidatoDto candidatoDto = candidatoService.cadastro(candidatoCreateDto);

        // Verificação (ASSERT)
        assertNotNull(candidatoDto);
        assertNotNull(candidatoDto.getIdCandidato());
    }

//    @Test(expected = RegraDeNegocioException.class)
//    public void deveTestarCadastrarComErroNoEmail() throws RegraDeNegocioException {
//        // Criar variaveis (SETUP)
//        CandidatoCreateDto candidatoCreateDto = new CandidatoCreateDto();
//        candidatoCreateDto.setEmail("eduardosedrez@gmail.com");
//
//        CandidatoEntity candidatoEntity = new CandidatoEntity();
//       List<CandidatoEntity> candidato = new ArrayList<>();
//       candidato.add(CandidatoFactory.getCandidatoEntity());
//
////        !candidatoRepository.findCandidatoEntitiesByEmail(candidatoCreateDto.getEmail()).isEmpty()
//        when(candidatoRepository.findCandidatoEntitiesByEmail(any())).thenReturn(candidato);
//
//        // Ação (ACT)
//        candidatoService.cadastro(candidatoCreateDto);
//
//        verify(candidatoRepository, times(1)).save(any());
//    }

//    @Test(expected = RegraDeNegocioException.class)
//    public void deveTestarCadastrarComErroDeFormularioRepetido() throws RegraDeNegocioException {
//        // Criar variaveis (SETUP)
//        CandidatoCreateDto candidatoCreateDto = new CandidatoCreateDto();
//        candidatoCreateDto.setIdFormulario(1);
//
//        CandidatoEntity candidatoEntity = new CandidatoEntity();
//        List<CandidatoCreateDto> candidato = new ArrayList<>();
//        candidato.add(CandidatoFactory.getCandidatoCreateDto());
////        !candidatoRepository.findCandidatoEntitiesByFormulario_IdFormulario(candidatoCreateDto.getIdFormulario()).isEmpty()
//        when(candidatoRepository.findCandidatoEntitiesByFormulario_IdFormulario(any())).thenReturn(candidato);
//
//        // Ação (ACT)
//        candidatoService.cadastro(candidatoCreateDto);
//
//        verify(candidatoRepository, times(1)).save(any());
//    }


    @Test
    public void deveTestarFindByIdComSucesso() throws RegraDeNegocioException {
        // Criar variaveis (SETUP)
        Integer busca = 10;

        CandidatoEntity candidatoEntity = CandidatoFactory.getCandidatoEntity();
        candidatoEntity.setIdCandidato(1);
        when(candidatoRepository.findById(anyInt())).thenReturn(Optional.of(candidatoEntity));

        // Ação (ACT)
        CandidatoEntity candidatoRecuperado = candidatoService.findById(busca);

        // Verificação (ASSERT)
        assertNotNull(candidatoRecuperado);
        assertEquals(1, candidatoRecuperado.getIdCandidato());
    }

    @Test
    public void deveTestarDeleteById() throws RegraDeNegocioException {
        // Criar variaveis (SETUP)
        Integer busca = 10;

        CandidatoEntity candidatoEntity = CandidatoFactory.getCandidatoEntity();
        candidatoEntity.setIdCandidato(1);
        when(candidatoRepository.findById(anyInt())).thenReturn(Optional.of(candidatoEntity));

        // Ação (ACT)
        candidatoService.deleteById(busca);

    }

    @Test
    public void deveTestarUpdateComSucesso() throws RegraDeNegocioException {
        // SETUP
        Integer id= 10;
        CandidatoCreateDto candidatoCreateDto = CandidatoFactory.getCandidatoCreateDto();

        CandidatoEntity candidatoEntity = CandidatoFactory.getCandidatoEntity();
        candidatoEntity.setIdCandidato(1);
        candidatoEntity.setNome("Ricardo de Lucas");
        when(candidatoRepository.findById(anyInt())).thenReturn(Optional.of(candidatoEntity));

        CandidatoEntity candidato = CandidatoFactory.getCandidatoEntity();
        when(candidatoRepository.save(any())).thenReturn(candidato);

        // Ação (ACT)
        CandidatoDto candidatoDto = candidatoService.update(id, candidatoCreateDto);

        // Verificação (ASSERT)
        assertNotNull(candidatoDto);
        assertNotEquals("Eduardo Sedrez", candidatoDto.getNome());
    }
    @Test(expected = RegraDeNegocioException.class)
    public void deveTestarFindByIdComErro() throws RegraDeNegocioException {
        // Criar variaveis (SETUP)
        Integer busca = 10;
        when(candidatoRepository.findById(anyInt())).thenReturn(Optional.empty());

        // Ação (ACT)
        candidatoService.findById(busca);
    }

    @Test
    public void deveTestarFindDtoByIdComSucesso() throws RegraDeNegocioException {
        // Criar variaveis (SETUP)
        Integer busca = 10;

        CandidatoEntity candidatoEntity = CandidatoFactory.getCandidatoEntity();
        candidatoEntity.setIdCandidato(1);
        when(candidatoRepository.findById(anyInt())).thenReturn(Optional.of(candidatoEntity));

        // Ação (ACT)
        CandidatoDto candidatoRecuperado = candidatoService.findDtoById(busca);

        // Verificação (ASSERT)
        assertNotNull(candidatoRecuperado);
        assertEquals(1, candidatoRecuperado.getIdCandidato());
    }

    @Test
    public void deveTestarFindCandidatoDtoByEmail() throws RegraDeNegocioException {
        // Criar variaveis (SETUP)
        String email = "eduardosedrez@gmail.com";
        CandidatoDto candidatoDto = CandidatoFactory.getCandidatoDto();

        CandidatoEntity candidatoEntity = CandidatoFactory.getCandidatoEntity();

        candidatoRepository.findCandidatoEntitiesByEmail(email);

        // Ação (ACT)
        List<CandidatoDto> candidatoRecuperado = candidatoService.findCandidatoDtoByEmail(email);
        // Verificação (ASSERT)
        assertNotNull(candidatoRecuperado);
        assertEquals(email, candidatoEntity.getEmail());
    }



//    @Test
//    public void deveTestarListarComSucesso() {
//        // Criar variaveis (SETUP)
//        List<CandidatoEntity> lista = new ArrayList<>();
//        lista.add(getCandidatoEntity());
//        when(candidatoRepository.findAll()).thenReturn(lista);
//
//        // Ação (ACT)
//        List<PageDto> candidatos = candidatoService.listaAllPaginado(1,10, "" ,1);
//
//        // Verificação (ASSERT)
//        assertNotNull(candidatos);
//        assertTrue(candidatos.size() > 0);
//        assertEquals(1, lista.size());
//    }




}
