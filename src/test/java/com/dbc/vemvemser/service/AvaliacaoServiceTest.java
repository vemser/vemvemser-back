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
        AvaliacaoCreateDto avaliacaoCreateDto = getAvaliacaoCreateDto();

        CandidatoDto candidatoDto = new CandidatoDto();
        candidatoDto.setNome("Ricardo");
        candidatoDto.setEmail("ricardo@email.com");

        InscricaoDto inscricaoDto = getInscricaoDto();

        CargoDto cargoDto = new CargoDto();
        cargoDto.setIdCargo(1);
        cargoDto.setNome("nome");


        GestorDto gestorDto = new GestorDto();
        gestorDto.setCargoDto(cargoDto);
        gestorDto.setNome("Gestor");
        gestorDto.setEmail("gestor@dbccompany.com.br");
        gestorDto.setIdGestor(1);

        GestorEntity gestorEntity = getGestorEntity();

        InscricaoEntity inscricaoEntity = getInscricaoEntity();

        when(avaliacaoRepository.findAvaliacaoEntitiesByInscricao_IdInscricao(anyInt())).thenReturn(Optional.empty());
        when(inscricaoService.findDtoByid(anyInt())).thenReturn(inscricaoDto);
        when(inscricaoService.convertToEntity(any())).thenReturn(inscricaoEntity);
        when(inscricaoService.converterParaDTO(any())).thenReturn(inscricaoDto);
        when(gestorService.findDtoById(anyInt())).thenReturn(gestorDto);
        when(gestorService.convertToEntity(any())).thenReturn(gestorEntity);
        when(avaliacaoRepository.save(any())).thenReturn(getAvaliacaoEntity());


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
        AvaliacaoEntity avaliacaoEntity = getAvaliacaoEntity();

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

        AvaliacaoEntity avaliacaoEntity= getAvaliacaoEntity();

        when(avaliacaoRepository.findById(anyInt())).thenReturn(Optional.of(avaliacaoEntity));

        avaliacaoService.deleteById(10);
    }

    private AvaliacaoCreateDto getAvaliacaoCreateDto() {
        AvaliacaoCreateDto avaliacaoCreateDto = new AvaliacaoCreateDto();
        avaliacaoCreateDto.setAprovadoBoolean(true);
        avaliacaoCreateDto.setIdInscricao(1);

        return avaliacaoCreateDto;
    }

    private AvaliacaoDto getAvaliacaoDto() {
        InscricaoDto inscricaoDto = getInscricaoDto();

        AvaliacaoDto avaliacaoDto = new AvaliacaoDto();
        avaliacaoDto.setAprovado(TipoMarcacao.T);
        avaliacaoDto.setIdAvaliacao(1);
        avaliacaoDto.setInscricao(inscricaoDto);


        return avaliacaoDto;
    }

    private AvaliacaoEntity getAvaliacaoEntity() {

        CandidatoEntity candidatoEntity = new CandidatoEntity();
        candidatoEntity.setNome("Ricardo");
        candidatoEntity.setEmail("ricardo@email.com");

        InscricaoEntity inscricaoEntity = new InscricaoEntity();
        inscricaoEntity.setIdInscricao(1);
        inscricaoEntity.setDataInscricao(LocalDate.now());
        inscricaoEntity.setAvaliado(TipoMarcacao.T);
        inscricaoEntity.setCandidato(candidatoEntity);

        GestorEntity gestorEntity = new GestorEntity();
        gestorEntity.setNome("gestor");
        gestorEntity.setEmail("gestpor@dbccompany.com.br");
        gestorEntity.setSenha("1234");
        gestorEntity.setIdGestor(1);
        gestorEntity.setAtivo(TipoMarcacao.T);

        AvaliacaoEntity avaliacaoEntity = new AvaliacaoEntity();
        avaliacaoEntity.setInscricao(inscricaoEntity);
        avaliacaoEntity.setAprovado(TipoMarcacao.T);
        avaliacaoEntity.setIdAvaliacao(1);
        avaliacaoEntity.setAvaliador(gestorEntity);

        return avaliacaoEntity;
    }

    private InscricaoDto getInscricaoDto() {

        CandidatoDto candidatoDto = new CandidatoDto();
        candidatoDto.setNome("Ricardo");
        candidatoDto.setEmail("ricardo@email.com");
        candidatoDto.setIdCandidato(1);

        InscricaoDto inscricaoDto = new InscricaoDto();
        inscricaoDto.setIdInscricao(1);
        inscricaoDto.setDataInscricao(LocalDate.now());
        inscricaoDto.setAvaliado(TipoMarcacao.T);
        inscricaoDto.setCandidato(candidatoDto);

        return inscricaoDto;
    }

    private InscricaoEntity getInscricaoEntity() {

        CandidatoEntity candidatoEntity = new CandidatoEntity();
        candidatoEntity.setNome("Ricardo");
        candidatoEntity.setEmail("ricardo@email.com");

        InscricaoEntity inscricaoEntity = new InscricaoEntity();
        inscricaoEntity.setIdInscricao(1);
        inscricaoEntity.setDataInscricao(LocalDate.now());
        inscricaoEntity.setAvaliado(TipoMarcacao.T);
        inscricaoEntity.setCandidato(candidatoEntity);

        return inscricaoEntity;
    }

    private GestorEntity getGestorEntity() {

        CargoEntity cargoEntity = new CargoEntity();
        cargoEntity.setIdCargo(1);
        cargoEntity.setNome("nome");

        GestorEntity gestorEntity = new GestorEntity();
        gestorEntity.setCargoEntity(cargoEntity);
        gestorEntity.setNome("Gestor");
        gestorEntity.setEmail("gestor@dbccompany.com.br");
        gestorEntity.setIdGestor(1);

        return gestorEntity;
    }

    private GestorDto getGestorDto() {

        CargoDto cargoDto = new CargoDto();
        cargoDto.setIdCargo(1);
        cargoDto.setNome("nome");

        GestorDto gestorDto = new GestorDto();
        gestorDto.setIdGestor(1);
        gestorDto.setAtivo(TipoMarcacao.T);
        gestorDto.setNome("Gestor");
        gestorDto.setEmail("gestor@dbccompany.com.br");
        gestorDto.setIdGestor(1);

        return gestorDto;
    }

    private CargoDto getCargoDto(){
        CargoDto cargoDto = new CargoDto();
        cargoDto.setIdCargo(1);
        cargoDto.setNome("nome");

        return cargoDto;

    }

    private CandidatoDto getCandidatoDto(){

        CandidatoDto candidatoDto = new CandidatoDto();
        candidatoDto.setNome("Ricardo");
        candidatoDto.setEmail("ricardo@email.com");
        candidatoDto.setIdCandidato(1);

        return candidatoDto;
    }

    private FormularioDto getFormularioDto() {
        FormularioDto formularioDto = new FormularioDto();

        TrilhaDto trilhaDto = new TrilhaDto();
        trilhaDto.setIdTrilha(2);
        trilhaDto.setNome("BACKEND");

        Set<TrilhaDto> trilhas = new HashSet<>();
        trilhas.add(trilhaDto);

        formularioDto.setMatriculado(TipoMarcacao.T);
        formularioDto.setCurso("TECNICO T.I");
        formularioDto.setTurno(TipoTurno.NOITE);
        formularioDto.setInstituicao("PUC");
        formularioDto.setGithub("github.com");
        formularioDto.setLinkedin("linkedin.com");
        formularioDto.setDesafios(TipoMarcacao.T);
        formularioDto.setProblema(TipoMarcacao.T);
        formularioDto.setReconhecimento(TipoMarcacao.T);
        formularioDto.setAltruismo(TipoMarcacao.T);
        formularioDto.setResposta("outro");
        formularioDto.setDisponibilidade(TipoMarcacao.T);
        formularioDto.setEfetivacao(TipoMarcacao.T);
        formularioDto.setProva(TipoMarcacao.T);
        formularioDto.setIngles("Basico");
        formularioDto.setEspanhol("Não possuo");
        formularioDto.setNeurodiversidade("Não possuo");
        formularioDto.setConfiguracoes("16gb RAM");
        formularioDto.setEfetivacao(TipoMarcacao.T);
        formularioDto.setDisponibilidade(TipoMarcacao.T);
        formularioDto.setGenero("MASCULINO");
        formularioDto.setTrilhas(trilhas);
        formularioDto.setOrientacao("Heterossexual");
        formularioDto.setLgpd(TipoMarcacao.T);

        return formularioDto;
    }

}
