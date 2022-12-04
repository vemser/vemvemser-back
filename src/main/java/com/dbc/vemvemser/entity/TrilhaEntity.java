package com.dbc.vemvemser.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "TRILHA")
public class TrilhaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_TRILHA")
    @SequenceGenerator(name = "SEQ_TRILHA", sequenceName = "SEQ_TRILHA", allocationSize = 1)
    @Column(name = "id_trilha")
    private Integer idTrilha;

    @Column(name = "nome")
    private String nome;


    @JsonIgnore
    @ManyToMany(mappedBy = "trilhaEntitySet", fetch = FetchType.LAZY)
    private Set<FormularioEntity> formularios;

}
