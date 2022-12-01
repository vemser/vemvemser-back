package com.dbc.vemvemser;

import com.dbc.vemvemser.dto.CandidatoCreateDto;
import com.dbc.vemvemser.dto.CandidatoDto;
import com.dbc.vemvemser.dto.PageDto;
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
        CandidatoCreateDto candidatoCreateDto = getCandidatoCreateDto();

        CandidatoEntity candidatoEntity = getCandidatoEntity();

        candidatoEntity.setIdCandidato(1);
        when(candidatoRepository.save(any())).thenReturn(candidatoEntity);

        // Ação (ACT)
        CandidatoDto candidatoDto = candidatoService.cadastro(candidatoCreateDto);

        // Verificação (ASSERT)
        assertNotNull(candidatoDto);
        assertNotNull(candidatoDto.getIdCandidato());
    }


    @Test
    public void deveTestarFindByIdComSucesso() throws RegraDeNegocioException {
        // Criar variaveis (SETUP)
        Integer busca = 10;

        CandidatoEntity candidatoEntity = getCandidatoEntity();
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

        CandidatoEntity candidatoEntity = getCandidatoEntity();
        candidatoEntity.setIdCandidato(1);
        when(candidatoRepository.findById(anyInt())).thenReturn(Optional.of(candidatoEntity));

        // Ação (ACT)
        candidatoService.deleteById(busca);

    }

    @Test
    public void deveTestarUpdateComSucesso() throws RegraDeNegocioException {
        // SETUP
        Integer id= 10;
        CandidatoCreateDto candidatoCreateDto = getCandidatoCreateDto();

        CandidatoEntity candidatoEntity = getCandidatoEntity();
        candidatoEntity.setIdCandidato(1);
        candidatoEntity.setNome("Ricardo de Lucas");
        when(candidatoRepository.findById(anyInt())).thenReturn(Optional.of(candidatoEntity));

        CandidatoEntity candidato = getCandidatoEntity();
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

        CandidatoEntity candidatoEntity = getCandidatoEntity();
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
        CandidatoDto candidatoDto = getCandidatoDto();

        CandidatoEntity candidatoEntity = getCandidatoEntity();

        when(candidatoRepository.findById(anyInt())).thenReturn(Optional.of(candidatoEntity));
        when(formularioService.convertToDto(any())).thenReturn(candidatoEntity);
        when(candidatoService.findDtoById(anyInt())).thenReturn(candidatoDto);

        // Ação (ACT)
        CandidatoDto candidatoRecuperado = candidatoService.findCandidatoDtoByEmail(email);
        // Verificação (ASSERT)
        assertNotNull(candidatoRecuperado);
        assertEquals("eduardosedrez@gmail.com", candidatoRecuperado.getEmail());
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



    private static CandidatoEntity getCandidatoEntity() {
        CandidatoEntity candidatoEntity = new CandidatoEntity();
        candidatoEntity.setNome("Eduardo Sedrez Rodrigues");
        candidatoEntity.setDataNascimento(LocalDate.of(2013,10,13));
        candidatoEntity.setEmail("eduardosedrez@gmail.com");
        candidatoEntity.setTelefone("53981258964");
        candidatoEntity.setRg("77.777.777-7");
        candidatoEntity.setCpf("049.239.620-54");
        candidatoEntity.setEstado("Rio Grande do Sul");
        candidatoEntity.setCidade("Pelotas");
        candidatoEntity.setPcd(TipoMarcacao.F);
        candidatoEntity.setInscricao(new InscricaoEntity());
        candidatoEntity.setFormulario(new FormularioEntity());
        return candidatoEntity;
    }

    private static CandidatoCreateDto getCandidatoCreateDto() {
        CandidatoCreateDto candidatoCreateDto = new CandidatoCreateDto();
        candidatoCreateDto.setNome("Eduardo Sedrez Rodrigues");
        candidatoCreateDto.setDataNascimento(LocalDate.of(2013,10,13));
        candidatoCreateDto.setEmail("eduardosedrez@gmail.com");
        candidatoCreateDto.setTelefone("53981258964");
        candidatoCreateDto.setRg("77.777.777-7");
        candidatoCreateDto.setCpf("049.239.620-54");
        candidatoCreateDto.setEstado("Rio Grande do Sul");
        candidatoCreateDto.setCidade("Pelotas");
        candidatoCreateDto.setPcdboolean(false);
        return candidatoCreateDto;
    }

    private static CandidatoDto getCandidatoDto() {
        CandidatoDto candidatoDto = new CandidatoDto();
        candidatoDto.setNome("Eduardo Sedrez Rodrigues");
        candidatoDto.setDataNascimento(LocalDate.of(2013,10,13));
        candidatoDto.setEmail("eduardosedrez@gmail.com");
        candidatoDto.setTelefone("53981258964");
        candidatoDto.setRg("77.777.777-7");
        candidatoDto.setCpf("049.239.620-54");
        candidatoDto.setEstado("Rio Grande do Sul");
        candidatoDto.setCidade("Pelotas");
        candidatoDto.setPcd(TipoMarcacao.F);
        return candidatoDto;
    }
}
