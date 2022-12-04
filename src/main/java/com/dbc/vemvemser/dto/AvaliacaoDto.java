package com.dbc.vemvemser.dto;

import com.dbc.vemvemser.enums.TipoMarcacao;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AvaliacaoDto {

    private Integer idAvaliacao;

    private GestorDto avaliador;

    private TipoMarcacao aprovado;

    private InscricaoDto inscricao;
}
