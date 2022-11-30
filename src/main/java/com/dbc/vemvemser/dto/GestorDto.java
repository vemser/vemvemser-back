package com.dbc.vemvemser.dto;


import com.dbc.vemvemser.entity.CargoEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.util.Set;

@Data
public class GestorDto {

    private Integer idGestor;

    private String nome;

    private String email;

    private CargoDto cargoDto;
}
