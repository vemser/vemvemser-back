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
    private TipoMarcacao avaliacao;

    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="ID_FORMULARIO", referencedColumnName = "ID_FORMULARIO")
    private FormularioEntity formulario;

    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="ID_CANDIDATO", referencedColumnName = "ID_CANDIDATO")
    private CandidatoEntity candidato;

    @JsonIgnore
    @OneToOne
    @JoinColumn(name="ID_AVALIACAO", referencedColumnName = "ID_AVALIACAO")
    private AvaliacaoEntity avaliacaoEntity;

}
