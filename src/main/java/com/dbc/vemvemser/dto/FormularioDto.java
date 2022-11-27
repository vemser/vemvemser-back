package com.dbc.vemvemser.dto;

import com.dbc.vemvemser.enums.TipoMarcacao;
import com.dbc.vemvemser.enums.TipoTurno;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FormularioDto {



    private Integer idFormulario;


    private TipoMarcacao matriculado;


    private String curso;


    private TipoTurno turno;


    private String instituicao;


    private String github;


    private TipoMarcacao desafios;


    private TipoMarcacao problema;

    private TipoMarcacao reconhecimento;

    private TipoMarcacao altruismo;

    private TipoMarcacao outro;

    private String motivo;

    private String curriculo;

    private TipoMarcacao lgpd;
}
