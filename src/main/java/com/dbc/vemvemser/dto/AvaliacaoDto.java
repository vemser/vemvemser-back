package com.dbc.vemvemser.dto;

import com.dbc.vemvemser.entity.GestorEntity;
import com.dbc.vemvemser.entity.InscricaoEntity;
import com.dbc.vemvemser.enums.TipoMarcacao;
import com.fasterxml.jackson.annotation.JsonIgnore;
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

    @JsonIgnore
    private GestorEntity avaliador;

    private TipoMarcacao aprovado;

    private InscricaoEntity inscricao;
}
