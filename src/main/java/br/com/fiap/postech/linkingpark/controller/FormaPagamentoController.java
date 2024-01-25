package br.com.fiap.postech.linkingpark.controller;

import br.com.fiap.postech.linkingpark.dto.FormaPagamentoDTO;
import br.com.fiap.postech.linkingpark.service.FormaPagamentoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@RestController
@RequestMapping("/formapagamento")
public class FormaPagamentoController {
    private final FormaPagamentoService formaPagamentoService;
    @Autowired
    public FormaPagamentoController(FormaPagamentoService formaPagamentoService) {
        this.formaPagamentoService = formaPagamentoService;
    }
    @Operation(summary = "lista as formas de pagamentos validas para informar no cadastro de motorista")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Sucesso - aqui estão as formas de pagamento suportadas",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = FormaPagamentoDTO.class)) }),
    })
    @GetMapping
    public ResponseEntity<List<FormaPagamentoDTO>> findAll() {
        List<FormaPagamentoDTO> formaPagamentos = formaPagamentoService.findAll();
        return ResponseEntity.ok(formaPagamentos);
    }
}
