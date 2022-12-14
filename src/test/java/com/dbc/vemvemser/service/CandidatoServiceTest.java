package com.dbc.vemvemser.service;

import com.dbc.vemvemser.dto.*;
import com.dbc.vemvemser.entity.*;
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
import org.springframework.data.domain.*;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDate;
import java.util.*;

import static factory.TrilhaFactory.getTrilhaDto;
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
    private TrilhaService trilhaService;
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

        // A????o (ACT)
        CandidatoDto candidatoDto = candidatoService.cadastro(candidatoCreateDto);

        // Verifica????o (ASSERT)
        assertNotNull(candidatoDto);
        assertNotNull(candidatoDto.getIdCandidato());
    }

    @Test(expected = RegraDeNegocioException.class)
    public void deveTestarCadastrarComErroNoEmail() throws RegraDeNegocioException {
        // Criar variaveis (SETUP)
        CandidatoCreateDto candidatoCreateDto = new CandidatoCreateDto();
        candidatoCreateDto.setEmail("eduardosedrez@gmail.com");

        CandidatoEntity candidatoEntity = new CandidatoEntity();
        List<CandidatoEntity> candidato = new ArrayList<>();
        candidato.add(CandidatoFactory.getCandidatoEntity());

//        !candidatoRepository.findCandidatoEntitiesByEmail(candidatoCreateDto.getEmail()).isEmpty()
        when(candidatoRepository.findCandidatoEntitiesByEmail(any())).thenReturn(candidato);

        // A????o (ACT)
        candidatoService.cadastro(candidatoCreateDto);

        verify(candidatoRepository, times(1)).save(any());
    }

    @Test(expected = RegraDeNegocioException.class)
    public void deveTestarCadastrarComErroDeFormularioRepetido() throws RegraDeNegocioException {
        // Criar variaveis (SETUP)
        CandidatoCreateDto candidatoCreateDto = new CandidatoCreateDto();
        candidatoCreateDto.setIdFormulario(1);


        CandidatoEntity candidato = new CandidatoEntity();
        FormularioEntity formulario = new FormularioEntity();
        candidato.setFormulario(formulario);
//        !candidatoRepository.findCandidatoEntitiesByFormulario_IdFormulario(candidatoCreateDto.getIdFormulario()).isEmpty()
        when(candidatoRepository.findCandidatoEntitiesByFormulario_IdFormulario(any())).thenReturn(Optional.of(candidato));

        // A????o (ACT)
        candidatoService.cadastro(candidatoCreateDto);

        verify(candidatoRepository, times(1)).save(any());
    }


    @Test
    public void deveTestarFindByIdComSucesso() throws RegraDeNegocioException {
        // Criar variaveis (SETUP)
        Integer busca = 10;

        CandidatoEntity candidatoEntity = CandidatoFactory.getCandidatoEntity();
        candidatoEntity.setIdCandidato(1);
        when(candidatoRepository.findById(anyInt())).thenReturn(Optional.of(candidatoEntity));

        // A????o (ACT)
        CandidatoEntity candidatoRecuperado = candidatoService.findById(busca);

        // Verifica????o (ASSERT)
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

        // A????o (ACT)
        candidatoService.deleteById(busca);

    }

    @Test
    public void deveTestarUpdateComSucesso() throws RegraDeNegocioException {
        // SETUP
        Integer id = 10;
        CandidatoCreateDto candidatoCreateDto = CandidatoFactory.getCandidatoCreateDto();

        CandidatoEntity candidatoEntity = CandidatoFactory.getCandidatoEntity();
        candidatoEntity.setIdCandidato(1);
        candidatoEntity.setNome("Ricardo de Lucas");
        when(candidatoRepository.findById(anyInt())).thenReturn(Optional.of(candidatoEntity));

        CandidatoEntity candidato = CandidatoFactory.getCandidatoEntity();
        when(candidatoRepository.save(any())).thenReturn(candidato);

        // A????o (ACT)
        CandidatoDto candidatoDto = candidatoService.update(id, candidatoCreateDto);

        // Verifica????o (ASSERT)
        assertNotNull(candidatoDto);
        assertNotEquals("Eduardo Sedrez", candidatoDto.getNome());
    }

    @Test(expected = RegraDeNegocioException.class)
    public void deveTestarFindByIdComErro() throws RegraDeNegocioException {
        // Criar variaveis (SETUP)
        Integer busca = 10;
        when(candidatoRepository.findById(anyInt())).thenReturn(Optional.empty());

        // A????o (ACT)
        candidatoService.findById(busca);
    }

    @Test
    public void deveTestarFindDtoByIdComSucesso() throws RegraDeNegocioException {
        // Criar variaveis (SETUP)
        Integer busca = 10;

        CandidatoEntity candidatoEntity = CandidatoFactory.getCandidatoEntity();
        candidatoEntity.setIdCandidato(1);
        when(candidatoRepository.findById(anyInt())).thenReturn(Optional.of(candidatoEntity));

        // A????o (ACT)
        CandidatoDto candidatoRecuperado = candidatoService.findDtoById(busca);

        // Verifica????o (ASSERT)
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

        // A????o (ACT)
        List<CandidatoDto> candidatoRecuperado = candidatoService.findCandidatoDtoByEmail(email);
        // Verifica????o (ASSERT)
        assertNotNull(candidatoRecuperado);
        assertEquals(email, candidatoEntity.getEmail());
    }

    @Test
    public void deveTestarConvertToEntity() {
        // Criar variaveis (SETUP)

        FormularioDto form = new FormularioDto();
        TrilhaDto trilhaDto = getTrilhaDto();
        Set<TrilhaEntity> trilhaEntity = trilhaService.convertToEntity(Set.of(trilhaDto));
        //candidatoEntity.setFormulario(formularioService.convertToEntity(candidatoDto.getFormulario()));

        // A????o (ACT)
        Set<TrilhaEntity> trilha = trilhaService.convertToEntity(form.getTrilhas());
        FormularioEntity formularioEntity = formularioService.convertToEntity(form);
        CandidatoEntity candidatoEntity = candidatoService.convertToEntity(CandidatoFactory.getCandidatoDto());

        // Verifica????o (ASSERT)
        assertNotNull(trilha);

    }

    @Test
    public void deveTestarListarPaginado() throws RegraDeNegocioException {
        Integer pagina = 1;
        Integer tamanho = 5;
        String sort = "idCandidato";
        Integer order = 1;//DESCENDING
        Sort odernacao = Sort.by(sort).descending();
        PageImpl<CandidatoEntity> candidatoEntities = new PageImpl<>(List.of(CandidatoFactory.getCandidatoEntity()),
                PageRequest.of(pagina, tamanho,odernacao), 0);

        when(candidatoRepository.findAll(any(Pageable.class))).thenReturn(candidatoEntities);
        when(formularioService.convertToDto(any())).thenReturn(FormularioFactory.getFormularioDto());

        PageDto<CandidatoDto> page = candidatoService.listaAllPaginado(pagina,tamanho,sort,order);

        assertEquals(page.getTamanho(),tamanho);

    }

}
