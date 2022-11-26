package com.dbc.vemvemser.entity;

import com.dbc.vemvemser.enums.TipoTurno;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.File;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "FORMULARIO")
public class FormularioEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "FORMULARIO_SEQ")
    @SequenceGenerator(name = "FORMULARIO_SEQ", sequenceName = "SEQ_FORMULARIO", allocationSize = 1)
    @Column(name = "ID_FORMULARIO")
    private Integer idFormulario;

    @Column(name = "MATRICULADO")
    private boolean matricula;

    @Column(name = "CURSO")
    private String curso;

    @Column(name = "TURNO")
    private TipoTurno turno;

    @Column(name = "INSTITUICAO")
    private String instituicao;

    @Column(name = "GITHUB")
    private String github;

    @Column(name = "DESAFIOS")
    private boolean desafios;

    @Column(name = "PROBLEMAS")
    private boolean problema;

    @Column(name = "RECONHECIMENTO")
    private boolean reconhecimento;

    @Column(name = "ALTRUISMO")
    private boolean altruismo;

    @Column(name = "OUTRO")
    private boolean outro;

    @Column(name = "MOTIVO")
    private String motivo;

    @Column(name = "CURRICULO")
    private File curriculo;

    @Column(name = "LGPD")
    private boolean lgpd;

}
