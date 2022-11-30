package com.dbc.vemvemser.service;


import com.dbc.vemvemser.dto.*;
import com.dbc.vemvemser.entity.GestorEntity;
import com.dbc.vemvemser.exception.RegraDeNegocioException;
import com.dbc.vemvemser.repository.GestorRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GestorService {

    private static final int DESCENDING = 1;
    private final GestorRepository gestorRepository;
    private final CargoService cargoService;
    private final ObjectMapper objectMapper;

    private static final int USUARIO_ATIVO = 1;

    private static final int USUARIO_INATIVO = 0;


    public GestorDto autenticarUsuario(LoginCreateDto loginCreateDto) throws RegraDeNegocioException {
        GestorEntity gestorEntity = gestorRepository.findGestorEntityByEmailAndAndSenha(loginCreateDto.getEmail(), loginCreateDto.getSenha())
                .orElseThrow(() -> new RegraDeNegocioException("Usuario ou senha invalido!"));
        return convertToDto(gestorEntity);
    }

    public GestorDto cadastrar(GestorCreateDto gestorCreateDto) throws RegraDeNegocioException {
        GestorEntity gestorEntity = convertToEntity(gestorCreateDto);
        return convertToDto(gestorRepository.save(gestorEntity));
    }

    public PageDto<GestorDto> listar(Integer pagina, Integer tamanho, String sort, int order) {
        Sort ordenacao = Sort.by(sort).ascending();
        if (order == DESCENDING) {
            ordenacao = Sort.by(sort).descending();
        }
        PageRequest pageRequest = PageRequest.of(pagina, tamanho, ordenacao);
        Page<GestorEntity> paginaGestorEntities = gestorRepository.findAll(pageRequest);
        List<GestorDto> gestorDtos = paginaGestorEntities.getContent().stream()
                .map(gestorEntity -> convertToDto(gestorEntity)).toList();
        return new PageDto<>(paginaGestorEntities.getTotalElements(),
                paginaGestorEntities.getTotalPages(),
                pagina,
                tamanho,
                gestorDtos);
    }

    public GestorDto findDtoById(Integer idGestor) throws RegraDeNegocioException {
        GestorEntity gestorEntity = findById(idGestor);
        GestorDto gestorDto = convertToDto(gestorEntity);
        return gestorDto;
    }

    private GestorEntity findById(Integer id) throws RegraDeNegocioException {
        return gestorRepository.findById(id)
                .orElseThrow(() -> new RegraDeNegocioException("Usuario não encontrado!"));
    }

    public GestorDto editar(Integer id, GestorUpdateDto gestorAtualizar) throws RegraDeNegocioException {
        GestorEntity gestorEntity = objectMapper.convertValue(gestorAtualizar, GestorEntity.class);
        gestorEntity.setCargoEntity(cargoService.findById(gestorAtualizar.getTipoCargo()));
        gestorEntity.setIdGestor(id);
        gestorRepository.save(gestorEntity);
        GestorDto gestorDto = convertToDto(gestorEntity);
        return gestorDto;

    }

    public void remover(Integer id) throws RegraDeNegocioException {
        findById(id);
        gestorRepository.deleteById(id);
    }

//    public Integer getIdLoggedUser() {
//        return Integer.parseInt(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());
//    }
//
//    public GestorDto getLoggedUser() throws RegraDeNegocioException {
//        return objectMapper.convertValue(findById(getIdLoggedUser()), GestorDto.class);
//    }


//    public GestorDto desativarConta(Integer idUsuario) throws RegraDeNegocioException {
//        GestorEntity usuarioEncontrado = findById(idUsuario);
//        usuarioEncontrado.setAtivo(USUARIO_INATIVO);
//        gestorRepository.save(usuarioEncontrado);
//
//        return objectMapper.convertValue(usuarioEncontrado, GestorDto.class);
//    }
//    public List<GestorDto> contasInativas(){
//        return gestorRepository.findByAtivo(USUARIO_INATIVO).stream()
//                .map(gestorEntity -> objectMapper.convertValue(gestorEntity, GestorDto.class))
//                .toList();
//    }

    public GestorDto convertToDto(GestorEntity gestorEntity) {
        GestorDto gestorDto = objectMapper.convertValue(gestorEntity, GestorDto.class);
        gestorDto.setCargoDto(cargoService.convertToDto(gestorEntity.getCargoEntity()));
        return gestorDto;
    }

    private GestorEntity convertToEntity(GestorCreateDto gestorCreateDto) throws RegraDeNegocioException {
        GestorEntity gestorEntity = objectMapper.convertValue(gestorCreateDto, GestorEntity.class);
        gestorEntity.setCargoEntity(cargoService.findById(gestorCreateDto.getTipoCargo()));
        return gestorEntity;
    }

    public GestorEntity convertToEntity(GestorDto gestorDto) {
        GestorEntity gestorEntity = objectMapper.convertValue(gestorDto, GestorEntity.class);
        gestorEntity.setCargoEntity(cargoService.convertToEntity(gestorDto.getCargoDto()));
        return gestorEntity;
    }


}
