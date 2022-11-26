package com.dbc.vemvemser.dto;

import com.dbc.vemvemser.enums.TipoGenero;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GestorCreateDto {

    @NotNull
    @Size(min = 3, max= 255, message = "O nome deve ter de 3 a 255 caracteres")
    @Schema(description = "Nome do gestor", example = "Márcia da Silva Santos")
    private String nome;

}
