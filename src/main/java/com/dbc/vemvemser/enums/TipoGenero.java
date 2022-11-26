package com.dbc.vemvemser.enums;

public enum TipoGenero {
    Feminino(0),
    Masculino(1),
    Neutro(2);

    private Integer genero;


    TipoGenero(Integer genero){
        this.genero = genero;
    }
}
