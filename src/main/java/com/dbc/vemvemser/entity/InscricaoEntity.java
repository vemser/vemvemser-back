package com.dbc.vemvemser.entity;

import com.dbc.vemvemser.enums.TipoMarcacao;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
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

    @Column(name = "DATA_INSCRICAO")
    private LocalDate dataInscricao;

    @Column(name = "AVALIADO")
    @Enumerated(EnumType.STRING)
    private TipoMarcacao avaliado;

    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY, orphanRemoval = true)
    @JoinColumn(name="ID_CANDIDATO", referencedColumnName = "ID_CANDIDATO")
    private CandidatoEntity candidato;

    @OneToOne(mappedBy ="inscricao" ,fetch = FetchType.LAZY)
    private AvaliacaoEntity avaliacaoEntity;

}
