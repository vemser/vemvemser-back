package com.dbc.vemvemser.dto;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GestorEmailNomeDto {

    @NotNull
    private String nome;
    
    @NotNull
    private String email;
}
