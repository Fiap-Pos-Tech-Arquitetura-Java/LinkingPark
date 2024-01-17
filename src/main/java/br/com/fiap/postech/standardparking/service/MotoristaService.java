package br.com.fiap.postech.standardparking.service;

import br.com.fiap.postech.standardparking.controller.exception.ControllerNotFoundException;
import br.com.fiap.postech.standardparking.dto.MotoristaDTO;
import br.com.fiap.postech.standardparking.entities.Motorista;
import br.com.fiap.postech.standardparking.repository.MotoristaRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MotoristaService {
    @Autowired
    private MotoristaRepository motoristaRepository;

    public List<MotoristaDTO> findAll() {
        List<Motorista> motoristas = motoristaRepository.findAll();
        return motoristas.stream().map(this::toDTO).toList();
    }

    public MotoristaDTO findById(Long id) {
        Motorista Motorista = motoristaRepository.findById(id)
                .orElseThrow(() -> new ControllerNotFoundException("Motorista não encontrado com o ID: " + id));
        return toDTO(Motorista);
    }

    public MotoristaDTO save(MotoristaDTO motoristaDTO) {
        Motorista motorista = toEntity(motoristaDTO);
        motorista = motoristaRepository.save(motorista);
        return toDTO(motorista);
    }

    public MotoristaDTO update(Long id, MotoristaDTO motoristaDTO) {
        try {
            Motorista motorista = motoristaRepository.getReferenceById(id);

            motorista.setNome(motoristaDTO.nome());
            motorista.setEmail(motoristaDTO.email());
            motorista.setTelefone(motoristaDTO.telefone());

            motorista = motoristaRepository.save(motorista);
            return toDTO(Boolean.FALSE, motorista);
        } catch (EntityNotFoundException e) {
            throw new ControllerNotFoundException("Motorista não encontrado com o ID: " + id);
        }
    }

    public void delete(Long id) {
        motoristaRepository.deleteById(id);
    }

    private Motorista toEntity(MotoristaDTO motoristaDTO) {
        return new Motorista(motoristaDTO.nome(),
                motoristaDTO.email(),
                motoristaDTO.telefone()
        );
    }

    private MotoristaDTO toDTO(Motorista motorista) {
        return toDTO(Boolean.TRUE, motorista);
    }

    private MotoristaDTO toDTO(Boolean includeId, Motorista motorista) {
        return new MotoristaDTO(
                getId(includeId, motorista),
                motorista.getNome(),
                motorista.getEmail(),
                motorista.getTelefone()
        );
    }

    private Long getId(boolean includeId, Motorista motorista) {
        if (includeId) {
            return motorista.getId();
        }
        return null;
    }
}