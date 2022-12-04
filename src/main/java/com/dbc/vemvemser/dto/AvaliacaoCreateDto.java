package com.dbc.vemvemser.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AvaliacaoCreateDto {

    private boolean aprovadoBoolean;

    @NotNull
    private Integer idInscricao;
}
