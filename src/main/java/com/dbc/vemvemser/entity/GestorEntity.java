package com.dbc.vemvemser.entity;

import com.dbc.vemvemser.enums.TipoCargo;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;


@Getter
@Setter
@NoArgsConstructor
@Entity(name = "GESTOR")
public class GestorEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_GESTOR")
    @SequenceGenerator(name = "SEQ_GESTOR", sequenceName = "SEQ_GESTOR", allocationSize = 1)
    @Column(name = "ID_GESTOR")
    private Integer idGestor;

    @Column(name = "nome")
    private String nome;

    @Column(name = "id_cargo", insertable = false, updatable = false)
    private Integer idCargo;

    @Column(name = "email")
    private String email;

    @Column(name = "senha")
    private String senha;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_cargo")
    private CargoEntity cargoEntity;


}
