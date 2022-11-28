package com.dbc.vemvemser.dto;

import com.dbc.vemvemser.enums.TipoMarcacao;
import com.dbc.vemvemser.enums.TipoTurno;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FormularioDto {


    private Integer idFormulario;


    private TipoMarcacao matriculado;


    private String curso;


    private TipoTurno turno;


    private String instituicao;


    private String github;

    private String linkedin;

    private TipoMarcacao desafios;


    private TipoMarcacao problema;


    private TipoMarcacao reconhecimento;


    private TipoMarcacao altruismo;


    private String resposta;


    private byte[] curriculo;

    private TipoMarcacao lgpd;
}