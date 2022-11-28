package com.dbc.vemvemser.dto;



import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginCreateDto {

    @NotNull
    private String email;

    @NotNull
    private String senha;

}
