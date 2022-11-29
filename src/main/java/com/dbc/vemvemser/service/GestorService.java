package com.dbc.vemvemser.service;


import com.dbc.vemvemser.dto.*;
import com.dbc.vemvemser.entity.CargoEntity;
import com.dbc.vemvemser.entity.FormularioEntity;
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
        GestorEntity gestorEntity = gestorRepository.findGestorEntityByEmailAndAndSenha(loginCreateDto.getEmail(),loginCreateDto.getSenha())
                .orElseThrow(()-> new RegraDeNegocioException("Usuario ou senha invalido!"));
        return objectMapper.convertValue(gestorEntity, GestorDto.class);
    }

    public GestorDto cadastrar(GestorCreateDto gestorCreateDto) throws RegraDeNegocioException {
        CargoEntity cargo = cargoService.findById(gestorCreateDto.getTipoCargo());
        GestorEntity gestorEntity = new GestorEntity();
        gestorEntity.setNome(gestorCreateDto.getNome());
        gestorEntity.setCargoEntity(cargo);
        gestorEntity.setEmail(gestorCreateDto.getEmail());
        gestorEntity.setSenha(gestorCreateDto.getSenha());


        return objectMapper.convertValue(gestorRepository.save(gestorEntity), GestorDto.class);
    }

    public PageDto<GestorDto> listar(Integer pagina, Integer tamanho, String sort, int order) {
        Sort ordenacao = Sort.by(sort).ascending();
        if (order == DESCENDING) {
            ordenacao = Sort.by(sort).descending();
        }
        PageRequest pageRequest = PageRequest.of(pagina, tamanho, ordenacao);
        Page<GestorEntity> paginaGestorEntities = gestorRepository.findAll(pageRequest);

        List<GestorDto> gestorDtos = paginaGestorEntities.getContent().stream()
                .map(gestorEntity -> objectMapper.convertValue(gestorEntity, GestorDto.class)).toList();

        return new PageDto<>(paginaGestorEntities.getTotalElements(),
                paginaGestorEntities.getTotalPages(),
                pagina,
                tamanho,
                gestorDtos);
    }

    public GestorDto findByIdDTO(Integer idGestor) throws RegraDeNegocioException {
       GestorDto gestorDto= objectMapper.convertValue(findById(idGestor),GestorDto.class);
       return gestorDto;
    }

    private GestorEntity findById(Integer id) throws RegraDeNegocioException {
        return gestorRepository.findById(id)
                .orElseThrow(() -> new RegraDeNegocioException("Usuario não encontrado!"));
    }

    public GestorDto editar(Integer id, GestorCreateDto gestorAtualizar) throws RegraDeNegocioException {
        GestorEntity gestorEncontrado = findById(id);
        gestorEncontrado.setNome(gestorAtualizar.getNome());
        gestorEncontrado.setEmail(gestorAtualizar.getEmail());
        gestorEncontrado.setSenha(gestorAtualizar.getSenha());
        gestorEncontrado.setCargoEntity(cargoService.findById(gestorAtualizar.getTipoCargo()));

        gestorRepository.save(gestorEncontrado);

        GestorDto usuarioDto = objectMapper.convertValue(gestorEncontrado, GestorDto.class);

        return usuarioDto;

    }

    public void remover(Integer id) throws RegraDeNegocioException {
        GestorEntity gestorEntity = findById(id);
        GestorDto gestorDto = objectMapper.convertValue(gestorEntity, GestorDto.class);
        gestorRepository.delete(gestorEntity);

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


}
