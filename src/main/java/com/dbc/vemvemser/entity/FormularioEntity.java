package com.dbc.vemvemser.entity;

import com.dbc.vemvemser.enums.TipoMarcacao;
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
    @Column(name = "id_formulario")
    private Integer idFormulario;

    @Column(name = "MATRICULA")
    private TipoMarcacao matriculado;

    @Column(name = "CURSO")
    private String curso;

    @Column(name = "TURNO")
    private String turno;

    @Column(name = "INSTITUICAO")
    private String instituicao;

    @Column(name = "GITHUB")
    private String github;

    @Column(name = "DESAFIOS")
    private TipoMarcacao desafios;

    @Column(name = "PROBLEMAS")
    private TipoMarcacao problema;

    @Column(name = "RECONHECIMENTO")
    private TipoMarcacao reconhecimento;

    @Column(name = "ALTRUISMO")
    private TipoMarcacao altruismo;

    @Column(name = "OUTRO")
    private TipoMarcacao outro;

    @Column(name = "MOTIVO")
    private String motivo;

    @Column(name = "CURRICULO")
    private File curriculo;

    @Column(name = "LGPD")
    private TipoMarcacao lgpd;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="ID_INSCRICAO", referencedColumnName = "ID_INSCRICAO")
    private InscricaoEntity inscricaoCandidato;

}
