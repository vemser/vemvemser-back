package com.dbc.vemvemser.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity(name = "CANDIDATO")
public class CandidatoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="SEQ_CANDIDATO")
    @SequenceGenerator(name = "SEQ_CANDIDATO", sequenceName = "seq_candidato",allocationSize = 1)
    @Column(name = "id_canditado")
    private Integer id;

    @Column(name = "nome")
    private String nome;

    @Column(name = "sexo")
    private char sexo;

    @Column(name = "email")
    private String email;

    @Column(name = "telefone")
    private String telefone;

    @Column(name = "rg")
    private String rg;

    @Column(name = "cpf")
    private String cpf;

    @Column(name = "estado")
    private String estado;

    @Column(name = "cidade")
    private String cidade;


}
