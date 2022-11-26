package com.dbc.vemvemser.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity(name = "CARGO")
public class CargoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CARGO_SEQUENCIA")
    @SequenceGenerator(name = "CARGO_SEQUENCIA", sequenceName = "SEQ_CARGO", allocationSize = 1)
    @Column(name = "ID_CARGO")
    private Integer idCargo;

    private String descricao;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    @MapsId("idCargo")
    @JoinColumn(name = "Id_Cargo")
    private GestorEntity gestor;
}
