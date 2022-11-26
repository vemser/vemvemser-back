package com.dbc.vemvemser.enums;

import lombok.Getter;

@Getter
public enum TipoMarcacao {
    TRUE("T"),
    FALSE("F");

    private final String descricao;

   TipoMarcacao(String descricao) {
        this.descricao = descricao;
    }


}
