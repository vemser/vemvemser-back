package com.dbc.vemvemser.entity;

import com.dbc.vemvemser.enums.TipoTurno;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.File;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class FormularioEntity {

    @Id
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
