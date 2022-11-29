package com.dbc.vemvemser.controller;

import com.dbc.vemvemser.dto.GestorCreateDto;
import com.dbc.vemvemser.dto.GestorDto;
import com.dbc.vemvemser.dto.LoginCreateDto;
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
@RequestMapping("/Gestor")
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
    public ResponseEntity<List<GestorDto>> listar() {
        return new ResponseEntity<>(gestorService.listar(), HttpStatus.OK);
    }

    @Operation(summary = "Logar com um registro de funcionário.", description = "Loga no sistema com um login de funcionário.")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Loga no sistema com um login de funcionário."),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @PostMapping
    public GestorDto auth(@RequestBody @Valid LoginCreateDto loginCreateDto) throws RegraDeNegocioException {
        return gestorService.autenticarUsuario(loginCreateDto);
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
        return ResponseEntity.ok(gestorService.cadastrar(gestorCreateDto));
    }

    @Operation(summary = "Atualizar o colaborador/administrador", description = "Atualiza o colaborador/administrador no banco de dados")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Atualizou com sucesso"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @PutMapping("/{idUsuario}")
    public ResponseEntity<GestorDto> editar(@PathVariable(name = "idUsuario") Integer idGestor, @Valid @RequestBody GestorCreateDto gestor) throws RegraDeNegocioException {
        log.info("Editando o Usuário...");
        GestorDto gestorEditado = gestorService.editar(idGestor, gestor);
        log.info("Usuário editado com sucesso!");
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
        gestorService.remover(idGestor);
        log.info("Usuário deletado com sucesso");
        return ResponseEntity.noContent().build();
    }

//    @Operation(summary = "Desativar conta", description = "Desativar sua conta do gestor")
//    @ApiResponses(
//            value = {
//                    @ApiResponse(responseCode = "201", description = "Conta desativada com sucesso"),
//                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
//                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
//            }
//    )
//    @PutMapping("/desativacao-conta/{idGestor}")
//    public ResponseEntity<GestorDto> desativar(@PathVariable(name = "idGestor") Integer idGestor) throws RegraDeNegocioException {
//        return new ResponseEntity<>(gestorService.desativarConta(idGestor), HttpStatus.OK);
//
//    }
//
//    @Operation(summary = "Listar contas inativas", description = "Listar todas as contas inativas")
//    @ApiResponses(
//            value = {
//                    @ApiResponse(responseCode = "200", description = "Retorna a lista de gestores inativos"),
//                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
//                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
//            }
//    )
//    @GetMapping("/contas-inativas")
//    public ResponseEntity<List<GestorDto>> listarContaInativas() {
//        return new ResponseEntity<>(gestorService.contasInativas(), HttpStatus.OK);
//    }

//    @Operation(summary = "Pegar conta logada", description = "Pegar sua conta logado no sistema")
//    @ApiResponses(
//            value = {
//                    @ApiResponse(responseCode = "201", description = "Gestor pego com sucesso"),
//                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
//                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
//            }
//    )
//    @GetMapping("/gestor-logado")
//    public ResponseEntity<GestorDto> pegarUserLogado() throws RegraDeNegocioException {
//        return new ResponseEntity<>(gestorService.getLoggedUser(), HttpStatus.OK);
//    }
}
