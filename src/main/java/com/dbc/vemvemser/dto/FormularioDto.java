package com.dbc.vemvemser.dto;

import com.dbc.vemvemser.enums.TipoTurno;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.File;

@Data
public class FormularioDto {


    @NotNull
    private Integer idFormulario;

    private boolean matricula;

    private String curso;

    private TipoTurno turno;

    private String instituicao;

    private String github;

    private boolean desafios;

    private boolean problema;

    private boolean reconhecimento;

    private boolean altruismo;

    private boolean outro;

    private String motivo;

    private File curriculo;

    private boolean lgpd;
}
