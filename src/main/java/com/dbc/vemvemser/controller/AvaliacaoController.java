package com.dbc.vemvemser.controller;

import com.dbc.vemvemser.dto.AvaliacaoCreateDto;
import com.dbc.vemvemser.dto.AvaliacaoDto;
import com.dbc.vemvemser.dto.PageDto;
import com.dbc.vemvemser.exception.RegraDeNegocioException;
import com.dbc.vemvemser.service.AvaliacaoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/avaliacao")
public class AvaliacaoController {

    private final AvaliacaoService avaliacaoService;

    @Operation(summary = "Cadastrar Avaliacao", description = "Cadastro de avaliacao")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Avaliacao cadastrada com sucesso"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @PostMapping
    public ResponseEntity<AvaliacaoDto> create(@RequestBody AvaliacaoCreateDto avaliacaoCreateDto) throws RegraDeNegocioException {
        log.info("Criando Avaliação...");
        AvaliacaoDto avaliacaoDto = avaliacaoService.create(avaliacaoCreateDto);
        log.info("Avaliação criada.");
        return new ResponseEntity<>(avaliacaoDto, HttpStatus.OK);
    }

    @Operation(summary = "Listar todas Avaliações", description = "Retorna uma lista com todas avaliações")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retornou lista de avaliações com sucesso"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping
    public ResponseEntity<PageDto<AvaliacaoDto>> listAll(@RequestParam(defaultValue = "0", required = false) Integer pagina,
                                                         @RequestParam(defaultValue = "10", required = false) Integer tamanho,
                                                         @RequestParam(defaultValue = "idAvaliacao", required = false) String sort,
                                                         @RequestParam(defaultValue = "0", required = false) int order) {
        log.info("Listando avaliações");
        return new ResponseEntity<>(avaliacaoService.list(pagina, tamanho, sort, order), HttpStatus.OK);
    }


    @Operation(summary = "Atualizar Avaliação", description = "Atualizar avaliação por ID")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Avaliacao atualiazada com sucesso"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @PutMapping
    public ResponseEntity<AvaliacaoDto> update(@RequestParam Integer idAvaliacao,
                                               @RequestBody AvaliacaoCreateDto avaliacaoCreateDto) throws RegraDeNegocioException {
        log.info("Atualizando Avaliação...");
        AvaliacaoDto avaliacaoDtoRetorno = avaliacaoService.update(idAvaliacao, avaliacaoCreateDto);
        log.info("Avaliação atualizada.");
        return new ResponseEntity<>(avaliacaoDtoRetorno, HttpStatus.OK);
    }


    @Operation(summary = "Deletar Avaliacao", description = "Deletar uma avaliacao por ID")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Avaliacao excluída com sucesso"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @DeleteMapping
    public ResponseEntity<Void> delete(Integer idAvaliacao) throws RegraDeNegocioException {
        log.info("Deletando Avaliação...");
        avaliacaoService.deleteById(idAvaliacao);
        log.info("Avaliação deletada.");
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    @Operation(summary = "Busca avaliacao por EMAIL", description = "Busca avaliação por email")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna uma avaliacao."),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping("/buscar-by-email")
    public ResponseEntity<List<AvaliacaoDto>> findInscricaoPorEmail(@RequestParam String email) {
        log.info("Buscando avaliação pelo email do candidato...");
        List<AvaliacaoDto> avaliacaoDto = avaliacaoService.findAvaliacaoByCanditadoEmail(email);
        log.info("Retornando avaliações.");
        return new ResponseEntity<>(avaliacaoDto, HttpStatus.OK);
    }
}
