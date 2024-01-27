package br.com.fiap.postech.linkingpark.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDate;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record VeiculoDTO(

        @NotBlank(message = "Placa do veiculo do Motorista é obrigatório")
        String placa,
        @NotBlank(message = "Modelo do veiculo do Motorista é obrigatório")
        String modelo,
        @NotBlank(message = "Cor do veiculo do Motorista é obrigatório")
        String cor
) {
}
