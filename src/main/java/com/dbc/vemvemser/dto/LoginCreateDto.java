package com.dbc.vemvemser.dto;



import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginCreateDto {

    @NotNull
    @Schema(description = "Email para login", example = "admin@dbccompany.com.br")
    private String email;

    @NotNull
    @Schema(description = "Senha para login", example = "123")
    private String senha;

}
