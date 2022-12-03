package com.dbc.vemvemser.service;


import com.dbc.vemvemser.dto.*;
import com.dbc.vemvemser.entity.CandidatoEntity;
import com.dbc.vemvemser.entity.FormularioEntity;
import com.dbc.vemvemser.entity.TrilhaEntity;
import com.dbc.vemvemser.enums.TipoMarcacao;
import com.dbc.vemvemser.exception.RegraDeNegocioException;
import com.dbc.vemvemser.repository.FormularioRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.Base64Utils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FormularioService {

    private static final int DESCENDING = 1;
    private final FormularioRepository formularioRepository;
    private final TrilhaService trilhaService;
    private final ObjectMapper objectMapper;


    public FormularioDto create(FormularioCreateDto formularioCreateDto) throws RegraDeNegocioException {
        if (!formularioCreateDto.isMatriculadoBoolean()) {
            throw new RegraDeNegocioException("Precisa estar matriculado!");
        }
        FormularioEntity formulario = convertToEntity(formularioCreateDto);
        FormularioEntity formularioRetornoBanco = formularioRepository.save(formulario);
        FormularioDto formularioDto = convertToDto(formularioRetornoBanco);
        return formularioDto;
    }


    public String retornarCurriculoDoCandidatoDecode(Integer idFormulario) throws RegraDeNegocioException {
        FormularioEntity formularioRetorno = findById(idFormulario);

        if (formularioRetorno.getCurriculo() == null) {
            formularioRetorno.setCurriculo("".getBytes());
        }
        return Base64Utils.encodeToString(formularioRetorno.getCurriculo());
    }

    public PageDto<FormularioDto> listAllPaginado(Integer pagina, Integer tamanho, String sort, int order) {
        Sort ordenacao = Sort.by(sort).ascending();
        if (order == DESCENDING) {
            ordenacao = Sort.by(sort).descending();
        }
        PageRequest pageRequest = PageRequest.of(pagina, tamanho, ordenacao);
        Page<FormularioEntity> paginaFormularioEntity = formularioRepository.findAll(pageRequest);
        List<FormularioDto> formularioDtos = paginaFormularioEntity.getContent().stream()
                .map(formularioEntity -> {
                    FormularioDto formulario = convertToDto(formularioEntity);
                    return formulario;
                }).toList();
        return new PageDto<>(paginaFormularioEntity.getTotalElements(),
                paginaFormularioEntity.getTotalPages(),
                pagina,
                tamanho,
                formularioDtos);
    }

    public FormularioEntity findById(Integer idFormulario) throws RegraDeNegocioException {
        return formularioRepository.findById(idFormulario)
                .orElseThrow(() -> new RegraDeNegocioException("Erro ao buscar Formulario"));
    }

    public FormularioDto findDtoById(Integer idFormulario) throws RegraDeNegocioException {
        FormularioEntity formulario = findById(idFormulario);
        FormularioDto formularioDto = convertToDto(formulario);
        return formularioDto;
    }

    public void deleteById(Integer idFormulario) throws RegraDeNegocioException {
        findById(idFormulario);
        formularioRepository.deleteById(idFormulario);
    }

    public FormularioDto update(Integer idFormulario, FormularioCreateDto formularioCreateDto) throws RegraDeNegocioException {
        findById(idFormulario);
        FormularioEntity formulario1 = convertToEntity(formularioCreateDto);
        formulario1.setIdFormulario(idFormulario);
        FormularioEntity formularioEntity = formularioRepository.save(formulario1);
        return convertToDto(formularioEntity);
    }

    public void updateCurriculo(MultipartFile curriculo, Integer idFormulario) throws RegraDeNegocioException {

        try {
            String arquivo = curriculo.getOriginalFilename();
            FormularioEntity formulario = findById(idFormulario);
            formulario.setCurriculo(curriculo.getBytes());
            if (!arquivo.endsWith(".pdf")) {
                throw new RegraDeNegocioException("Formato de arquivo invalido!");
            }
            formularioRepository.save(formulario);
        } catch (IOException e) {
            throw new RegraDeNegocioException("Arquivo invalido");
        }
    }


    public FormularioDto convertToDto(FormularioEntity formulario) {
        FormularioDto formularioDto = objectMapper.convertValue(formulario, FormularioDto.class);
        formularioDto.setTrilhas(formulario.getTrilhaEntitySet().stream()
                .map(trilhaEntity -> objectMapper.convertValue(trilhaEntity, TrilhaDto.class))
                .collect(Collectors.toSet()));
        return formularioDto;
    }

    private FormularioEntity convertToEntity(FormularioCreateDto formularioCreateDto) throws RegraDeNegocioException {
        FormularioEntity formularioEntity = objectMapper.convertValue(formularioCreateDto, FormularioEntity.class);
        formularioEntity.setMatriculado(formularioCreateDto.isMatriculadoBoolean() ? TipoMarcacao.T : TipoMarcacao.F);
        formularioEntity.setDesafios(formularioCreateDto.isDesafiosBoolean() ? TipoMarcacao.T : TipoMarcacao.F);
        formularioEntity.setProblema(formularioCreateDto.isProblemaBoolean() ? TipoMarcacao.T : TipoMarcacao.F);
        formularioEntity.setReconhecimento(formularioCreateDto.isReconhecimentoBoolean() ? TipoMarcacao.T : TipoMarcacao.F);
        formularioEntity.setAltruismo(formularioCreateDto.isAltruismoBoolean() ? TipoMarcacao.T : TipoMarcacao.F);
        formularioEntity.setLgpd(formularioCreateDto.isLgpdBoolean() ? TipoMarcacao.T : TipoMarcacao.F);
        formularioEntity.setProva(formularioCreateDto.isProvaBoolean() ? TipoMarcacao.T : TipoMarcacao.F);
        formularioEntity.setEfetivacao(formularioCreateDto.isEfetivacaoBoolean() ? TipoMarcacao.T : TipoMarcacao.F);
        formularioEntity.setDisponibilidade(formularioCreateDto.isDisponibilidadeBoolean() ? TipoMarcacao.T : TipoMarcacao.F);
        Set<TrilhaEntity> trilhas = trilhaService.findListaTrilhas(formularioCreateDto.getTrilhas());
        formularioEntity.setTrilhaEntitySet(trilhas);
        return formularioEntity;
    }

    public FormularioEntity convertToEntity(FormularioDto formularioDto) {
        FormularioEntity formulario = objectMapper.convertValue(formularioDto, FormularioEntity.class);
        formulario.setTrilhaEntitySet(trilhaService.convertToEntity(formularioDto.getTrilhas()));
        return formulario;
    }
}