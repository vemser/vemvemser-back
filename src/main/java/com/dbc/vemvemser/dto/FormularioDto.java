package com.dbc.vemvemser.dto;

import com.dbc.vemvemser.enums.TipoMarcacao;
import com.dbc.vemvemser.enums.TipoTurno;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.List;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FormularioDto {

    private Integer idFormulario;

    private TipoMarcacao matriculado;

    private String curso;

    private TipoTurno turno;

    private String instituicao;

    private String github;

    private String linkedin;

    private TipoMarcacao desafios;

    private TipoMarcacao problema;

    private TipoMarcacao reconhecimento;

    private TipoMarcacao altruismo;

    private String resposta;

    @JsonIgnore
    private String curriculo;

    private TipoMarcacao lgpd;

    private TipoMarcacao prova;

    private String ingles;

    private String espanhol;

    private String neurodiversidade;

    private String configuracoes;

    private TipoMarcacao efetivacao;

    private String genero;

    private String orientacao;

    private TipoMarcacao disponibilidade;

    private Set<TrilhaDto> trilhas;

}