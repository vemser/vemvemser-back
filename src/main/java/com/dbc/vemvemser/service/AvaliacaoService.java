package com.dbc.vemvemser.service;

import com.dbc.vemvemser.dto.*;
import com.dbc.vemvemser.entity.AvaliacaoEntity;
import com.dbc.vemvemser.entity.InscricaoEntity;
import com.dbc.vemvemser.enums.TipoEmail;
import com.dbc.vemvemser.enums.TipoMarcacao;
import com.dbc.vemvemser.exception.RegraDeNegocioException;
import com.dbc.vemvemser.repository.AvaliacaoRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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
    private final EmailService emailService;


    public AvaliacaoDto create(AvaliacaoCreateDto avaliacaoCreateDto) throws RegraDeNegocioException {
        if (!avaliacaoRepository.findAvaliacaoEntitiesByInscricao_IdInscricao(avaliacaoCreateDto.getIdInscricao()).isEmpty()) {
            throw new RegraDeNegocioException("Formulario cadastrado para outro candidato");
        }
        AvaliacaoEntity avaliacaoEntity = convertToEntity(avaliacaoCreateDto);
        AvaliacaoDto avaliacaoDto = convertToDto(avaliacaoRepository.save(avaliacaoEntity));
        avaliacaoDto.setAvaliador(gestorService.convertToDto(avaliacaoEntity.getAvaliador()));
        SendEmailDto sendEmailDto = new SendEmailDto();
        sendEmailDto.setNome(avaliacaoDto.getInscricao().getCandidato().getNome());
        sendEmailDto.setEmail(avaliacaoDto.getInscricao().getCandidato().getEmail());
        if (avaliacaoDto.getAprovado() == TipoMarcacao.T) {
            emailService.sendEmail(sendEmailDto, TipoEmail.APROVADO);
        } else {
            emailService.sendEmail(sendEmailDto, TipoEmail.REPROVADO);
        }
        inscricaoService.setAvaliado(avaliacaoCreateDto.getIdInscricao());
        return avaliacaoDto;
    }


    public PageDto<AvaliacaoDto> list(Integer pagina, Integer tamanho, String sort, int order) {
        Sort ordenacao = Sort.by(sort).ascending();
        if (order == DESCENDING) {
            ordenacao = Sort.by(sort).descending();
        }
        PageRequest pageRequest = PageRequest.of(pagina, tamanho, ordenacao);
        Page<AvaliacaoEntity> paginaAvaliacaoEntities = avaliacaoRepository.findAll(pageRequest);

        List<AvaliacaoDto> avaliacaoDtos = paginaAvaliacaoEntities.getContent().stream()
                .map(avaliacaoEntity -> convertToDto(avaliacaoEntity)).toList();

        return new PageDto<>(paginaAvaliacaoEntities.getTotalElements(),
                paginaAvaliacaoEntities.getTotalPages(),
                pagina,
                tamanho,
                avaliacaoDtos);
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

    public List<AvaliacaoDto> findAvaliacaoByCanditadoEmail(String email) {
        List<AvaliacaoEntity> lista = avaliacaoRepository.findAvaliacaoEntitiesByInscricao_Candidato_Email(email);
        return lista.stream().map(avaliacaoEntity -> convertToDto(avaliacaoEntity))
                .toList();
    }


    public AvaliacaoDto convertToDto(AvaliacaoEntity avaliacaoEntity) {
        AvaliacaoDto avaliacaoDto = new AvaliacaoDto();
        avaliacaoDto.setIdAvaliacao(avaliacaoEntity.getIdAvaliacao());
        avaliacaoDto.setAprovado(avaliacaoEntity.getAprovado());
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
