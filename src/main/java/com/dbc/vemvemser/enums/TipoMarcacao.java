package com.dbc.vemvemser.enums;

public enum TipoMarcacao {
    TRUE("T"),
    FALSE("F");

    private final String descricao;

   TipoMarcacao(String descricao) {
        this.descricao = descricao;
    }


    @Override
    public String toString() {
        return descricao;
    }
}
