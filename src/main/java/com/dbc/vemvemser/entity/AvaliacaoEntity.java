package com.dbc.vemvemser.entity;



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
    @MapsId("idInscricao")
    private InscricaoEntity inscricaoEntity;


//    @JoinColumn(name = "id_gestor")
//    @MapsId("idGestor")
//    @ManyToOne(fetch = FetchType.LAZY)
//    private GestorEntity gestorEntity;

    @Column(name="APROVADO")
    private boolean aprovado;

}
