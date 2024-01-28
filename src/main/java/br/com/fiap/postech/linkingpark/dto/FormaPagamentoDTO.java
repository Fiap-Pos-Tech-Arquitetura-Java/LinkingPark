package br.com.fiap.postech.linkingpark.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record FormaPagamentoDTO(
        String id,

        String nome

) {
}
