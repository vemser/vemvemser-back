package com.dbc.vemvemser.controller;


import com.dbc.vemvemser.dto.FormularioCreateDto;
import com.dbc.vemvemser.dto.FormularioDto;
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


//    @Operation(summary = "Criar um formulário", description = "Criar um formulário para o candidato.")
//    @ApiResponses(
//            value = {
//                    @ApiResponse(responseCode = "200", description = "Criou com sucesso o formulário"),
//                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
//                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
//            }
//    )
//    @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE,MediaType.APPLICATION_JSON_VALUE})
//    public ResponseEntity<FormularioDto> create(
//                                                @RequestParam TipoMarcacao matriculado,
//                                                @RequestParam String curso,
//                                                @RequestParam TipoTurno turno,
//                                                @RequestParam String instituicao,
//                                                @RequestParam (required = false, name = "github") String github,
//                                                @RequestParam (required = false, name = "linkedin") String linkedin,
//                                                @RequestParam TipoMarcacao desafios,
//                                                @RequestParam TipoMarcacao problemas,
//                                                @RequestParam TipoMarcacao reconhecimento,
//                                                @RequestParam TipoMarcacao altruismo,
//                                                @RequestParam String resposta,
//                                                @RequestParam TipoMarcacao lgpd,
//                                                @RequestPart (required = false, name = "curriculo") MultipartFile curriculo) throws IOException {
//
//        FormularioDto formularioDto=formularioService.create(matriculado,
//                curso,
//                turno,
//                instituicao,
//                github,
//                linkedin,
//                desafios,
//                problemas,
//                reconhecimento,
//                altruismo,
//                resposta,
//                curriculo,
//                lgpd);
//        log.info("Criando Formulario ID:" + formularioDto.getIdFormulario());
//        return new ResponseEntity<>(formularioDto, HttpStatus.OK);
//    }

    @Operation(summary = "Criar um formulário", description = "Criar um formulário para o candidato.")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Criou com sucesso o formulário"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @PostMapping
    public ResponseEntity<FormularioDto> create(FormularioCreateDto formularioCreateDto) {

        FormularioDto formularioDto=formularioService.create(formularioCreateDto);
        log.info("Criando Formulario ID:" + formularioDto.getIdFormulario());
        return new ResponseEntity<>(formularioDto, HttpStatus.OK);
    }

    @PutMapping(value = "/update-curriculo",consumes = {MediaType.MULTIPART_FORM_DATA_VALUE,MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Void> updateCurriculo (@RequestParam Integer idFormulario,MultipartFile curriculo) throws IOException, RegraDeNegocioException {

        formularioService.updateCurriculo(curriculo, idFormulario);

        log.info("Atualizando Curriculo em Formulario ID: " +idFormulario);

        return new ResponseEntity<>(null,HttpStatus.OK);
    }


    @GetMapping("/get-curriculo/id-formulario")
    public ResponseEntity<String> retornarCurriculoPDF(@RequestParam Integer idFormulario) throws RegraDeNegocioException {
        log.info("Recuperando Curriculo referente ao Formulario ID: " + idFormulario);
        return new ResponseEntity<>(formularioService.retornarCurriculoDoCandidatoDecode(idFormulario), HttpStatus.OK);
    }

    @Operation(summary = "Listar todos formularios", description = "Listar todos formularios")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "201", description = "Cadastro realizado com sucesso"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping
    public ResponseEntity<List<FormularioDto>> listAll() {
        log.info("Listando todos os formulários");
        return new ResponseEntity<>(formularioService.list(), HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<FormularioDto> updateFormulario(@RequestParam Integer idFormulario, @RequestBody @Valid FormularioCreateDto formularioCreateDto) throws RegraDeNegocioException{
        FormularioDto formularioDto = formularioService.update(idFormulario,formularioCreateDto);
        log.info("Atualizando Formulario ID: "+ idFormulario);
        return new ResponseEntity<>(formularioDto,HttpStatus.OK);
    }

    @DeleteMapping
    public void deletarFormulario(@RequestParam Integer idFormulario) throws RegraDeNegocioException {
        formularioService.deleteById(idFormulario);
        log.info("Deletando Formulario ID: " + idFormulario);
        new ResponseEntity<>(null,HttpStatus.OK);
    }
}
