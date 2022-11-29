package com.dbc.vemvemser.dto;

import com.dbc.vemvemser.entity.GestorEntity;
import com.dbc.vemvemser.entity.InscricaoEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
