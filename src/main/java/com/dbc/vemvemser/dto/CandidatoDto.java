package com.dbc.vemvemser.dto;

import com.dbc.vemvemser.enums.TipoMarcacao;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;


@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class CandidatoDto {

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
