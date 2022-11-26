package com.dbc.vemvemser.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.text.Normalizer;
import java.time.LocalDate;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "INSCRICAO")
public class InscricaoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "INSCRICAO_SEQ")
    @SequenceGenerator(name = "INSCRICAO_SEQ", sequenceName = "SEQ_INSCRICAO", allocationSize = 1)
    @Column(name = "ID_INSCRICAO")
    private Integer idInscricao;


    @OneToOne(mappedBy = "inscricaoCandidato", fetch = FetchType.LAZY)
    private FormularioEntity formulario;

    @OneToOne(mappedBy ="inscricaoCandidato" ,fetch = FetchType.LAZY)
    private CandidatoEntity candidato;

    @Column(name = "DATA_INSCRICAO")
    private LocalDate dataInscricao;

    @Column(name = "AVALIADO")
    private String avaliacao;

    @OneToOne
    @JoinColumn(name="ID_AVALIACAO", referencedColumnName = "ID_AVALIACAO")
    private AvaliacaoEntity avaliacaoEntity;

}
