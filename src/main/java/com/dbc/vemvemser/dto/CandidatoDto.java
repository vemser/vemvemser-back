package com.dbc.vemvemser.dto;

import com.dbc.vemvemser.enums.TipoMarcacao;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class CandidatoDto{

    private Integer idCandidato;

    private String nome;

    private LocalDate dataNascimento;

    private String email;

    private String telefone;

    private String rg;

    private String cpf;

    private String estado;

    private String cidade;

    private TipoMarcacao pcd;

    private FormularioDto formulario;

}
