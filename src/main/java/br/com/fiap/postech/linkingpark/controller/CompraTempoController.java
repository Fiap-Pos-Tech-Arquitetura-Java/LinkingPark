package br.com.fiap.postech.linkingpark.controller;

import br.com.fiap.postech.linkingpark.dto.CompraTempoDTO;
import br.com.fiap.postech.linkingpark.service.CompraTempoService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/compratempo")
public class CompraTempoController {

    private final CompraTempoService compraTempoService;
    @Autowired
    public CompraTempoController(CompraTempoService compraTempoService) {
        this.compraTempoService = compraTempoService;
    }

    // para tempo fixo, ou um unico "aviso de o tempo está acabando" de 10 minutos
    // ou um alerta com 15 e um com 5 minutos

    @Operation(summary = "registra uma compra de tempo")
    @PostMapping
    public ResponseEntity<CompraTempoDTO> save(@Valid @RequestBody CompraTempoDTO compraTempoDTO) {
        CompraTempoDTO savedCompraTempoDTO = compraTempoService.save(compraTempoDTO);
        return new ResponseEntity<>(savedCompraTempoDTO, HttpStatus.CREATED);
    }

    @Operation(summary = "finaliza uma compra de tempo")
    @PutMapping("/{id}")
    public ResponseEntity<CompraTempoDTO> update(@PathVariable String id) {
        CompraTempoDTO compraTempoDTO = compraTempoService.finaliza(id);
        return ResponseEntity.ok(compraTempoDTO);
    }

    @Operation(summary = "obtém todos as compras de tempo")
    @GetMapping
    public ResponseEntity<List<CompraTempoDTO>> findAll() {
        List<CompraTempoDTO> comprasTempo = compraTempoService.findAll();
        return ResponseEntity.ok(comprasTempo);
    }
    @Operation(summary = "obtém uma compra de tempo")
    @GetMapping("/{id}")
    public ResponseEntity<CompraTempoDTO> findById(@PathVariable String id) {
        CompraTempoDTO compraTempoDTO = compraTempoService.findById(id);
        return ResponseEntity.ok(compraTempoDTO);
    }

}
