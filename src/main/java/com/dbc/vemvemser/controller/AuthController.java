package com.dbc.vemvemser.controller;

import com.dbc.vemvemser.dto.LoginCreateDto;
import com.dbc.vemvemser.dto.TokenDto;
import com.dbc.vemvemser.entity.GestorEntity;
import com.dbc.vemvemser.enums.TipoMarcacao;
import com.dbc.vemvemser.exception.RegraDeNegocioException;
import com.dbc.vemvemser.security.TokenService;
import com.dbc.vemvemser.service.GestorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Slf4j
@RestController
@Validated
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;

    private final GestorService gestorService;

    private final TokenService tokenService;

    @Operation(summary = "Logar com um registro de funcionário.", description = "Loga no sistema com um login de funcionário.")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Loga no sistema com um login de funcionário."),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @PostMapping("/login")
    public ResponseEntity<TokenDto> auth(@RequestBody @Valid LoginCreateDto loginCreateDto) throws RegraDeNegocioException {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(
                        loginCreateDto.getEmail(),
                        loginCreateDto.getSenha()
                );

        Authentication authenticate = authenticationManager.authenticate(usernamePasswordAuthenticationToken);

        // UsuarioEntity
        Object principal = authenticate.getPrincipal();
        GestorEntity gestorEntity = (GestorEntity) principal;
        if (gestorEntity.getAtivo() != TipoMarcacao.F) {
            TokenDto token = tokenService.getToken(gestorEntity, false);
            return new ResponseEntity<>(token, HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
    }


    @Operation(summary = "Esqueci minha senha.", description = "Faz a requisição para a troca de senha, que enviara um token para o email cadastrado")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Troca de senha solicitada."),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @PostMapping("/forgot-password")
    public ResponseEntity<Void> findEmail(@RequestBody @Valid String email) throws RegraDeNegocioException {

        gestorService.forgotPassword(email);

        return new ResponseEntity<>(null, HttpStatus.OK);
    }
}
