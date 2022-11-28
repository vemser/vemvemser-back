package com.dbc.vemvemser.dto;

import com.dbc.vemvemser.enums.TipoMarcacao;
import com.dbc.vemvemser.enums.TipoTurno;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FormularioCreateDto {

    @Schema(example = "T", description = "T(TRUE) or F(FALSE)")
    private TipoMarcacao matriculado;

    @Schema(example = "Analise e Desenvolvimento de Software", description = "Nome do curso")
    private String curso;

    @Schema(example = "NOITE", description = "Turno que estuda: MANHA, TARDE ou NOITE")
    private TipoTurno turno;

    @Schema(example = "PUC", description = "Nome da Instituição onde cursa")
    private String instituicao;

    @Schema(example = "https://github.com/link-github", description = "Link referente ao seu Github")
    private String github;

    @Schema(example = "https://linkedin.com/", description = "Link referente ao seu Linkedin")
    private String Linkedin;

    @Schema(example = "T", description = "T(TRUE) or F(FALSE)")
    private TipoMarcacao desafios;

    @Schema(example = "T", description = "T(TRUE) or F(FALSE)")
    private TipoMarcacao problema;

    @Schema(example = "T", description = "T(TRUE) or F(FALSE)")
    private TipoMarcacao reconhecimento;


    @Schema(example = "T", description = "T(TRUE) or F(FALSE)")
    private TipoMarcacao altruismo;

    @Schema(example = "Outro", description = "Motivo pelo qual se interessou pela área de Tecnologia")
    private String resposta;

    @JsonIgnore
    private byte[] curriculo;

    @Schema(example = "T", description = "T(TRUE) or F(FALSE)")
    private TipoMarcacao lgpd;

}
