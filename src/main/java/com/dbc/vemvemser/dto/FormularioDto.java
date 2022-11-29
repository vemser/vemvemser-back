package com.dbc.vemvemser.dto;

import com.dbc.vemvemser.enums.TipoMarcacao;
import com.dbc.vemvemser.enums.TipoTurno;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

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

    private String curriculo;

    private TipoMarcacao lgpd;

    private TipoMarcacao prova;

    private String ingles;

    private String espanhol;

    private String neurodiversidade;

    private String configurações;

    private TipoMarcacao efetivacao;

    private TipoMarcacao disponibilidade;
}