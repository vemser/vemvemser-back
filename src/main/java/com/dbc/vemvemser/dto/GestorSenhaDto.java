package com.dbc.vemvemser.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class GestorSenhaDto {

    @NotNull
    @Size(min = 6, max = 20)
    private String senha;

}
