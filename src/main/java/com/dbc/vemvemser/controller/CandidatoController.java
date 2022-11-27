package com.dbc.vemvemser.controller;

import com.dbc.vemvemser.dto.CandidatoCreateDto;
import com.dbc.vemvemser.dto.CandidatoDto;
import com.dbc.vemvemser.exception.RegraDeNegocioException;
import com.dbc.vemvemser.service.CandidatoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/candidato")
@Validated
@RequiredArgsConstructor
public class CandidatoController {

    private final CandidatoService candidatoService;

    @Operation(summary = "Cadastrar Candidato", description = "Cadastro de candidato")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "201", description = "Cadastro realizado com sucesso"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @PostMapping("/cadastro")
    public ResponseEntity<CandidatoDto> cadastroCandidato(@Valid @RequestBody CandidatoCreateDto candidatoCreateDto) {
        return ResponseEntity.ok(candidatoService.cadastro(candidatoCreateDto));
    }

    @Operation(summary = "Listar Candidato", description = "Lista de candidatos")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "201", description = "Retorna lista de candidatos."),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping("/listar")
    public ResponseEntity<List<CandidatoDto>> listarCandidatos() {
        return new ResponseEntity<>(candidatoService.listAll(), HttpStatus.OK);
    }

    @Operation(summary = "Deletar Candidato", description = "deleta o candidato")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "201", description = "Candidato deletado com sucesso"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @PostMapping("/deletar")
    public void deletarCandidato(@RequestParam Integer idCandidato) throws RegraDeNegocioException {
        candidatoService.deleteById(idCandidato);
        new ResponseEntity<>(null,HttpStatus.OK);
    }

    @Operation(summary = "Atualizar Candidato", description = "Atualiza o candidato")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "201", description = "Candidato atualizado com sucesso"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @PostMapping("/update")
    public ResponseEntity<CandidatoDto> atualizarCandidato(@RequestParam Integer idCandidato,@RequestBody @Valid CandidatoCreateDto candidatoCreateDto) throws RegraDeNegocioException{
        CandidatoDto candidatoDto = candidatoService.update(idCandidato,candidatoCreateDto);
        return new ResponseEntity<>(candidatoDto,HttpStatus.OK);
    }







}
