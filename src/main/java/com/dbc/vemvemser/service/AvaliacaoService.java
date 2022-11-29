package com.dbc.vemvemser.service;

import com.dbc.vemvemser.dto.AvaliacaoCreateDto;
import com.dbc.vemvemser.dto.AvaliacaoDto;
import com.dbc.vemvemser.dto.InscricaoDto;
import com.dbc.vemvemser.entity.AvaliacaoEntity;
import com.dbc.vemvemser.entity.InscricaoEntity;
import com.dbc.vemvemser.enums.TipoMarcacao;
import com.dbc.vemvemser.exception.RegraDeNegocioException;
import com.dbc.vemvemser.repository.AvaliacaoRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AvaliacaoService {

    private static final int DESCENDING = 1;
    private final ObjectMapper objectMapper;
    private final AvaliacaoRepository avaliacaoRepository;

    private final InscricaoService inscricaoService;


    public AvaliacaoDto create(AvaliacaoCreateDto avaliacaoCreateDto) throws RegraDeNegocioException {

        AvaliacaoEntity avaliacaoEntity = objectMapper.convertValue(avaliacaoCreateDto, AvaliacaoEntity.class);
        InscricaoEntity inscricaoRetorno = objectMapper.convertValue(inscricaoService.findDtoByid(avaliacaoCreateDto.getIdInscricao()),InscricaoEntity.class);
        avaliacaoEntity.setInscricao(inscricaoRetorno);

        AvaliacaoDto avaliacaoDto = objectMapper.convertValue(avaliacaoRepository.save(avaliacaoEntity), AvaliacaoDto.class);

        return avaliacaoDto;
    }


    public List<AvaliacaoDto> list() {
        return avaliacaoRepository.findAll().stream()
                .map(avaliacaoEntity -> objectMapper.convertValue(avaliacaoEntity, AvaliacaoDto.class))
                .toList();
    }

    public AvaliacaoDto update(Integer idAvaliacao, AvaliacaoCreateDto avaliacaoCreateDto) throws RegraDeNegocioException {
        AvaliacaoEntity avaliacaoEntity = findById(idAvaliacao);

        avaliacaoEntity.setAprovado(convertToEnum(avaliacaoCreateDto.isAprovadoBoolean()));

        AvaliacaoDto avaliacaoRetorno = objectMapper.convertValue(avaliacaoRepository.save(avaliacaoEntity), AvaliacaoDto.class);

        return avaliacaoRetorno;
    }


    public void deleteById(Integer idAvaliacao) throws RegraDeNegocioException {
        findById(idAvaliacao);
        avaliacaoRepository.deleteById(idAvaliacao);
    }

    private AvaliacaoEntity findById(Integer idAvaliacao) throws RegraDeNegocioException {
        Optional avaliacao = avaliacaoRepository.findById(idAvaliacao);
        if (avaliacao == null) {
            throw new RegraDeNegocioException("Avaliação não encontrada!");
        }
        AvaliacaoEntity avaliacaoEntity = objectMapper.convertValue(avaliacao, AvaliacaoEntity.class);

        return avaliacaoEntity;
    }

    private TipoMarcacao convertToEnum(boolean opcao) {
        if (opcao) {
            return TipoMarcacao.T;
        } else {
            return TipoMarcacao.F;
        }
    }

}
