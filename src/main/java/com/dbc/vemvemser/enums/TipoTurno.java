package com.dbc.vemvemser.enums;

public enum TipoTurno {
    MANHA(1),
    TARDE(2),
    NOITE(3);

    private final Integer descricao;

    TipoTurno(Integer turno) {
        this.descricao = turno;
    }
        public Integer getDescricao() {
        return descricao;
    }
}
