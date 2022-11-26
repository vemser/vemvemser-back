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
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CARGO_SEQUENCIA")
    @SequenceGenerator(name = "CARGO_SEQUENCIA", sequenceName = "SEQ_CARGO", allocationSize = 1)
    @Column(name = "ID_GESTOR")
    private Integer idGestor;

    private String nome;

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY,mappedBy = "gestor")
    private Set<CargoEntity> cargo;

//    @JsonIgnore
//    @OneToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "ID_LOGIN", referencedColumnName = "ID_LOGIN")
//    private LoginEntity login;

}
