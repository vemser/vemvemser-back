package com.dbc.vemvemser.entity;

import com.dbc.vemvemser.enums.TipoCargo;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

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

    private String nome;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("idCargo")
    @JoinColumn(name = "id_cargo")
    private CargoEntity cargoEntity;

    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_login", referencedColumnName = "id_login")
    private LoginEntity login;

}
