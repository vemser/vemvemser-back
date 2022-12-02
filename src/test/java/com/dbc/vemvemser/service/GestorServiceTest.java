package com.dbc.vemvemser.service;

import com.dbc.vemvemser.dto.*;
import com.dbc.vemvemser.entity.CargoEntity;
import com.dbc.vemvemser.entity.GestorEntity;
import com.dbc.vemvemser.enums.TipoMarcacao;
import com.dbc.vemvemser.exception.RegraDeNegocioException;
import com.dbc.vemvemser.repository.CargoRepository;
import com.dbc.vemvemser.repository.GestorRepository;
import com.dbc.vemvemser.security.TokenService;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import factory.CargoFactory;
import factory.GestorFactory;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
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

    @Mock
    private TokenService tokenService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Before
    public void init() {
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        ReflectionTestUtils.setField(gestorService, "objectMapper", objectMapper);
    }

    @Test
    public void deveTestarCadastrarComSucesso() throws RegraDeNegocioException {
        GestorCreateDto gestorCreateDto = GestorFactory.getGestorCreateDto();
        GestorEntity gestorEntity = GestorFactory.getGestorEntity();

        CargoEntity cargoEntity = CargoFactory.getCargoEntity();
        CargoDto cargoDto = CargoFactory.getCargoDto();

        when(cargoService.findById(anyInt()))
                .thenReturn(cargoEntity);

        when(cargoService.convertToDto(any()))
                .thenReturn(cargoDto);

        when(gestorRepository.save(any()))
                .thenReturn(gestorEntity);

        GestorDto gestorDtoRetorno = gestorService.cadastrar(gestorCreateDto);

        Assert.assertNotNull(gestorDtoRetorno);

    }

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

        GestorDto gestorDtoRetorno = gestorService.findDtoById(1);

        Assert.assertNotNull(gestorDtoRetorno);
    }

    @Test
    public void deveTestarEditarComSucesso() throws RegraDeNegocioException {

        GestorEntity gestorEntity = GestorFactory.getGestorEntity();

        UsernamePasswordAuthenticationToken user
                = new UsernamePasswordAuthenticationToken(1, gestorEntity.getEmail(), Collections.emptyList());

        SecurityContextHolder.getContext().setAuthentication(user);
        GestorDto gestorDto = objectMapper.convertValue(user, GestorDto.class);
        GestorCreateDto gestorCreateDto = GestorFactory.getGestorCreateDto();
        gestorEntity.setIdGestor(gestorDto.getIdGestor());

        when(gestorRepository.findById(anyInt())).thenReturn(Optional.of(gestorEntity));

        GestorDto gestorDtoRetorno = gestorService.editar(1, gestorCreateDto);

        Assert.assertNotNull(gestorDtoRetorno);
    }

    @Test(expected = RegraDeNegocioException.class)
    public void deveTestarEditarComException() throws RegraDeNegocioException {

        GestorEntity gestorEntity = GestorFactory.getGestorEntity();

        UsernamePasswordAuthenticationToken user
                = new UsernamePasswordAuthenticationToken(1, gestorEntity.getEmail(), Collections.emptyList());
        SecurityContextHolder.getContext().setAuthentication(user);

        GestorDto gestorDto = objectMapper.convertValue(user, GestorDto.class);

        GestorCreateDto gestorCreateDto = GestorFactory.getGestorCreateDto();
        gestorEntity.setIdGestor(gestorDto.getIdGestor());

        GestorDto gestorDtoRetorno = gestorService.editar(4, gestorCreateDto);


    }

    @Test
    public void deveTestarGestorByNomeOrEmailComListaVaziaComSucesso() throws RegraDeNegocioException {

        GestorEmailNomeCargoDto gestorEmailNomeCargoDto = new GestorEmailNomeCargoDto();
        gestorEmailNomeCargoDto.setNome("");
        gestorEmailNomeCargoDto.setEmail("");

        List<GestorDto> listaRetorno = gestorService.findGestorbyNomeOrEmail(gestorEmailNomeCargoDto);

        Assert.assertNotNull(listaRetorno);
    }

    @Test
    public void deveTestarGestorByNomeOrEmailComSucesso() throws RegraDeNegocioException {

        GestorEmailNomeCargoDto gestorEmailNomeCargoDto = GestorFactory.getGestorEmailNomeCargoDto();
        GestorEntity gestorEntity = GestorFactory.getGestorEntity();
        GestorDto gestorDto = GestorFactory.getGestorDto();
        CargoEntity cargoEntity = CargoFactory.getCargoEntity();
        CargoDto cargoDto = CargoFactory.getCargoDto();
        gestorEntity.setCargoEntity(cargoEntity);

        List<GestorEntity> lista = new ArrayList<>();
        lista.add(gestorEntity);

        when(cargoService.findById(anyInt())).thenReturn(cargoEntity);
        when(gestorRepository.findGestorEntitiesByNomeEqualsIgnoreCaseOrEmailEqualsIgnoreCaseAndCargoEntity(any(), any(), any()))
                .thenReturn(lista);
        List<GestorDto> listaRetorno = gestorService.findGestorbyNomeOrEmail(gestorEmailNomeCargoDto);

        Assert.assertNotNull(listaRetorno);
    }

    @Test
    public void deveTestarRemoverComSucesso() throws RegraDeNegocioException {

        GestorEntity gestorEntity = GestorFactory.getGestorEntity();

        when(gestorRepository.findById(anyInt())).thenReturn(Optional.of(gestorEntity));

        gestorService.remover(1);

    }

    @Test
    public void deveTestarFindByEmailComSucesso() {

        GestorEntity gestorEntity = GestorFactory.getGestorEntity();

        when(gestorRepository.findByEmail(anyString())).thenReturn(Optional.of(gestorEntity));

        Optional<GestorEntity> gestorRetorno = gestorService.findByEmail("email@email.com");

        Assert.assertNotNull(gestorRetorno);
    }

    @Test
    public void deveTestarGetLoggedUser() throws RegraDeNegocioException {

        GestorEntity gestorEntity = GestorFactory.getGestorEntity();

        UsernamePasswordAuthenticationToken user
                = new UsernamePasswordAuthenticationToken(1, gestorEntity.getEmail(), Collections.emptyList());
        SecurityContextHolder.getContext().setAuthentication(user);

        GestorEntity gestorEntityUser = objectMapper.convertValue(user, GestorEntity.class);

        when(gestorRepository.findById(anyInt())).thenReturn(Optional.of(gestorEntityUser));

        GestorDto gestorDtoRetorno = gestorService.getLoggedUser();


        Assert.assertNotNull(gestorDtoRetorno);
    }

    @Test
    public void deveTestarForgotPasswordComSucesso() throws RegraDeNegocioException {
        GestorEntity gestorEntity = GestorFactory.getGestorEntity();
        TokenDto tokenDto = new TokenDto();
        tokenDto.setCargoDto(CargoFactory.getCargoDto());
        tokenDto.setIdGestor(gestorEntity.getIdGestor());
        tokenDto.setToken("abcdefgh");

        when(gestorRepository.findByEmail(anyString())).thenReturn(Optional.of(gestorEntity));
        when(tokenService.getToken(any(), any())).thenReturn(tokenDto);

        TokenDto tokenDtoRetorno = gestorService.forgotPassword("email@email.com.br");

        Assert.assertNotNull(tokenDtoRetorno);
        Assert.assertEquals(tokenDtoRetorno.getToken(), tokenDto.getToken());
    }

    @Test(expected = RegraDeNegocioException.class)
    public void deveTestarForgotPasswordComException() throws RegraDeNegocioException {

        when(gestorRepository.findByEmail(anyString())).thenReturn(Optional.empty());

        gestorService.forgotPassword("email@email.com");

        verify(gestorRepository, times(1)).findByEmail(anyString());
    }

    @Test
    public void deveTestarDesativarContaComSucesso() throws RegraDeNegocioException {
        GestorEntity gestorEntity = GestorFactory.getGestorEntity();
        CargoEntity cargoEntity = CargoFactory.getCargoEntity();

        when(gestorRepository.findById(anyInt())).thenReturn(Optional.of(gestorEntity));

        GestorDto gestorDtoRetorno = gestorService.desativarConta(1);

        Assert.assertNotNull(gestorDtoRetorno);
    }


    @Test
    public void deveTestarContasInativasComSucesso() {

        GestorEntity gestorEntity = GestorFactory.getGestorEntity();
        gestorEntity.setAtivo(TipoMarcacao.F);

        CargoDto cargoDto = CargoFactory.getCargoDto();

        List<GestorEntity> lista = new ArrayList<>();
        lista.add(gestorEntity);

        List<GestorDto> listaRetorno = gestorService.contasInativas();

        Assert.assertNotNull(listaRetorno);
    }

    @Test
    public void deveTestarConvertToEntityComSucesso() {
        GestorEntity gestorEntity = GestorFactory.getGestorEntity();
        GestorDto gestorDto = GestorFactory.getGestorDto();
        CargoEntity cargoEntity = CargoFactory.getCargoEntity();

        when(cargoService.convertToEntity(any())).thenReturn(cargoEntity);

        GestorEntity gestorRetorno = gestorService.convertToEntity(gestorDto);

        Assert.assertNotNull(gestorRetorno);
        Assert.assertEquals(gestorRetorno.getNome(), gestorEntity.getNome());

    }

}
