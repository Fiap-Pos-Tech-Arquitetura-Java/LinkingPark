package br.com.fiap.postech.linkingpark.controller;

import br.com.fiap.postech.linkingpark.dto.MotoristaDTO;
import br.com.fiap.postech.linkingpark.dto.VeiculoDTO;
import br.com.fiap.postech.linkingpark.service.MotoristaService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/motorista")
public class MotoristaController {
    private final MotoristaService motoristaService;

    @Autowired
    public MotoristaController(MotoristaService motoristaService) {
        this.motoristaService = motoristaService;
    }

    @Operation(summary = "obtém todos os motoristas")
    @GetMapping
    public ResponseEntity<List<MotoristaDTO>> findAll() {
        List<MotoristaDTO> motoristas = motoristaService.findAll();
        return ResponseEntity.ok(motoristas);
    }
    @Operation(summary = "obtém um motorista")
    @GetMapping("/{id}")
    public ResponseEntity<MotoristaDTO> findById(@PathVariable String id) {
        MotoristaDTO motorista = motoristaService.findById(id);
        return ResponseEntity.ok(motorista);
    }
    @Operation(summary = "insere um motorista")
    @PostMapping
    public ResponseEntity<MotoristaDTO> save(@Valid @RequestBody MotoristaDTO motoristaDTO) {
        MotoristaDTO savedMotorista = motoristaService.save(motoristaDTO);
        return new ResponseEntity<>(savedMotorista, HttpStatus.CREATED);
    }

    @Operation(summary = "altera os dados de um motorista")
    @PutMapping("/{id}")
    public ResponseEntity<MotoristaDTO> update(@PathVariable String id, @Valid @RequestBody MotoristaDTO motoristaDTO) {
        MotoristaDTO updatedMotorista = motoristaService.update(id, motoristaDTO);
        return ResponseEntity.ok(updatedMotorista);
    }

    @Operation(summary = "remove um motorista")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        motoristaService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "adiciona um veículo a um motorista")
    @PutMapping("/{id}/veiculo")
    public ResponseEntity<VeiculoDTO> update(@PathVariable String id, @Valid @RequestBody VeiculoDTO veiculoDTO) {
        motoristaService.adicionaVeiculo(id, veiculoDTO);
        return ResponseEntity.ok(veiculoDTO);
    }

    @Operation(summary = "remove um veículo de um motorista")
    @DeleteMapping("/{id}/veiculo/{placa}")
    public ResponseEntity<Void> delete(@PathVariable String id, @PathVariable String placa) {
        motoristaService.deleteVeiculo(id, placa);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "lista os veículos de um motorista")
    @GetMapping("/{id}/veiculo")
    public ResponseEntity<List<VeiculoDTO>> findAllVeiculos(@PathVariable String id) {
        List<VeiculoDTO> veiculos = motoristaService.findAllVeiculos(id);
        return ResponseEntity.ok(veiculos);
    }
}
