package br.com.fiap.postech.linkingpark.controller;

import br.com.fiap.postech.linkingpark.dto.FormaPagamentoDTO;
import br.com.fiap.postech.linkingpark.service.FormaPagamentoService;
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
    @GetMapping
    public ResponseEntity<List<FormaPagamentoDTO>> findAll() {
        List<FormaPagamentoDTO> formaPagamentos = formaPagamentoService.findAll();
        return ResponseEntity.ok(formaPagamentos);
    }
}
