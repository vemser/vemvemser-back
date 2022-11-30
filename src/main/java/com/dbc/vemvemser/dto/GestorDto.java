package com.dbc.vemvemser.dto;


import com.dbc.vemvemser.enums.TipoMarcacao;
import lombok.Data;


@Data
public class GestorDto {

    private Integer idGestor;

    private String nome;

    private String email;

    private CargoDto cargoDto;

    private TipoMarcacao ativo;
}
