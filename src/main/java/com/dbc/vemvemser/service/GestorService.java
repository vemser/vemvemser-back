package com.dbc.vemvemser.service;


import com.dbc.vemvemser.dto.*;
import com.dbc.vemvemser.entity.CargoEntity;
import com.dbc.vemvemser.entity.GestorEntity;
import com.dbc.vemvemser.enums.TipoEmail;
import com.dbc.vemvemser.enums.TipoMarcacao;
import com.dbc.vemvemser.exception.RegraDeNegocioException;
import com.dbc.vemvemser.repository.GestorRepository;
import com.dbc.vemvemser.security.TokenService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GestorService {

    private static final int DESCENDING = 1;
    private final GestorRepository gestorRepository;
    private final CargoService cargoService;
    private final ObjectMapper objectMapper;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;

    private final EmailService emailService;

    private static final TipoMarcacao USUARIO_ATIVO = TipoMarcacao.T;

    private static final TipoMarcacao USUARIO_INATIVO = TipoMarcacao.F;


    public GestorDto cadastrar(GestorCreateDto gestorCreateDto) throws RegraDeNegocioException {
        if (!gestorCreateDto.getEmail().endsWith("@dbccompany.com.br")) {
            throw new RegraDeNegocioException("Email não valido!");
        }
        GestorEntity gestorEntity = convertToEntity(gestorCreateDto);
        gestorEntity.setAtivo(TipoMarcacao.T);
        gestorEntity.setSenha(passwordEncoder.encode(gestorCreateDto.getSenha()));
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

    // FIXME usar equals
    public GestorDto editar(Integer id, GestorCreateDto gestorCreateDto) throws RegraDeNegocioException {
        if (id != getIdLoggedUser() && findById(getIdLoggedUser()).getCargoEntity().getNome() == "ROLE_COLABORADOR") {
            throw new RegraDeNegocioException("Você não tem permissão para editar esse gestor.");
        }
        GestorEntity gestorEntity = findById(id);
        gestorEntity.setCargoEntity(cargoService.findById(gestorCreateDto.getTipoCargo()));
        gestorEntity.setNome(gestorCreateDto.getNome());
        gestorEntity.setEmail(gestorCreateDto.getEmail());
        if (!gestorCreateDto.getSenha().isBlank()) {
            gestorEntity.setSenha(passwordEncoder.encode(gestorCreateDto.getSenha()));
        }
        gestorRepository.save(gestorEntity);
        GestorDto gestorDto = convertToDto(gestorEntity);
        return gestorDto;

    }

    public GestorDto editarSenha(Integer id, GestorSenhaDto gestorSenhaDto) throws RegraDeNegocioException {
        GestorEntity gestorEntity = findById(id);
        gestorEntity.setSenha(passwordEncoder.encode(gestorSenhaDto.getSenha()));
        gestorRepository.save(gestorEntity);
        GestorDto gestorDto = convertToDto(gestorEntity);
        return gestorDto;

    }

    public List<GestorDto> findGestorbyNomeOrEmail(GestorEmailNomeCargoDto gestorEmailNomeCargoDto) throws RegraDeNegocioException {
        if (gestorEmailNomeCargoDto.getNome().isBlank() && gestorEmailNomeCargoDto.getEmail().isBlank()) {
            return Collections.emptyList();
        }
        CargoEntity cargo = cargoService.findById(gestorEmailNomeCargoDto.getCargo().getCargo());
        List<GestorEntity> lista = gestorRepository.findGestorEntitiesByCargoEntityAndNomeIgnoreCaseOrCargoEntityAndEmailIgnoreCase(cargo, gestorEmailNomeCargoDto.getNome(), cargo, gestorEmailNomeCargoDto.getEmail());
        return lista.stream()
                .map(gestorEntity -> convertToDto(gestorEntity))
                .toList();

    }

    public void remover(Integer id) throws RegraDeNegocioException {
        findById(id);
        gestorRepository.deleteById(id);
    }

    public GestorEntity findByEmail(String email) throws RegraDeNegocioException {
        return gestorRepository.findGestorEntityByEmailEqualsIgnoreCase(email)
                .orElseThrow(() -> new RegraDeNegocioException("Email não encontrado"));
    }

    public void forgotPassword(GestorEmailDto gestorEmailDto) throws RegraDeNegocioException {
        GestorEntity gestorEntity = findByEmail(gestorEmailDto.getEmail());
        TokenDto token = tokenService.getToken(gestorEntity, true);

        SendEmailDto sendEmailDto = new SendEmailDto();
        sendEmailDto.setEmail(gestorEntity.getEmail());
        sendEmailDto.setNome(gestorEntity.getNome());
        String url = gestorEmailDto.getUrl();
        sendEmailDto.setUrlToken(url + "/forgot-password/?token=" + token.getToken());
        emailService.sendEmail(sendEmailDto, TipoEmail.RECOVER_PASSWORD);
    }

    public Integer getIdLoggedUser() {
        return Integer.parseInt(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());
    }

    public GestorDto getLoggedUser() throws RegraDeNegocioException {
        return convertToDto(findById(getIdLoggedUser()));
    }


    public GestorDto desativarConta(Integer idUsuario) throws RegraDeNegocioException {
        GestorEntity usuarioEncontrado = findById(idUsuario);
        usuarioEncontrado.setAtivo(USUARIO_INATIVO);
        gestorRepository.save(usuarioEncontrado);
        return convertToDto(usuarioEncontrado);
    }

    public List<GestorDto> contasInativas() {
        return gestorRepository.findByAtivo(USUARIO_INATIVO).stream()
                .map(gestorEntity -> convertToDto(gestorEntity))
                .toList();
    }

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
