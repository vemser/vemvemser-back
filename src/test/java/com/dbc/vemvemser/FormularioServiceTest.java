package com.dbc.vemvemser;

import com.dbc.vemvemser.dto.FormularioCreateDto;
import com.dbc.vemvemser.dto.FormularioDto;
import com.dbc.vemvemser.dto.PageDto;
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
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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

        FormularioCreateDto formularioCreateDto = getFormularioCreateDto();

        when(formularioRepository.save(any())).thenReturn(getFormularioEntity());


        FormularioDto formularioDtoRetorno = formularioService.create(formularioCreateDto);

        assertNotNull(formularioDtoRetorno);
    }

    @Test(expected = RegraDeNegocioException.class)
    public void deveTestarCreateFormularioComException() throws RegraDeNegocioException {

        FormularioCreateDto formularioCreateDto = getFormularioCreateDto();
        formularioCreateDto.setMatriculadoBoolean(false);

        when(formularioRepository.save(any())).thenReturn(formularioCreateDto);

        formularioService.create(formularioCreateDto);

        verify(formularioRepository, times(1)).save(any());
    }

//    @Test
//    public void deveTestarRetornarCurriculoDoCandidatoDecodeComSucesso() throws RegraDeNegocioException, IOException {
//
//        FormularioEntity formularioEntity = getFormularioEntity();
//
////        byte[] imagemBytes = HexFormat.of().parseHex("e04fd020ea3a6910a2d808002b30309d");
//        byte[] imagemBytes = new byte[10*1024];
//
//        MultipartFile imagem = new MockMultipartFile("imagem", imagemBytes);
//
//        formularioEntity.setCurriculo(imagem.getBytes());
//
//        Optional formulario = Optional.of(formularioEntity);
//
//        when(formularioRepository.findById(anyInt())).thenReturn(formulario);
//
//        String base64=formularioService.retornarCurriculoDoCandidatoDecode(1);
//
//        assertEquals(base64,"curriculo");
//    }

    @Test(expected = RegraDeNegocioException.class)
    public void deveTestarRetornarCurriculoDoCandidatoDecodeComException() throws RegraDeNegocioException {

        Optional formulario = Optional.empty();

        when(formularioRepository.findById(anyInt())).thenReturn(formulario);

        String base64 = formularioService.retornarCurriculoDoCandidatoDecode(1);

        verify(formularioRepository, times(1)).findById(anyInt());

    }



    @Test
    public void deveTestarUpdateFormularioComSucesso() throws RegraDeNegocioException {
        FormularioCreateDto formularioCreateDto = getFormularioCreateDto();

        FormularioEntity formularioEntity = getFormularioEntity();
        Optional formulario = Optional.of(formularioEntity);

        FormularioEntity formularioEntityUpdate = getFormularioEntity();
        formularioEntityUpdate.setIngles("Não possuo");
        formularioEntityUpdate.setConfiguracoes("8gb ram");

        when(formularioRepository.findById(anyInt())).thenReturn(formulario);
        when(formularioRepository.save(any(FormularioEntity.class))).thenReturn(formularioEntityUpdate);

       FormularioDto formularioDtoRetorno = formularioService.update(1,formularioCreateDto);

       assertNotNull(formularioDtoRetorno);
       assertEquals(formularioDtoRetorno.getIdFormulario(),formularioEntityUpdate.getIdFormulario());
       assertNotEquals(formularioEntity.getIngles(),formularioDtoRetorno.getIngles());

    }
    @Test
    public void deveTestarUpdateCurriculoComSucesso() throws IOException, RegraDeNegocioException {

        FormularioEntity formularioEntity = getFormularioEntity();

        byte[] imagemBytes = new byte[10 * 1024];

        MultipartFile imagem = new MockMultipartFile("curriculo.pdf","curriculo.pdf",".pdf", imagemBytes);

        formularioEntity.setCurriculo(imagem.getBytes());

        Optional formulario = Optional.of(formularioEntity);

        when(formularioRepository.findById(anyInt())).thenReturn(formulario);

        formularioService.updateCurriculo(imagem, 1);

        verify(formularioRepository, times(1)).findById(anyInt());
        verify(formularioRepository, times(1)).save(any(FormularioEntity.class));
    }


    @Test(expected = RegraDeNegocioException.class)
    public void deveTestarUpdateCurriculoComRegraNegocioException() throws RegraDeNegocioException, IOException {
        FormularioEntity formularioEntity = getFormularioEntity();

        byte[] imagemBytes = new byte[10 * 1024];

        MultipartFile imagem = new MockMultipartFile("curriculo", imagemBytes);

        formularioEntity.setCurriculo(imagem.getBytes());

        Optional formulario = Optional.of(formularioEntity);

        when(formularioRepository.findById(anyInt())).thenReturn(formulario);

        formularioService.updateCurriculo(imagem, 1);

        verify(formularioRepository, times(1)).findById(anyInt());
        verify(formularioRepository, times(1)).save(any(FormularioEntity.class));

    }

    private FormularioEntity getFormularioEntity() {
        FormularioEntity formularioEntity = new FormularioEntity();

        Set<TrilhaEntity> trilhas = new HashSet<>();
        TrilhaEntity trilhaEntity = new TrilhaEntity();
        trilhaEntity.setIdTrilha(1);
        trilhaEntity.setNome("FRONTEND");
        trilhas.add(trilhaEntity);


        formularioEntity.setIdFormulario(1);
        formularioEntity.setMatriculado(TipoMarcacao.T);
        formularioEntity.setCurso("TECNICO T.I");
        formularioEntity.setTurno(TipoTurno.NOITE);
        formularioEntity.setInstituicao("PUC");
        formularioEntity.setGithub("github.com");
        formularioEntity.setLinkedin("linkedin.com");
        formularioEntity.setDesafios(TipoMarcacao.T);
        formularioEntity.setProblema(TipoMarcacao.T);
        formularioEntity.setReconhecimento(TipoMarcacao.T);
        formularioEntity.setAltruismo(TipoMarcacao.T);
        formularioEntity.setResposta("outro");
        formularioEntity.setDisponibilidade(TipoMarcacao.T);
        formularioEntity.setEfetivacao(TipoMarcacao.T);
        formularioEntity.setProva(TipoMarcacao.T);
        formularioEntity.setIngles("Basico");
        formularioEntity.setEspanhol("Não possuo");
        formularioEntity.setNeurodiversidade("Não possuo");
        formularioEntity.setConfiguracoes("16gb RAM");
        formularioEntity.setEfetivacao(TipoMarcacao.T);
        formularioEntity.setDisponibilidade(TipoMarcacao.T);
        formularioEntity.setGenero("MASCULINO");
        formularioEntity.setTrilhaEntitySet(trilhas);
        formularioEntity.setOrientacao("Heterossexual");
        formularioEntity.setLgpd(TipoMarcacao.T);


        return formularioEntity;
    }

    private FormularioCreateDto getFormularioCreateDto() {
        FormularioCreateDto formularioCreateDto = new FormularioCreateDto();

        List<Integer> trilhas = new ArrayList<>();
        trilhas.add(1);

        formularioCreateDto.setMatriculadoBoolean(true);
        formularioCreateDto.setCurso("TECNICO T.I");
        formularioCreateDto.setTurno(TipoTurno.NOITE);
        formularioCreateDto.setInstituicao("PUC");
        formularioCreateDto.setGithub("github.com");
        formularioCreateDto.setLinkedin("linkedin.com");
        formularioCreateDto.setDesafiosBoolean(true);
        formularioCreateDto.setProblemaBoolean(true);
        formularioCreateDto.setReconhecimentoBoolean(true);
        formularioCreateDto.setAltruismoBoolean(true);
        formularioCreateDto.setResposta("outro");
        formularioCreateDto.setDisponibilidadeBoolean(true);
        formularioCreateDto.setEfetivacaoBoolean(true);
        formularioCreateDto.setProvaBoolean(true);
        formularioCreateDto.setIngles("Basico");
        formularioCreateDto.setEspanhol("Não possuo");
        formularioCreateDto.setNeurodiversidade("Não possuo");
        formularioCreateDto.setConfiguracoes("16gb RAM");
        formularioCreateDto.setEfetivacaoBoolean(true);
        formularioCreateDto.setDisponibilidadeBoolean(true);
        formularioCreateDto.setGenero("MASCULINO");
        formularioCreateDto.setTrilhas(trilhas);
        formularioCreateDto.setOrientacao("Heterossexual");
        formularioCreateDto.setLgpdBoolean(true);

        return formularioCreateDto;
    }

}
