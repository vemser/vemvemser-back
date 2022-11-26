package com.dbc.vemvemser.entity;

import com.dbc.vemvemser.enums.TipoCargo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@Entity(name = "CARGO")
public class CargoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CARGO_SEQUENCIA")
    @SequenceGenerator(name = "CARGO_SEQUENCIA", sequenceName = "SEQ_CARGO", allocationSize = 1)
    @Column(name = "ID_CARGO")
    private Integer idCargo;

    private TipoCargo tipoCargo;

    private String descricao;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "GESTOR_CARGO",
            joinColumns = @JoinColumn(name = "ID_CARGO"),
            inverseJoinColumns = @JoinColumn(name = "ID_GESTOR")
    )
    private Set<GestorEntity> gestor;
}
