package com.dbc.vemvemser.enums;

public enum TipoCargo {
    ADMINISTRADOR(1),
    COLABORADOR(2);

    private Integer cargo;

    TipoCargo(Integer cargo) {
        this.cargo = cargo;
    }
    public Integer getCargo() {
        return cargo;
    }
}
