package com.dbc.vemvemser;

import com.dbc.vemvemser.entity.CandidatoEntity;
import com.dbc.vemvemser.repository.CandidatoRepository;
import com.dbc.vemvemser.service.CandidatoService;
import com.dbc.vemvemser.service.FormularioService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CandidatoServiceTest {

    @InjectMocks
    private CandidatoService candidatoService;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Mock
    private CandidatoRepository candidatoRepository;

    @Mock
    private FormularioService formularioService;

    @Test
    public void deveTestarListarComSucesso() {
        // Criar variaveis (SETUP)
        List<CandidatoEntity> lista = new ArrayList<>();
        lista.add(ggetUsuarioEntity());
        when(candidatoRepository.findAll()).thenReturn(lista);

        // Ação (ACT)
        List<UsuarioDto> usuarios = usuarioService.listar();

        // Verificação (ASSERT)
        assertNotNull(usuarios);
        assertTrue(usuarios.size() > 0);
        assertEquals(1, lista.size());
    }



    private static CandidatoEntity getUsuarioEntity() {
        CandidatoEntity candidatoEntity = new CandidatoEntity();
        candidatoEntity.setNome("Eduardo Sedrez Rodrigues");
        usuarioEntity.setIdade(25);
        usuarioEntity.setEmail("luiz@dbccompany.com.br");
        usuarioEntity.setSenha("123");
        usuarioEntity.setAtivo(1);
        usuarioEntity.setCargos(new HashSet<>());
        return usuarioEntity;
    }
}
