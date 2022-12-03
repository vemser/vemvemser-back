package com.dbc.vemvemser.service;

import com.dbc.vemvemser.dto.*;
import com.dbc.vemvemser.entity.CandidatoEntity;
import com.dbc.vemvemser.entity.FormularioEntity;
import com.dbc.vemvemser.entity.TrilhaEntity;
import com.dbc.vemvemser.enums.TipoMarcacao;
import com.dbc.vemvemser.enums.TipoTurno;
import com.dbc.vemvemser.exception.RegraDeNegocioException;
import com.dbc.vemvemser.repository.FormularioRepository;
import com.dbc.vemvemser.service.FormularioService;
import com.dbc.vemvemser.service.TrilhaService;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import factory.CandidatoFactory;
import factory.FormularioFactory;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.*;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.util.Base64Utils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class FormularioServiceTest {

    @InjectMocks
    private FormularioService formularioService;

    @Mock
    private FormularioRepository formularioRepository;

    @Mock
    private TrilhaService trilhaService;

    @Mock
    private MultipartFile multipartFile;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Before
    public void init() {
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        ReflectionTestUtils.setField(formularioService, "objectMapper", objectMapper);
    }

    @Test
    public void deveTestarCreateFormularioComSucesso() throws RegraDeNegocioException {
        FormularioCreateDto formularioCreateDto = FormularioFactory.getFormularioCreateDto();

        when(formularioRepository.save(any())).thenReturn(FormularioFactory.getFormularioEntity());

        FormularioDto formularioDtoRetorno = formularioService.create(formularioCreateDto);

        assertNotNull(formularioDtoRetorno);
    }

    @Test(expected = RegraDeNegocioException.class)
    public void deveTestarCreateFormularioComException() throws RegraDeNegocioException {

        FormularioCreateDto formularioCreateDto = FormularioFactory.getFormularioCreateDto();
        formularioCreateDto.setMatriculadoBoolean(false);

//        when(formularioRepository.save(any())).thenReturn(formularioCreateDto);

        formularioService.create(formularioCreateDto);

        verify(formularioRepository, times(1)).save(any());
    }

    @Test
    public void deveTestarUpdateComSucesso() throws RegraDeNegocioException {
        // SETUP
        Integer id = 10;
        FormularioCreateDto formularioCreateDto = FormularioFactory.getFormularioCreateDto();

        FormularioEntity formularioEntity = FormularioFactory.getFormularioEntity();
        formularioEntity.setIdFormulario(1);
        when(formularioRepository.findById(anyInt())).thenReturn(Optional.of(formularioEntity));

        FormularioEntity formulario = FormularioFactory.getFormularioEntity();
        when(formularioRepository.save(any())).thenReturn(formulario);

        // Ação (ACT)
        FormularioDto formularioDto = formularioService.update(id, formularioCreateDto);

        // Verificação (ASSERT)
        assertNotNull(formularioDto);
        assertNotEquals("github.com/vemser/vemvemser-back", formularioDto.getGithub());
    }


    @Test
    public void deveTestarUpdateCurriculoComSucesso() throws IOException, RegraDeNegocioException {

        FormularioEntity formularioEntity = FormularioFactory.getFormularioEntity();

        byte[] imagemBytes = new byte[10 * 1024];

        MultipartFile imagem = new MockMultipartFile("curriculo.pdf", "curriculo.pdf", ".pdf", imagemBytes);

        formularioEntity.setCurriculo(imagem.getBytes());

        Optional formulario = Optional.of(formularioEntity);

        when(formularioRepository.findById(anyInt())).thenReturn(formulario);

        formularioService.updateCurriculo(imagem, 1);

        verify(formularioRepository, times(1)).findById(anyInt());
        verify(formularioRepository, times(1)).save(any(FormularioEntity.class));
    }


    @Test(expected = RegraDeNegocioException.class)
    public void deveTestarUpdateCurriculoComRegraNegocioException() throws RegraDeNegocioException, IOException {
        FormularioEntity formularioEntity = FormularioFactory.getFormularioEntity();

        byte[] imagemBytes = new byte[10 * 1024];
        MultipartFile imagem = new MockMultipartFile("curriculo", imagemBytes);
        formularioEntity.setCurriculo(imagem.getBytes());
        Optional formulario = Optional.of(formularioEntity);

        formularioService.updateCurriculo(imagem, 1);

        verify(formularioRepository, times(1)).findById(anyInt());
        verify(formularioRepository, times(1)).save(any(FormularioEntity.class));
    }

    @Test(expected = Exception.class)
    public void deveTestarUpdateCurriculoComRegraNegocioExceptionIoExceptionComSucesso() throws RegraDeNegocioException, IOException {

            FormularioEntity formularioEntity = FormularioFactory.getFormularioEntity();

            MultipartFile curriculoFake = Mockito.mock(MultipartFile.class, Mockito.RETURNS_DEEP_STUBS);
            when(curriculoFake.getOriginalFilename()).thenReturn("seila.pdf");
            when(curriculoFake.getBytes()).thenThrow(new IOException(""));
            when(formularioRepository.findById(anyInt())).thenReturn(Optional.of(FormularioFactory.getFormularioEntity()));

            formularioEntity.setCurriculo(curriculoFake.getBytes());

            formularioService.updateCurriculo(curriculoFake, 1);

            verify(formularioRepository, times(1)).findById(anyInt());
            verify(formularioRepository, times(1)).save(any(FormularioEntity.class));

    }

    @Test
    public void deveTestarFindDtoByIdComSucesso() throws RegraDeNegocioException {
        FormularioEntity formularioEntity = FormularioFactory.getFormularioEntity();

        when(formularioRepository.findById(anyInt())).thenReturn(Optional.of(formularioEntity));

        FormularioDto formularioRetorno = formularioService.findDtoById(1);

        assertEquals(formularioRetorno.getIdFormulario(), formularioEntity.getIdFormulario());
    }

    @Test
    public void deveTestarDeleteByIdComSucesso() throws RegraDeNegocioException {
        FormularioEntity formularioEntity = FormularioFactory.getFormularioEntity();

        when(formularioRepository.findById(anyInt())).thenReturn(Optional.of(formularioEntity));

        formularioService.deleteById(10);

    }

    @Test
    public void deveTestarListarPaginado() throws RegraDeNegocioException {
        Integer pagina = 1;
        Integer tamanho = 5;
        String sort = "idFormulario";
        Integer order = 1;//DESCENDING
        Sort odernacao = Sort.by(sort).descending();
        PageImpl<FormularioEntity> pageImpl = new PageImpl<>(List.of(FormularioFactory.getFormularioEntity()),
                PageRequest.of(pagina, tamanho, odernacao), 0);

        when(formularioRepository.findAll(any(Pageable.class))).thenReturn(pageImpl);

        PageDto<FormularioDto> page = formularioService.listAllPaginado(pagina, tamanho, sort, order);

        assertEquals(page.getTamanho(), tamanho);
    }

    @Test
    public void deveTestarRetornarCurriloCandidatoDecodeComSucesso() throws RegraDeNegocioException {
        Integer idFormulario = 1;

        when(formularioRepository.findById(anyInt())).thenReturn(Optional.of(FormularioFactory.getFormularioEntity()));

        String base64 = formularioService.retornarCurriculoDoCandidatoDecode(idFormulario);

        verify(formularioRepository, times(1)).findById(anyInt());
        assertNotNull(base64);
    }


    @Test
    public void deveTestarConvertToEntityComSucesso() {
        FormularioDto formularioDto = FormularioFactory.getFormularioDto();

        when(trilhaService.convertToEntity(any())).thenReturn(FormularioFactory.getFormularioEntity().getTrilhaEntitySet());

        FormularioEntity formulario = formularioService.convertToEntity(formularioDto);

        assertEquals(formulario.getIdFormulario(), formularioDto.getIdFormulario());

    }


}
