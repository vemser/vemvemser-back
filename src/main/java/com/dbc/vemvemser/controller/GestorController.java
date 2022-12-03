package com.dbc.vemvemser.controller;

import com.dbc.vemvemser.dto.*;
import com.dbc.vemvemser.exception.RegraDeNegocioException;
import com.dbc.vemvemser.service.GestorService;
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

@RestController
@Slf4j
@RequestMapping("/gestor")
@Validated
@RequiredArgsConstructor
public class GestorController {

    private final GestorService gestorService;


    @Operation(summary = "Listar Gestores", description = "Lista todos os gestores do banco de dados")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna a lista de gestores"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping
    public ResponseEntity<PageDto<GestorDto>> listar(@RequestParam(defaultValue = "0", required = false) Integer pagina,
                                                     @RequestParam(defaultValue = "10", required = false) Integer tamanho,
                                                     @RequestParam(defaultValue = "idGestor", required = false) String sort,
                                                     @RequestParam(defaultValue = "0", required = false) int order) {
        log.info("Listando gestores...");
        return new ResponseEntity<>(gestorService.listar(pagina, tamanho, sort, order), HttpStatus.OK);
    }

    @Operation(summary = "Listar Gestores por ID", description = "Retorna um Gestor por ID do banco de dados")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna Gestor"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping("/id-gestor")
    public ResponseEntity<GestorDto> findById(@RequestParam Integer idGestor) throws RegraDeNegocioException {
        log.info("Buscando gestor com id:"+idGestor+"...");
        GestorDto gestorDto = gestorService.findDtoById(idGestor);
        log.info("Gestor encontrado.");
        return new ResponseEntity<>(gestorDto, HttpStatus.OK);
    }


    @Operation(summary = "Cadastrar um novo colaborador/administrador", description = "Cadastro de colaborador/administrador")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Cadastro realizado com sucesso"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @PostMapping("/cadastro")
    public ResponseEntity<GestorDto> cadastroCandidato(@Valid @RequestBody GestorCreateDto gestorCreateDto) throws RegraDeNegocioException {
        log.info("Cadastrando novo gestor...");
        GestorDto gestorDto = gestorService.cadastrar(gestorCreateDto);
        log.info("Gestor cadastrado.");
        return ResponseEntity.ok(gestorDto);
    }

    @Operation(summary = "Atualizar o colaborador/administrador", description = "Atualiza o colaborador/administrador no banco de dados")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Atualizou com sucesso"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @PutMapping("/{idGestor}")
    public ResponseEntity<GestorDto> editar(@PathVariable(name = "idGestor") Integer idGestor,
                                            @Valid @RequestBody GestorCreateDto gestor) throws RegraDeNegocioException {
        log.info("Editando o Gestor...");
        GestorDto gestorEditado = gestorService.editar(idGestor, gestor);
        log.info("Gestor editado com sucesso!");
        return new ResponseEntity<>(gestorEditado, HttpStatus.OK);
    }

    @PutMapping("/trocar-senha/{idGestor}")
    public ResponseEntity<GestorDto> editarSenhaGestor(@PathVariable(name = "idGestor") Integer idGestor,
                                            @Valid @RequestBody GestorSenhaDto gestor) throws RegraDeNegocioException {
        log.info("Editando senha do Gestor...");
        GestorDto gestorEditado = gestorService.editarSenha(idGestor, gestor);
        log.info("Gestor senha editado com sucesso!");
        return new ResponseEntity<>(gestorEditado, HttpStatus.OK);
    }

    @Operation(summary = "Deletar colaborador/administrador", description = "Deletar o colaborador/administrador no banco de dados")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "204", description = "Deletado com sucesso"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "404", description = "Não encontrado"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @DeleteMapping("/{idGestor}")
    public ResponseEntity<Void> remover(@PathVariable(name = "idGestor") Integer idGestor) throws RegraDeNegocioException {
        log.info("Deletando gestor...");
        gestorService.remover(idGestor);
        log.info("Gestor deletado com sucesso");
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Desativar conta", description = "Desativar sua conta do gestor")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Conta desativada com sucesso"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @PutMapping("/desativacao-conta/{idGestor}")
    public ResponseEntity<GestorDto> desativar(@PathVariable(name = "idGestor") Integer idGestor) throws RegraDeNegocioException {
        log.info("Desativando gestor com id:"+idGestor);
        return new ResponseEntity<>(gestorService.desativarConta(idGestor), HttpStatus.OK);

    }

    @Operation(summary = "Listar contas inativas", description = "Listar todas as contas inativas")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna a lista de gestores inativos"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping("/contas-inativas")
    public ResponseEntity<List<GestorDto>> listarContaInativas() {
        log.info("Listando contas inativas");
        return new ResponseEntity<>(gestorService.contasInativas(), HttpStatus.OK);
    }

    @Operation(summary = "Pegar gestor pelo email,nome e cargo", description = "Pegar o gestor pelo nome,email e cargo informado")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Gestor pego com sucesso"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @PostMapping("/gestor-by-nome-email")
    public ResponseEntity<List<GestorDto>> pegarGestorPorEmailNomeCargo(@RequestBody GestorEmailNomeCargoDto gestorEmailNomeCargoDto) throws RegraDeNegocioException {
        log.info("Buscando gestor por cargo e ( email ou nome )");
        return new ResponseEntity<>(gestorService.findGestorbyNomeOrEmail(gestorEmailNomeCargoDto), HttpStatus.OK);
    }

    @Operation(summary = "Pegar conta logada", description = "Pegar sua conta logado no sistema")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Gestor pego com sucesso"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping("/gestor-logado")
    public ResponseEntity<GestorDto> pegarUserLogado() throws RegraDeNegocioException {
        log.info("Buscando gestor logado.");
        return new ResponseEntity<>(gestorService.getLoggedUser(), HttpStatus.OK);
    }

}
