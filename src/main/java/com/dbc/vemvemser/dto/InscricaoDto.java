package com.dbc.vemvemser.dto;


import lombok.Data;

import java.time.LocalDate;

@Data
public class InscricaoDto {

    private Integer idInscricao;

    private Integer idFormulario;

    private Integer idCandidato;

    private LocalDate dataInscricao;

    private String avaliacao;
}
