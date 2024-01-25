package br.com.fiap.postech.linkingpark.service;

import br.com.fiap.postech.linkingpark.controller.exception.ControllerNotFoundException;
import br.com.fiap.postech.linkingpark.dto.FormaPagamentoDTO;
import br.com.fiap.postech.linkingpark.dto.MotoristaDTO;
import br.com.fiap.postech.linkingpark.dto.VeiculoDTO;
import br.com.fiap.postech.linkingpark.entities.FormaPagamento;
import br.com.fiap.postech.linkingpark.entities.Motorista;
import br.com.fiap.postech.linkingpark.entities.Veiculo;
import br.com.fiap.postech.linkingpark.repository.FormaPagamentoRepository;
import br.com.fiap.postech.linkingpark.repository.MotoristaRepository;
import br.com.fiap.postech.linkingpark.repository.VeiculoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MotoristaService {
    @Autowired
    private MotoristaRepository motoristaRepository;
    @Autowired
    private VeiculoRepository veiculoRepository;
    @Autowired
    private FormaPagamentoRepository formaPagamentoRepository;

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
                motoristaDTO.telefone(),
                motoristaDTO.dataNascimento(),
                motoristaDTO.sexo(),
                formaPagamentoRepository.getReferenceById(motoristaDTO.formaPagamentoPreferencial().id())
        );
    }

    private Veiculo toVeiculoEntity(VeiculoDTO veiculoDTO) {
        return new Veiculo(veiculoDTO.placa());
    }

    private MotoristaDTO toDTO(Motorista motorista) {
        return toDTO(Boolean.TRUE, motorista);
    }

    private MotoristaDTO toDTO(Boolean includeId, Motorista motorista) {
        return new MotoristaDTO(
                getId(includeId, motorista),
                motorista.getNome(),
                motorista.getEmail(),
                motorista.getTelefone(),
                motorista.getDataNascimento(),
                motorista.getSexo(),
                new FormaPagamentoDTO(
                        motorista.getFormaPagamentoPreferencial().getId(),
                        motorista.getFormaPagamentoPreferencial().getNome()
                )
        );
    }

    private VeiculoDTO toVeiculoDTO(Veiculo veiculo) {
        return new VeiculoDTO(veiculo.getPlaca());
    }

    private Long getId(boolean includeId, Motorista motorista) {
        if (includeId) {
            return motorista.getId();
        }
        return null;
    }

    public void adicionaVeiculo(Long id, VeiculoDTO veiculoDTO) {
        try {
            Motorista motorista = motoristaRepository.getReferenceById(id);

            Veiculo veiculo = new Veiculo(veiculoDTO.placa());
            veiculoRepository.save(veiculo);

            motorista.getVeiculos().add(veiculo);

            motoristaRepository.save(motorista);
        } catch (EntityNotFoundException e) {
            throw new ControllerNotFoundException("Motorista não encontrado com o ID: " + id);
        }
    }

    public void deleteVeiculo(Long id, String placa) {
        try {
            Motorista motorista = motoristaRepository.getReferenceById(id);
            if (motorista.getVeiculos() != null) {
                List<Veiculo> veiculos = motorista.getVeiculos()
                        .stream()
                        .filter(v -> !v.getPlaca().equals(placa))
                        .collect(Collectors.toList());
                motorista.setVeiculos(veiculos);
                motoristaRepository.save(motorista);
            }
        } catch (EntityNotFoundException e) {
            throw new ControllerNotFoundException("Motorista não encontrado com o ID: " + id);
        }
    }

    public List<VeiculoDTO> findAllVeiculos(Long id) {
        try {
            Motorista motorista = motoristaRepository.getReferenceById(id);
            if (motorista.getVeiculos() == null) {
                return null;
            }
            return motorista.getVeiculos().stream().map(this::toVeiculoDTO).toList();
        } catch (EntityNotFoundException e) {
            throw new ControllerNotFoundException("Motorista não encontrado com o ID: " + id);
        }
    }
}