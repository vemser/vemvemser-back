package com.dbc.vemvemser.controller;

import com.dbc.vemvemser.dto.AvaliacaoCreateDto;
import com.dbc.vemvemser.dto.AvaliacaoDto;
import com.dbc.vemvemser.exception.RegraDeNegocioException;
import com.dbc.vemvemser.service.AvaliacaoService;
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
    @PostMapping
    public ResponseEntity<AvaliacaoDto> create (@RequestBody AvaliacaoCreateDto avaliacaoCreateDto) throws RegraDeNegocioException {
       AvaliacaoDto avaliacaoDto= avaliacaoService.create(avaliacaoCreateDto);

    return new ResponseEntity<>(avaliacaoDto, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<AvaliacaoDto>> listAll(){
       return new ResponseEntity<>(avaliacaoService.list(),HttpStatus.OK);
    }


    @PutMapping
    public ResponseEntity<AvaliacaoDto> update(@RequestParam Integer idAvaliacao,
                                               @RequestBody AvaliacaoCreateDto avaliacaoCreateDto) throws RegraDeNegocioException {

        AvaliacaoDto avaliacaoDtoRetorno = avaliacaoService.update(idAvaliacao,avaliacaoCreateDto);

        return new ResponseEntity<>(avaliacaoDtoRetorno,HttpStatus.OK);
    }


    @DeleteMapping
    public ResponseEntity<Void> delete(Integer idAvaliacao) throws RegraDeNegocioException {
        avaliacaoService.deleteById(idAvaliacao);

        return new ResponseEntity<>(null,HttpStatus.OK);
    }
}
