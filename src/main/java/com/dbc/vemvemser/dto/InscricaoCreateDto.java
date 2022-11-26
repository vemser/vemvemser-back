package com.dbc.vemvemser.dto;

import com.dbc.vemvemser.enums.TipoMarcacao;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
public class InscricaoCreateDto {


    private Integer idFormulario;

    private Integer idCandidato;

    @NotNull
    private LocalDate dataInscricao;

    @NotNull
    private TipoMarcacao avaliacao;
}
