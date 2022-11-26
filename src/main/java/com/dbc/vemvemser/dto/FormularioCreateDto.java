package com.dbc.vemvemser.dto;

import com.dbc.vemvemser.enums.TipoTurno;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.File;

@Data
public class FormularioCreateDto {


    @NotNull
    private boolean matricula;

    @NotNull
    private String curso;

    @NotNull
    private TipoTurno turno;

    @NotNull
    private String instituicao;

    private String github;

    @NotNull
    private boolean desafios;

    @NotNull
    private boolean problema;

    @NotNull
    private boolean reconhecimento;

    @NotNull
    private boolean altruismo;

    private boolean outro;

    @NotNull
    private String motivo;

    @NotNull
    private File curriculo;

    @NotNull
    private boolean lgpd;
}
