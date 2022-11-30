package com.dbc.vemvemser.service;

import com.dbc.vemvemser.dto.AvaliacaoCreateDto;
import com.dbc.vemvemser.dto.AvaliacaoDto;
import com.dbc.vemvemser.entity.AvaliacaoEntity;
import com.dbc.vemvemser.entity.InscricaoEntity;
import com.dbc.vemvemser.enums.TipoMarcacao;
import com.dbc.vemvemser.exception.RegraDeNegocioException;
import com.dbc.vemvemser.repository.AvaliacaoRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AvaliacaoService {

    private static final int DESCENDING = 1;
    private final ObjectMapper objectMapper;
    private final AvaliacaoRepository avaliacaoRepository;
    private final InscricaoService inscricaoService;
    private final GestorService gestorService;


    public AvaliacaoDto create(AvaliacaoCreateDto avaliacaoCreateDto) throws RegraDeNegocioException {
        if (!avaliacaoRepository.findAvaliacaoEntitiesByInscricao_IdInscricao(avaliacaoCreateDto.getIdInscricao()).isEmpty()) {
            throw new RegraDeNegocioException("Formulario cadastrado para outro candidato");
        }
        AvaliacaoEntity avaliacaoEntity = convertToEntity(avaliacaoCreateDto);
        AvaliacaoDto avaliacaoDto = convertToDto(avaliacaoRepository.save(avaliacaoEntity));
        avaliacaoDto.setAvaliador(gestorService.convertToDto(avaliacaoEntity.getAvaliador()));
        return avaliacaoDto;
    }


    public List<AvaliacaoDto> list() {
        return avaliacaoRepository.findAll().stream()
                .map(avaliacaoEntity -> convertToDto(avaliacaoEntity))
                .toList();
    }

    public AvaliacaoDto update(Integer idAvaliacao, AvaliacaoCreateDto avaliacaoCreateDto) throws RegraDeNegocioException {
        AvaliacaoEntity avaliacaoEntity = findById(idAvaliacao);
        avaliacaoEntity.setAprovado(avaliacaoCreateDto.isAprovadoBoolean() ? TipoMarcacao.T : TipoMarcacao.F);
        AvaliacaoDto avaliacaoRetorno = convertToDto(avaliacaoRepository.save(avaliacaoEntity));
        return avaliacaoRetorno;
    }


    public void deleteById(Integer idAvaliacao) throws RegraDeNegocioException {
        findById(idAvaliacao);
        avaliacaoRepository.deleteById(idAvaliacao);
    }

    private AvaliacaoEntity findById(Integer idAvaliacao) throws RegraDeNegocioException {
        return avaliacaoRepository.findById(idAvaliacao)
                .orElseThrow(() -> new RegraDeNegocioException("Avaliação não encontrada!"));
    }


    public AvaliacaoDto convertToDto(AvaliacaoEntity avaliacaoEntity) {
        AvaliacaoDto avaliacaoDto = objectMapper.convertValue(avaliacaoEntity, AvaliacaoDto.class);
        avaliacaoDto.setAvaliador(gestorService.convertToDto(avaliacaoEntity.getAvaliador()));
        avaliacaoDto.setInscricao(inscricaoService.converterParaDTO(avaliacaoEntity.getInscricao()));
        return avaliacaoDto;
    }

    private AvaliacaoEntity convertToEntity(AvaliacaoCreateDto avaliacaoCreateDto) throws RegraDeNegocioException {
        AvaliacaoEntity avaliacaoEntity = objectMapper.convertValue(avaliacaoCreateDto, AvaliacaoEntity.class);
        InscricaoEntity inscricaoEntity = inscricaoService.convertToEntity(inscricaoService.findDtoByid(avaliacaoCreateDto.getIdInscricao()));
        avaliacaoEntity.setInscricao(inscricaoEntity);
        avaliacaoEntity.setAprovado(avaliacaoCreateDto.isAprovadoBoolean() ? TipoMarcacao.T : TipoMarcacao.F);
        avaliacaoEntity.setAvaliador(gestorService.convertToEntity(gestorService.findDtoById(1)));
        return avaliacaoEntity;
    }

}
