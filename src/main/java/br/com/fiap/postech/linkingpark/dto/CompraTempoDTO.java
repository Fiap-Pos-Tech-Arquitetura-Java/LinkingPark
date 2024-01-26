package br.com.fiap.postech.linkingpark.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record CompraTempoDTO(


        Long id,

        @NotBlank(message = "id do motorista")
        @Schema(example = "1", required = true)
        Long idMotorista,

        @NotBlank(message = "Placa do veiculo que ficará estacionado")
        @Schema(example = "ABC1234", required = true)
        String placa,
        @Schema(example = "PENDENTE", required = false)
        String status,

        @NotBlank(message = "se a compra é por tempo fixo ou variável")
        @Schema(example = "FIXO", required = true, allowableValues = { "FIXO", "VARIAVEL" } )
        String tipo,

        @Schema(example = "20", required = false )
        Integer tempoEmMinutos,

        @Schema(example = "PIX", required = false, allowableValues = { "PIX", "Cartão de débito", "Cartão de crédito" } )
        FormaPagamentoDTO formaPagamentoPreferencial
) {
}
