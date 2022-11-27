package com.dbc.vemvemser.controller;


import com.dbc.vemvemser.dto.FormularioCreateDto;
import com.dbc.vemvemser.dto.FormularioDto;
import com.dbc.vemvemser.exception.RegraDeNegocioException;
import com.dbc.vemvemser.service.FormularioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/formulario")
@Validated
@RequiredArgsConstructor
public class FormularioController {

    private final FormularioService formularioService;


    @Operation(summary = "Criar um formulário", description = "Criar um formulário para o candidato.")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Criou com sucesso o formulário"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @PostMapping
    public ResponseEntity<FormularioDto> create(@RequestBody @Valid FormularioCreateDto formularioCreateDto) {
        FormularioDto formularioDto=formularioService.create(formularioCreateDto);
        return new ResponseEntity<>(formularioDto, HttpStatus.OK);
    }

    @Operation(summary = "Cadastrar Candidato", description = "Cadastro de candidato")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "201", description = "Cadastro realizado com sucesso"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )

    @GetMapping
    public ResponseEntity<List<FormularioDto>> listAll() {
        log.info("ENTROU NO METODOD");
        return new ResponseEntity<>(formularioService.list(), HttpStatus.OK);
    }

    @DeleteMapping
    public void deletarFormulario(@RequestParam Integer idFormulario) throws RegraDeNegocioException {
        formularioService.deleteById(idFormulario);
        new ResponseEntity<>(null,HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<FormularioDto> updateFormulario(@RequestParam Integer idFormulario, @RequestBody @Valid FormularioCreateDto formularioCreateDto) throws RegraDeNegocioException{
        FormularioDto formularioDto = formularioService.update(idFormulario,formularioCreateDto);
        return new ResponseEntity<>(formularioDto,HttpStatus.OK);
    }

}