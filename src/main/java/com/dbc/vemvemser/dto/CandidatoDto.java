package com.dbc.vemvemser.dto;

import com.dbc.vemvemser.enums.TipoGenero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class CandidatoDto{

    private Integer idCandidato;

    private String nome;

    private TipoGenero genero;

    private String email;

    private String telefone;

    private String rg;

    private String cpf;

    private String estado;

    private String cidade;

    private FormularioDto formulario;
}
