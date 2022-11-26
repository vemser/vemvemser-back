package com.dbc.vemvemser.controller;

import com.dbc.vemvemser.dto.LoginCreateDto;
import com.dbc.vemvemser.dto.LoginDto;
import com.dbc.vemvemser.exception.RegraDeNegocioException;
import com.dbc.vemvemser.service.LoginService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
@Validated
@RequiredArgsConstructor
public class LoginController {
    private final LoginService loginService;

    @Operation(summary = "Logar com um registro de funcionário.", description = "Loga no sistema com um login de funcionário.")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Loga no sistema com um login de funcionário."),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @PostMapping
    public LoginDto auth(@RequestBody @Valid LoginCreateDto loginCreateDto) throws RegraDeNegocioException {
        return loginService.autenticarUsuario(loginCreateDto);
    }


}
