package com.dbc.vemvemser.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
public class AvaliacaoEntity {

<<<<<<< HEAD
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
=======
>>>>>>> a90e43a94915934588999502cc5a18b130a64744

}
