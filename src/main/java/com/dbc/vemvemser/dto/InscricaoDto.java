package com.dbc.vemvemser.dto;


import com.dbc.vemvemser.enums.TipoMarcacao;
import lombok.Data;

import java.time.LocalDate;

@Data
public class InscricaoDto {

    private Integer idInscricao;

    private CandidatoDto candidato;

    private LocalDate dataInscricao;

    private TipoMarcacao avaliado;
}
