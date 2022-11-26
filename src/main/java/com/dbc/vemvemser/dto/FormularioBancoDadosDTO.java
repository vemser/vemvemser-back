package com.dbc.vemvemser.dto;

import com.dbc.vemvemser.enums.TipoTurno;

import javax.validation.constraints.NotNull;
import java.io.File;

public class FormularioBancoDadosDTO {

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


    private boolean altruismo;

    private boolean outro;

    private String motivo;


    private File curriculo;


    private boolean lgpd;
}
