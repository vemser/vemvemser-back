package com.dbc.vemvemser.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
public class GestorEmailDto {

    @NotNull
    @Email
    private String email;
}
