package com.dbc.vemvemser.entity;
import com.dbc.vemvemser.enums.TipoMarcacao;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@Entity(name = "CANDIDATO")
public class CandidatoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="SEQ_CANDIDATO")
    @SequenceGenerator(name = "SEQ_CANDIDATO", sequenceName = "SEQ_CANDIDATO",allocationSize = 1)
    @Column(name = "id_candidato")
    private Integer idCandidato;

    @Column(name = "nome")
    private String nome;

    @Column(name = "data_nascimento")
    private LocalDate dataNascimento;

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

    @Column(name = "pcd")
    @Enumerated(EnumType.STRING)
    private TipoMarcacao pcd;

    @OneToOne(mappedBy ="candidato" ,fetch = FetchType.LAZY)
    private InscricaoEntity inscricao;

    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="ID_FORMULARIO", referencedColumnName = "ID_FORMULARIO")
    private FormularioEntity formulario;

}
