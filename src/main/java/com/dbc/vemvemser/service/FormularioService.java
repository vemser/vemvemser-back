package com.dbc.vemvemser.service;


import com.dbc.vemvemser.dto.FormularioCreateDto;
import com.dbc.vemvemser.dto.FormularioDto;
import com.dbc.vemvemser.entity.FormularioEntity;
import com.dbc.vemvemser.enums.TipoMarcacao;
import com.dbc.vemvemser.enums.TipoTurno;
import com.dbc.vemvemser.exception.RegraDeNegocioException;
import com.dbc.vemvemser.repository.FormularioRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.Base64Utils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FormularioService {

    private final FormularioRepository formularioRepository;
    private final ObjectMapper objectMapper;


    public FormularioDto create(TipoMarcacao matriculado,
                                String curso,
                                TipoTurno turno,
                                String instituicao,
                                String github,
                                String linkedin,
                                TipoMarcacao desafios,
                                TipoMarcacao problemas,
                                TipoMarcacao reconhecimento,
                                TipoMarcacao altruismo,
                                String resposta,
                                MultipartFile curriculo,
                                TipoMarcacao lgpd) throws IOException {

        FormularioCreateDto formularioCreateDto = new FormularioCreateDto();
        formularioCreateDto.setMatriculado(matriculado);
        formularioCreateDto.setCurso(curso);
        formularioCreateDto.setTurno(turno);
        formularioCreateDto.setInstituicao(instituicao);
        formularioCreateDto.setGithub(github);
        formularioCreateDto.setLinkedin(linkedin);
        formularioCreateDto.setDesafios(desafios);
        formularioCreateDto.setProblema(problemas);
        formularioCreateDto.setReconhecimento(reconhecimento);
        formularioCreateDto.setAltruismo(altruismo);
        formularioCreateDto.setResposta(resposta);
        formularioCreateDto.setCurriculo(curriculo.getBytes());
        formularioCreateDto.setLgpd(lgpd);

        FormularioEntity formulario = objectMapper.convertValue(formularioCreateDto, FormularioEntity.class);

        FormularioEntity formularioRetornoBanco = formularioRepository.save(formulario);

        FormularioDto formularioDto = objectMapper.convertValue(formularioRetornoBanco, FormularioDto.class);

        return formularioDto;
    }


    public String retornarCurriculoDoCandidatoDecode(Integer idFormulario) throws RegraDeNegocioException {
        Optional formularioRetorno = formularioRepository.findById(idFormulario);

        if (formularioRetorno.isEmpty()) {
            throw new RegraDeNegocioException("Curriculo não encontrado");
        }
        FormularioEntity formularioComCurriculo = objectMapper.convertValue(formularioRetorno, FormularioEntity.class);

        return Base64Utils.encodeToString(formularioComCurriculo.getCurriculo());
    }

    public List<FormularioDto> list() {
        return formularioRepository.findAll().stream()
                .map(formularioEntity -> objectMapper.convertValue(formularioEntity, FormularioDto.class)).toList();
    }

    public FormularioEntity findById(Integer idFormulario) throws RegraDeNegocioException {
        return formularioRepository.findById(idFormulario)
                .orElseThrow(() -> new RegraDeNegocioException("Erro ao buscar Formulario"));
    }

    public void deleteById(Integer idFormulario) throws RegraDeNegocioException {
        findById(idFormulario);
        formularioRepository.deleteById(idFormulario);
    }

    public FormularioDto update(Integer idFormulario, FormularioCreateDto formularioCreateDto) throws RegraDeNegocioException {
        FormularioEntity formulario = findById(idFormulario);
        FormularioEntity formulario1 = objectMapper.convertValue(formularioCreateDto, FormularioEntity.class);
        formulario1.setIdFormulario(idFormulario);
        FormularioEntity formularioEntity = formularioRepository.save(formulario1);
        return objectMapper.convertValue(formularioEntity, FormularioDto.class);
    }

    public String updateCurriculo(MultipartFile curriculo, Integer idFormulario) throws RegraDeNegocioException, IOException {
        FormularioEntity formulario = findById(idFormulario);

        formulario.setCurriculo(curriculo.getBytes());

        FormularioEntity formularioRetorno = formularioRepository.save(formulario);

        FormularioDto formularioDto = objectMapper.convertValue(formularioRetorno, FormularioDto.class);

        return Base64Utils.encodeToString(formulario.getCurriculo());
    }

}