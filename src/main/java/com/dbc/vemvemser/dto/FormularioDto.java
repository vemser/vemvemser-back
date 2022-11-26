package com.dbc.vemvemser.dto;

import com.dbc.vemvemser.enums.TipoMarcacao;
import com.dbc.vemvemser.enums.TipoTurno;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.io.File;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FormularioDto {



    private Integer idFormulario;


    private TipoMarcacao matriculado;


    private String curso;


    private String turno;


    private String instituicao;


    private String github;


    private TipoMarcacao desafios;


    private TipoMarcacao problema;

    private TipoMarcacao reconhecimento;

    private TipoMarcacao altruismo;

    private TipoMarcacao outro;

    private String motivo;

    private File curriculo;

    private TipoMarcacao lgpd;
}
