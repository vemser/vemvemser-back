package com.dbc.vemvemser.service;

import com.dbc.vemvemser.dto.GestorDto;
import com.dbc.vemvemser.dto.InscricaoCreateDto;
import com.dbc.vemvemser.dto.InscricaoDto;
import com.dbc.vemvemser.dto.PageDto;
import com.dbc.vemvemser.entity.GestorEntity;
import com.dbc.vemvemser.entity.InscricaoEntity;
import com.dbc.vemvemser.enums.TipoEmail;
import com.dbc.vemvemser.enums.TipoMarcacao;
import com.dbc.vemvemser.exception.RegraDeNegocioException;
import com.dbc.vemvemser.repository.InscricaoRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class InscricaoService {

    private static final int DESCENDING = 1;
    private final InscricaoRepository inscricaoRepository;
    private final CandidatoService candidatoService;

    private final EmailService emailService;
    private final ObjectMapper objectMapper;

    public InscricaoDto create(InscricaoCreateDto inscricaoCreateDto) throws RegraDeNegocioException {
        if(!inscricaoRepository.findInscricaoEntitiesByCandidato_IdCandidato(inscricaoCreateDto.getIdCandidato()).isEmpty()){
            throw new RegraDeNegocioException("Formulario cadastrado para outro candidato");
        }
        InscricaoEntity inscricaoEntity = convertToEntity(inscricaoCreateDto);
        inscricaoEntity.setCandidato(candidatoService.convertToEntity(candidatoService.findDtoById(inscricaoCreateDto.getIdCandidato())));
        inscricaoEntity.setDataInscricao(LocalDate.now());
        inscricaoEntity.setAvaliado(TipoMarcacao.F);
        inscricaoRepository.save(inscricaoEntity);
        InscricaoDto inscricaoDto = converterParaDTO(inscricaoEntity);

        emailService.sendEmail(inscricaoDto.getCandidato(), TipoEmail.INSCRICAO);
        return inscricaoDto;
    }

    public InscricaoDto setAvaliado(Integer idInscricao) throws RegraDeNegocioException {
        InscricaoEntity inscricaoEntity = findById(idInscricao);
        inscricaoEntity.setAvaliado(TipoMarcacao.T);
        InscricaoDto inscricaoDto = converterParaDTO(inscricaoRepository.save(inscricaoEntity));
        return inscricaoDto;
    }

    public PageDto<InscricaoDto> listar(Integer pagina, Integer tamanho, String sort, int order) {
        Sort ordenacao = Sort.by(sort).ascending();
        if (order == DESCENDING) {
            ordenacao = Sort.by(sort).descending();
        }
        PageRequest pageRequest = PageRequest.of(pagina, tamanho, ordenacao);
        Page<InscricaoEntity> paginaInscricaoEntities = inscricaoRepository.findAll(pageRequest);

        List<InscricaoDto> inscricaoDtos = paginaInscricaoEntities.getContent().stream()
                .map(inscricaoEntity -> converterParaDTO(inscricaoEntity)).toList();

        return new PageDto<>(paginaInscricaoEntities.getTotalElements(),
                paginaInscricaoEntities.getTotalPages(),
                pagina,
                tamanho,
                inscricaoDtos);
    }

    public InscricaoDto findDtoByid(Integer idInscricao) throws RegraDeNegocioException {
        InscricaoDto inscricaoDto = converterParaDTO(findById(idInscricao));
        return inscricaoDto;
    }

    public void delete(Integer id) throws RegraDeNegocioException {
        findById(id);
        inscricaoRepository.deleteById(id);
    }


    private InscricaoEntity findById(Integer idInscricao) throws RegraDeNegocioException {
        return inscricaoRepository.findById(idInscricao)
                .orElseThrow(() -> new RegraDeNegocioException("ID_Inscrição inválido"));

    }

    public InscricaoDto converterParaDTO(InscricaoEntity inscricaoEntity) {
        InscricaoDto inscricaoDto = objectMapper.convertValue(inscricaoEntity, InscricaoDto.class);
        inscricaoDto.setCandidato(candidatoService.convertToDto(inscricaoEntity.getCandidato()));
        return inscricaoDto;
    }

    private InscricaoEntity convertToEntity(InscricaoCreateDto inscricaoCreateDto) {
        InscricaoEntity inscricaoEntity = objectMapper.convertValue(inscricaoCreateDto, InscricaoEntity.class);
        return inscricaoEntity;
    }

    public InscricaoEntity convertToEntity(InscricaoDto inscricaoDto) {
        InscricaoEntity inscricaoEntity = objectMapper.convertValue(inscricaoDto, InscricaoEntity.class);
        inscricaoEntity.setCandidato(candidatoService.convertToEntity(inscricaoDto.getCandidato()));
        return inscricaoEntity;
    }

}
