package com.dbc.vemvemser.dto;

import com.dbc.vemvemser.enums.TipoTurno;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FormularioCreateDto {

    @Schema(example = "T", description = "T(TRUE) or F(FALSE)")
    private boolean matriculadoBoolean;

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
    private boolean desafiosBoolean;

    @Schema(example = "T", description = "T(TRUE) or F(FALSE)")
    private boolean problemaBoolean;

    @Schema(example = "T", description = "T(TRUE) or F(FALSE)")
    private boolean reconhecimentoBoolean;

    @Schema(example = "T", description = "T(TRUE) or F(FALSE)")
    private boolean altruismoBoolean;

    @Schema(example = "Outro", description = "Motivo pelo qual se interessou pela área de Tecnologia")
    private String resposta;

    @JsonIgnore
    private byte[] curriculo;

    @Schema(example = "T", description = "T(TRUE) or F(FALSE)")
    private boolean lgpdBoolean;

    @Schema(example = "T", description = "T(TRUE) or F(FALSE)")
    private boolean provaBoolean;

    @Schema(example = "Não possuo", description = "Selecione seu nivel de inglês")
    private String ingles;

    @Schema(example = "Não possuo", description = "Selecione seu nivel de espanhol")
    private String espanhol;

    @Schema(example = "TDAH", description = "Digite sua neurodiversidade")
    private String neurodiversidade;

    @Schema(example = "T", description = "T(TRUE) or F(FALSE)")
    private String configurações;

    @Schema(example = "T", description = "T(TRUE) or F(FALSE)")
    private boolean efetivacaoBoolean;

    @Schema(example = "T", description = "T(TRUE) or F(FALSE)")
    private boolean disponibilidadeBoolean;

    @Schema(example = "[ 1 ]" , description = "Escolha sua(s) trilha(s) entre Frontend (1), Backend (2) e QA (3)")
    private List<Integer> trilhas;
}
