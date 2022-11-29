package com.dbc.vemvemser.controller;

import com.dbc.vemvemser.dto.*;
import com.dbc.vemvemser.exception.RegraDeNegocioException;
import com.dbc.vemvemser.service.TrilhaService;
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
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/trilha")
public class TrilhaController {

    private final TrilhaService trilhaService;

    public ResponseEntity<TrilhaDto> create(TrilhaCreateDto trilhaCreateDto) {
        TrilhaDto trilhaDto = trilhaService.create(trilhaCreateDto);
        log.info("Criando Formulario ID:" + trilhaDto.getIdTrilha());
        return new ResponseEntity<>(trilhaDto, HttpStatus.OK);
    }

    @GetMapping("/listar")
    public ResponseEntity<List<TrilhaDto>> listAll() {
        log.info("Listando todos os formulários");
        return new ResponseEntity<>(trilhaService.list(), HttpStatus.OK);
    }


    @DeleteMapping
    public void delete(@RequestParam Integer idTrilha) throws RegraDeNegocioException {
        trilhaService.delete(idTrilha);
        log.info("Deletando Formulario ID: " + idTrilha);
        new ResponseEntity<>(null, HttpStatus.OK);
    }
}
