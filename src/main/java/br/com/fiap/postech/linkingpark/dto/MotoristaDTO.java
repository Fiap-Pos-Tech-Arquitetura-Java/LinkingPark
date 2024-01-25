package br.com.fiap.postech.linkingpark.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDate;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record MotoristaDTO(
        Long id,

        @NotBlank(message = "Nome do Motorista é obrigatório")
        String nome,

        @NotBlank(message = "Email é obrigatório")
        String email,

        @NotBlank(message = "Telefone é obrigatório")
        String telefone,

        @NotBlank(message = "Data de Nascimento é obrigatório")
        LocalDate dataNascimento,

        @NotBlank(message = "Sexo é obrigatório")
        String sexo,

        FormaPagamentoDTO formaPagamentoPreferencial

        //@NotBlank(message = "Endereço é obrigatório")
        //Endereco endereco
) {
}
