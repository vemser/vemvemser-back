package com.dbc.vemvemser.entity;


import com.dbc.vemvemser.enums.TipoMarcacao;
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

    @Column(name = "FRONTEND")
    @Enumerated(EnumType.STRING)
    private TipoMarcacao front;

    @Column(name = "BACKEND")
    @Enumerated(EnumType.STRING)
    private TipoMarcacao backend;

    @Column(name = "QA")
    @Enumerated(EnumType.STRING)
    private TipoMarcacao qualityAssurance;

    @JsonIgnore
    @OneToMany(mappedBy = "trilha",fetch = FetchType.LAZY)
    private Set<FormularioEntity> form;

}
