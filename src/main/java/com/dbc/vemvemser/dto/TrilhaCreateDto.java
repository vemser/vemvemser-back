package com.dbc.vemvemser.dto;

import lombok.Data;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Data
@Setter
public class TrilhaCreateDto {

    @NotNull
    private String nome;
}
