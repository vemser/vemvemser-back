package com.dbc.vemvemser.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginCreateDto {

    @NotNull
    private String email;

    @NotNull
    private String senha;

    @NotNull
    @Size(min = 3, max= 255, message = "O nome deve ter de 3 a 255 caracteres")
    @Schema(description = "Nome do gestor", example = "Arnaldo da Silva")
    private String nome;

    private Integer tipoCargo;
}
