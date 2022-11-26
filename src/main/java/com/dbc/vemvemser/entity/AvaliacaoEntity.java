package com.dbc.vemvemser.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
public class AvaliacaoEntity {

    @Id
    @GeneratedValue(strategy =  GenerationType.SEQUENCE, generator = "SEQ_AVALIACAO")
    @SequenceGenerator(name = "SEQ_AVALIACAO", sequenceName = "SEQ_CARGO", allocationSize = 1)
    @Column(name = "ID_AVALIACAO")
    private Integer idAvaliacao;

    @OneToOne
    @JoinColumn(name="ID_INSCRICAO", referencedColumnName = "ID_INSCRICAO")
    private InscricaoEntity inscricao;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "gestor")
    private GestorEntity gestor;

    @Column(name="APROVADO")
    private boolean aprovado;

}
