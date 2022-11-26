package com.dbc.vemvemser.entity;
import com.dbc.vemvemser.enums.TipoGenero;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity(name = "CANDIDATO")
public class CandidatoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="SEQ_CANDIDATO")
    @SequenceGenerator(name = "SEQ_CANDIDATO", sequenceName = "seq_candidato",allocationSize = 1)
    @Column(name = "id_candidato")
    private Integer idCandidato;

    @Column(name = "nome")
    private String nome;

    @Column(name = "genero")
    private String genero;

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

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="ID_INSCRICAO", referencedColumnName = "ID_INSCRICAO")
    private InscricaoEntity inscricaoCandidato;


}
