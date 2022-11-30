package com.dbc.vemvemser.dto;

import com.dbc.vemvemser.enums.TipoCargo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;


@Data
public class GestorEmailNomeCargoDto {

    @Schema(description = "(opcional) nome do gestor", example = "")
    private String nome;
    @Schema(description = "(opcional) email do gestor", example = "")
    private String email;
    @Schema(description = "cargo do gestor", example = "ADMINISTRADOR")
    private TipoCargo cargo;
}
