package com.dbc.vemvemser.controller;

import com.dbc.vemvemser.dto.CandidatoCreateDto;
import com.dbc.vemvemser.dto.CandidatoDto;
import com.dbc.vemvemser.dto.PageDto;
import com.dbc.vemvemser.exception.RegraDeNegocioException;
import com.dbc.vemvemser.service.CandidatoService;
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
@RequestMapping("/candidato")
@Validated
@RequiredArgsConstructor
public class CandidatoController {

    private final CandidatoService candidatoService;

    @Operation(summary = "Cadastrar Candidato", description = "Cadastro de candidato")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Cadastro realizado com sucesso"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @PostMapping("/cadastro")
    public ResponseEntity<CandidatoDto> cadastroCandidato(@Valid @RequestBody CandidatoCreateDto candidatoCreateDto) throws RegraDeNegocioException {
        log.info("Cadastrando candidato...");
        CandidatoDto candidatoDto = candidatoService.cadastro(candidatoCreateDto);
        log.info("Candidato cadastrado.");
        return ResponseEntity.ok(candidatoDto);
    }

    @Operation(summary = "Listar Candidato", description = "Lista de candidatos")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna lista de candidatos."),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping("/listar")
    public ResponseEntity<PageDto<CandidatoDto>> listarCandidatos(@RequestParam(defaultValue = "0", required = false) Integer pagina,
                                                                  @RequestParam(defaultValue = "10", required = false) Integer tamanho,
                                                                  @RequestParam(defaultValue = "idCandidato", required = false) String sort,
                                                                  @RequestParam(defaultValue = "0", required = false) int order) {
        log.info("Retornando lista de candidatos.");
        return new ResponseEntity<>(candidatoService.listaAllPaginado(pagina, tamanho, sort, order), HttpStatus.OK);
    }

    @Operation(summary = "Busca candidato por EMAIL", description = "Busca Candidato por EMAIL")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna um Candidato."),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping("/buscar-by-email")
    public ResponseEntity<List<CandidatoDto>> findCandidatoDtoByEmail(@RequestParam String email) throws RegraDeNegocioException {
        log.info("Buscando candidato pelo email...");
        List<CandidatoDto> candidatoDtos = candidatoService.findCandidatoDtoByEmail(email);
        log.info("Retornando candidato.");
        return new ResponseEntity<>(candidatoService.findCandidatoDtoByEmail(email), HttpStatus.OK);
    }

    @Operation(summary = "Listar Candidatos por ID", description = "Retorna um candidato por ID do banco de dados")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna Gestor"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping("/by-idCandidato")
    public ResponseEntity<CandidatoDto> findById(@RequestParam Integer idCandidato) throws RegraDeNegocioException {
        log.info("Buscando candidato por id...");
        CandidatoDto candidatoDto = candidatoService.findDtoById(idCandidato);
        log.info("Retornando candidato encontrado");
        return new ResponseEntity<>(candidatoDto, HttpStatus.OK);
    }

    @Operation(summary = "Deletar Candidato", description = "deleta o candidato")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Candidato deletado com sucesso"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @DeleteMapping("/deletar")
    public void deletarCandidato(@RequestParam Integer idCandidato) throws RegraDeNegocioException {
        log.info("Deletando Candidato...");
        candidatoService.deleteById(idCandidato);
        log.info("Candidato Deletado.");
        new ResponseEntity<>(null, HttpStatus.OK);
    }

    @Operation(summary = "Atualizar Candidato", description = "Atualiza o candidato")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Candidato atualizado com sucesso"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @PutMapping("/update")
    public ResponseEntity<CandidatoDto> atualizarCandidato(@RequestParam Integer idCandidato,
                                                           @RequestBody @Valid CandidatoCreateDto candidatoCreateDto) throws RegraDeNegocioException {
        log.info("Atualizando Candidato...");
        CandidatoDto candidatoDto = candidatoService.update(idCandidato, candidatoCreateDto);
        log.info("Candidato Atualizado.");
        return new ResponseEntity<>(candidatoDto, HttpStatus.OK);
    }


}
