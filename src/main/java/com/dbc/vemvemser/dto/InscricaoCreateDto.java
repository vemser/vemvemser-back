package com.dbc.vemvemser.dto;

import com.dbc.vemvemser.enums.TipoMarcacao;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
public class InscricaoCreateDto {

    @NotNull
    private Integer idFormulario;

    @NotNull
    private Integer idCandidato;

    @NotNull
    private TipoMarcacao avaliacao;
}
