package br.com.fiap.postech.standardparking.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record MotoristaDTO(
        Long id,

        @NotBlank(message = "Nome do Motorista é obrigatorio")
        String nome,

        @NotBlank(message = "Email é obrigatorio")
        String email,

        @NotBlank(message = "Telefone é obrigatorio")
        String telefone
) {
}
