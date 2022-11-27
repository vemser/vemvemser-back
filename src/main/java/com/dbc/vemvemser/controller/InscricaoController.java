package com.dbc.vemvemser.controller;

import com.dbc.vemvemser.dto.InscricaoCreateDto;
import com.dbc.vemvemser.dto.InscricaoDto;
import com.dbc.vemvemser.exception.RegraDeNegocioException;
import com.dbc.vemvemser.service.InscricaoService;
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
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/inscricao")
public class InscricaoController {

    private final InscricaoService inscricaoService;

    @Operation(summary = "Criar uma inscrição", description = "Criar uma inscrição com o ID do candidate e ID do formulario.")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Criou uma inscrição com sucesso"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @PostMapping
    public ResponseEntity<InscricaoDto> create(@RequestBody @Valid InscricaoCreateDto inscricaoCreateDto) {
        log.info("ENTROU NO CHAMADO DO CREATE");
        InscricaoDto inscricaoDto=inscricaoService.create(inscricaoCreateDto);
        return new ResponseEntity<>(inscricaoDto, HttpStatus.OK);
    }

    @Operation(summary = "Procura uma inscrição", description = "Procura uma inscrição por ID da inscrição.")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Encontrou uma inscrição com sucesso"),
                    @ApiResponse(responseCode = "400", description = "ID _Inscrição inválido"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )

    @GetMapping("/{id}")
    public ResponseEntity<InscricaoDto> listById(@RequestParam ("id") Integer id) throws RegraDeNegocioException {
        InscricaoDto inscricaoDto=inscricaoService.listById(id);
        return new ResponseEntity<>(inscricaoDto, HttpStatus.OK);
    }
    @Operation(summary = "Busca toda lista de inscrições", description = "Retonar uma lista com todas inscrições do Banco de dados.")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retornou uma lista de Inscrições com sucesso"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping
    public ResponseEntity<List<InscricaoDto>> list() {
        return new ResponseEntity<>(inscricaoService.list(), HttpStatus.OK);
    }

    @Operation(summary = "Atualizar inscrição", description = "Atualiza uma inscrição por iID")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Atualizou uma inscrição com sucesso"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @PutMapping ("/{id-inscricao}")
    public ResponseEntity <InscricaoDto> update(@RequestParam ("id-inscricao") Integer idInscricao,@RequestBody InscricaoCreateDto inscricaoCreateDto) throws RegraDeNegocioException {

        InscricaoDto inscricaoDto= inscricaoService.update(idInscricao,inscricaoCreateDto);

        return new ResponseEntity<>( inscricaoDto, HttpStatus.OK);
    }

    @Operation(summary = "Deleta inscrição por ID", description = "Deleta inscrição por ID")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Deletou inscrição com sucesso"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @DeleteMapping ("/{id-inscricao}")
    public void  delete(@RequestParam ("id-inscricao") Integer idInscricao) throws RegraDeNegocioException {

        inscricaoService.delete(idInscricao);

    }

}
