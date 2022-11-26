package com.dbc.vemvemser.dto;


import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginCreateDto {

    @NotNull
    private String email;

    @NotNull
    private String senha;
}
