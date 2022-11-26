package com.dbc.vemvemser.entity;


import com.dbc.vemvemser.enums.TipoMarcacao;
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

    @OneToOne(mappedBy = "avaliacaoEntity",fetch = FetchType.LAZY)
    private InscricaoEntity inscricaoEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_LOGIN", referencedColumnName = "ID_LOGIN")
    private LoginEntity login;

    @Column(name="APROVADO")
    private TipoMarcacao aprovado;

}
