package com.dbc.vemvemser.dto;

import com.dbc.vemvemser.enums.TipoMarcacao;
import com.dbc.vemvemser.enums.TipoTurno;
import lombok.*;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FormularioCreateDto {


    @NotNull
    private TipoMarcacao matriculado;

    @NotNull
    private String curso;

    @NotNull
    private TipoTurno turno;

    @NotNull
    private String instituicao;

    private String github;

    @NotNull
    private TipoMarcacao desafios;

    @NotNull
    private TipoMarcacao problema;

    @NotNull
    private TipoMarcacao reconhecimento;

    @NotNull
    private TipoMarcacao altruismo;

    private TipoMarcacao outro;

    private String motivo;

//    private File curriculo;
    @NotNull
    private TipoMarcacao lgpd;
}
