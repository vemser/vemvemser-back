package com.dbc.vemvemser.controller;

import com.dbc.vemvemser.dto.GestorCreateDto;
import com.dbc.vemvemser.dto.GestorDto;
import com.dbc.vemvemser.dto.LoginCreateDto;
import com.dbc.vemvemser.dto.LoginDto;
import com.dbc.vemvemser.exception.RegraDeNegocioException;
import com.dbc.vemvemser.service.GestorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/Gestor")
@Validated
@RequiredArgsConstructor
public class GestorController {

    private final GestorService gestorService;


    @Operation(summary = "Logar com um registro de funcionário.", description = "Loga no sistema com um login de funcionário.")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Loga no sistema com um login de funcionário."),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @PostMapping
    public GestorDto auth(@RequestBody @Valid GestorCreateDto gestorCreateDto) throws RegraDeNegocioException {
        return gestorService.autenticarUsuario(gestorCreateDto);
    }

    @Operation(summary = "Cadastrar um novo colaborador/administrador", description = "Cadastro de colaborador/administrador")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "201", description = "Cadastro realizado com sucesso"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @PostMapping("/cadastro")
    public ResponseEntity<GestorDto> cadastroCandidato(@Valid @RequestBody GestorCreateDto gestorCreateDto) throws RegraDeNegocioException {
        return ResponseEntity.ok(gestorService.cadastrar(gestorCreateDto));
    }
}
