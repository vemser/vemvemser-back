package com.dbc.vemvemser.dto;

import com.dbc.vemvemser.entity.GestorEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CargoDto {

    private Integer idCargo;

    private String nome;

    @JsonIgnore
    private Set<GestorEntity> pessoa;
}
