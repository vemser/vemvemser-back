package com.dbc.vemvemser.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class InscricaoCreateDto {

    @NotNull
    private Integer idCandidato;

}
