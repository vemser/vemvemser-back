package com.dbc.vemvemser.dto;

import com.dbc.vemvemser.entity.CargoEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class TokenDto {

    private String token;
    private Integer idGestor;
    private CargoDto cargoDto;
}
