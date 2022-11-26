package com.dbc.vemvemser.enums;

public enum TipoCargo {
    ADMINISTRADOR(0),
    COLABORADOR(1);

    private Integer cargo;

    TipoCargo(Integer cargo) {
        this.cargo = cargo;
    }
}
