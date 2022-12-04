package com.dbc.vemvemser.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.hibernate.validator.constraints.br.CPF;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Data
public class CandidatoCreateDto {


    @NotNull
    @Size(min = 3, max = 255, message = "O nome deve ter de 3 a 255 caracteres")
    @Schema(description = "Nome do candidato", example = "José da Silva da Silva")
    @Pattern(regexp = "^[A-Za-záàâãéèêíïóôõöúçñÁÀÂÃÉÈÍÏÓÔÕÖÚÇÑ ]{0,}", message = "Não permitido números e caracteres especiais.")
    private String nome;

    @NotNull
    @Schema(description = "data de nascimento do candidato", example = "20/10/2000")
    private LocalDate dataNascimento;

    @Email
    @Schema(description = "email do candidato", example = "Jose@dbccompany.com.br")
    private String email;

    @NotNull
    @Size(min = 8, max = 255, message = "O nome deve ter de 8 a 30 caracteres")
    @Schema(description = "Telefone do candidato", example = "32251515")
    private String telefone;

    @NotNull
    @Size(min = 3, max = 255, message = "O nome deve ter de 8 a 30 caracteres")
    @Schema(description = "Registro Geral (RG)", example = "77.777.777-7")
    private String rg;

    @CPF
    @Schema(description = "Cadastro de pessoa fisica (CPF)", example = "777.777.777-77")
    private String cpf;

    @NotNull
    @Schema(description = "Estado em que habita o candidato", example = "RS")
    private String estado;

    @NotNull
    @Size(min = 3, max = 255, message = "O nome deve ter de 3 a 30 caracteres")
    @Schema(description = "Cidade em que habita o candidato", example = "Porto Alegre")
    private String cidade;

    @Schema(example = "T", description = "T(TRUE) or F(FALSE)")
    private boolean pcdBoolean;

    @NotNull
    private Integer idFormulario;
}
