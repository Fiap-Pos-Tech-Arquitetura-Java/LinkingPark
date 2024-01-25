package br.com.fiap.postech.linkingpark.controller;

import br.com.fiap.postech.linkingpark.dto.MotoristaDTO;
import br.com.fiap.postech.linkingpark.dto.VeiculoDTO;
import br.com.fiap.postech.linkingpark.service.MotoristaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/motorista")
public class MotoristaContoller {
    private final MotoristaService motoristaService;

    @Autowired
    public MotoristaContoller(MotoristaService motoristaService) {
        this.motoristaService = motoristaService;
    }

    @GetMapping
    public ResponseEntity<List<MotoristaDTO>> findAll() {
        List<MotoristaDTO> motoristas = motoristaService.findAll();
        return ResponseEntity.ok(motoristas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MotoristaDTO> findById(@PathVariable Long id) {
        MotoristaDTO motorista = motoristaService.findById(id);
        return ResponseEntity.ok(motorista);
    }

    @PostMapping
    public ResponseEntity<MotoristaDTO> save(@Valid @RequestBody MotoristaDTO motoristaDTO) {
        MotoristaDTO savedMotorista = motoristaService.save(motoristaDTO);
        return new ResponseEntity<>(savedMotorista, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MotoristaDTO> update(@PathVariable Long id, @Valid @RequestBody MotoristaDTO motoristaDTO) {
        MotoristaDTO updatedMotorista = motoristaService.update(id, motoristaDTO);
        return ResponseEntity.ok(updatedMotorista);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        motoristaService.delete(id);
        return ResponseEntity.noContent().build();
    }
    @PutMapping("/{id}/veiculo")
    public ResponseEntity<Void> update(@PathVariable Long id, @Valid @RequestBody VeiculoDTO veiculoDTO) {
        motoristaService.adicionaVeiculo(id, veiculoDTO);
        return ResponseEntity.noContent().build();
    }
    @DeleteMapping("/{id}/veiculo/{placa}")
    public ResponseEntity<Void> delete(@PathVariable Long id, @PathVariable String placa) {
        motoristaService.deleteVeiculo(id, placa);
        return ResponseEntity.noContent().build();
    }
    @GetMapping("/{id}/veiculo")
    public ResponseEntity<List<VeiculoDTO>> findAllVeiculos(@PathVariable Long id) {
        List<VeiculoDTO> veiculos = motoristaService.findAllVeiculos(id);
        return ResponseEntity.ok(veiculos);
    }
}
