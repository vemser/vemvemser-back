package com.dbc.vemvemser.controller;


import com.dbc.vemvemser.dto.FormularioCreateDto;
import com.dbc.vemvemser.dto.FormularioDto;
import com.dbc.vemvemser.dto.PageDto;
import com.dbc.vemvemser.enums.TipoMarcacao;
import com.dbc.vemvemser.enums.TipoTurno;
import com.dbc.vemvemser.exception.RegraDeNegocioException;
import com.dbc.vemvemser.service.FormularioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
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
    @PostMapping("/cadastro")
    public ResponseEntity<FormularioDto> create(@RequestBody FormularioCreateDto formularioCreateDto) throws RegraDeNegocioException {
        FormularioDto formularioDto = formularioService.create(formularioCreateDto);
        log.info("Criando Formulario ID:" + formularioDto.getIdFormulario());
        return new ResponseEntity<>(formularioDto, HttpStatus.OK);
    }


    @Operation(summary = "Update curriculo", description = "Update curriculo por ID Formulario.")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Atualizou curriculo com sucesso"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @PutMapping(value = "/update-curriculo-by-id-formulario", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public void updateCurriculo(@RequestParam Integer idFormulario,
                                @RequestBody String curriculoBase64) throws RegraDeNegocioException {
        formularioService.updateCurriculo(curriculoBase64, idFormulario);
        log.info("Atualizando Formulario ID: " + idFormulario);
        new ResponseEntity<>(null, HttpStatus.OK);
    }


    @Operation(summary = "Retorna curriculo em Base64", description = "Retorna curriculo em Base64 por ID de formulario")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retornou curriculo em Base64 com sucesso"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping("/get-curriculo-by-id-formulario")
    public ResponseEntity<String> retornarCurriculoPDF(@RequestParam Integer idFormulario) throws RegraDeNegocioException {
        log.info("Recuperando Curriculo referente ao Formulario ID: " + idFormulario);
        return new ResponseEntity<>(formularioService.retornarCurriculoDoCandidatoDecode(idFormulario), HttpStatus.OK);
    }

    @Operation(summary = "Listar todos formularios", description = "Listar todos formularios")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Cadastro realizado com sucesso"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping("/listar")
    public ResponseEntity<PageDto<FormularioDto>> listAll(@RequestParam(defaultValue = "0", required = false) Integer pagina,
                                                          @RequestParam(defaultValue = "10", required = false) Integer tamanho,
                                                          @RequestParam(defaultValue = "idFormulario", required = false) String sort,
                                                          @RequestParam(defaultValue = "0", required = false) int order) {
        log.info("Listando todos os formulários");
        return new ResponseEntity<>(formularioService.listAllPaginado(pagina, tamanho, sort, order), HttpStatus.OK);
    }

    @Operation(summary = "Atualizar Formulario", description = "Atualizar formulario por ID")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Atualizado com sucesso"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @PutMapping
    public ResponseEntity<FormularioDto> updateFormulario(@RequestParam Integer idFormulario,
                                                          @RequestBody @Valid FormularioCreateDto formularioCreateDto) throws RegraDeNegocioException {
        FormularioDto formularioDto = formularioService.update(idFormulario, formularioCreateDto);
        log.info("Atualizando Formulario ID: " + idFormulario);
        return new ResponseEntity<>(formularioDto, HttpStatus.OK);
    }

    @Operation(summary = "Deletar Formulario", description = "Deletar formulario por ID")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Atualizado com sucesso"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @DeleteMapping
    public void deletarFormulario(@RequestParam Integer idFormulario) throws RegraDeNegocioException {
        formularioService.deleteById(idFormulario);
        log.info("Deletando Formulario ID: " + idFormulario);
        new ResponseEntity<>(null, HttpStatus.OK);
    }
}
