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
public class FormularioCreateDto {


    @NotNull
    private char matricula;

    @NotNull
    private String curso;

    @NotNull
    private String turno;

    @NotNull
    private String instituicao;

    private String github;

    @NotNull
    private char desafios;

    @NotNull
    private char problema;

    @NotNull
    private char reconhecimento;

    @NotNull
    private char altruismo;

    private char outro;

    private String motivo;

//    private File curriculo;

    @NotNull
    private char lgpd;
}
