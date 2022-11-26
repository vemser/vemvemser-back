package com.dbc.vemvemser.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity (name = "INSCRICAO")
public class InscricaoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "INSCRICAO_SEQ")
    @SequenceGenerator(name = "INSCRICAO_SEQ", sequenceName = "SEQ_INSCRICAO", allocationSize = 1)
    @Column(name = "ID_INSCRICAO")
    private Integer idInscricao;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="ID_FORMULARIO", referencedColumnName = "ID_FORMULARIO")
    private FormularioEntity formulario;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="ID_CANDIDATO", referencedColumnName = "ID_CANDIDATO")
    private CandidatoEntity candidato;

    @Column(name = "DATA_INSCRICAO")
    private LocalDate dataInscricao;

    @Column(name = "AVALIADO")
    private String avaliacao;

}
