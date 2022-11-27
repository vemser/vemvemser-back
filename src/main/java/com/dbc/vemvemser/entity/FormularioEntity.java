package com.dbc.vemvemser.entity;

import com.dbc.vemvemser.enums.TipoMarcacao;
import com.dbc.vemvemser.enums.TipoTurno;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_FORMULARIO")
    @SequenceGenerator(name = "SEQ_FORMULARIO", sequenceName = "SEQ_FORMULARIO", allocationSize = 1)
    @Column(name = "id_formulario")
    private Integer idFormulario;

    @Column(name = "MATRICULA")
    @Enumerated(EnumType.STRING)
    private TipoMarcacao matriculado;

    @Column(name = "CURSO")
    private String curso;

    @Column(name = "TURNO")
    @Enumerated(EnumType.ORDINAL)
    private TipoTurno turno;

    @Column(name = "INSTITUICAO")
    private String instituicao;

    @Column(name = "GITHUB")
    private String github;

    @Column(name = "DESAFIOS")
    @Enumerated(EnumType.STRING)
    private TipoMarcacao desafios;

    @Column(name = "PROBLEMAS")
    @Enumerated(EnumType.STRING)
    private TipoMarcacao problema;

    @Column(name = "RECONHECIMENTO")
    @Enumerated(EnumType.STRING)
    private TipoMarcacao reconhecimento;

    @Column(name = "ALTRUISMO")
    @Enumerated(EnumType.STRING)
    private TipoMarcacao altruismo;

    @Column(name = "OUTRO")
    @Enumerated(EnumType.STRING)
    private TipoMarcacao outro;

    @Column(name = "MOTIVO")
    private String motivo;

    @Column(name = "CURRICULO")
    private File curriculo;

    @Column(name = "LGPD")
    @Enumerated(EnumType.STRING)
    private TipoMarcacao lgpd;

    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY)
    private InscricaoEntity inscricaoCandidato;

}
