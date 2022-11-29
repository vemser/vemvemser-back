package com.dbc.vemvemser.service;


import com.dbc.vemvemser.dto.CandidatoDto;
import com.dbc.vemvemser.dto.FormularioCreateDto;
import com.dbc.vemvemser.dto.FormularioDto;
import com.dbc.vemvemser.dto.PageDto;
import com.dbc.vemvemser.entity.CandidatoEntity;
import com.dbc.vemvemser.entity.FormularioEntity;
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
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FormularioService {


    private static final int ASCENDING = 0;
    private static final int DESCENDING = 1;
    private final FormularioRepository formularioRepository;
    private final ObjectMapper objectMapper;


    public FormularioDto create(FormularioCreateDto formularioCreateDto) {
        FormularioEntity formulario = objectMapper.convertValue(formularioCreateDto, FormularioEntity.class);
        formulario.setMatriculado(convertToEnum(formularioCreateDto.isMatriculadoBoolean()));
        formulario.setDesafios(convertToEnum(formularioCreateDto.isDesafiosBoolean()));
        formulario.setProblema(convertToEnum(formularioCreateDto.isProblemaBoolean()));
        formulario.setReconhecimento(convertToEnum(formularioCreateDto.isReconhecimentoBoolean()));
        formulario.setAltruismo(convertToEnum(formularioCreateDto.isAltruismoBoolean()));
        formulario.setLgpd(convertToEnum((formularioCreateDto.isLgpdBoolean())));
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

    public PageDto<FormularioDto> listAllPaginado(Integer pagina, Integer tamanho, String sort, int order) {
        Sort ordenacao = Sort.by(sort).ascending();
        if (order == DESCENDING) {
            ordenacao = Sort.by(sort).descending();
        }
        PageRequest pageRequest = PageRequest.of(pagina, tamanho, ordenacao);
        Page<FormularioEntity> paginaFormularioEntity = formularioRepository.findAll(pageRequest);

        List<FormularioDto> formularioDtos = paginaFormularioEntity.getContent().stream()
                .map(candidatoEntity -> objectMapper.convertValue(candidatoEntity, FormularioDto.class)).toList();

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

    private TipoMarcacao convertToEnum(boolean opcao){
        if(opcao){
            return TipoMarcacao.T;
        }else{
            return TipoMarcacao.F;
        }
    }

}