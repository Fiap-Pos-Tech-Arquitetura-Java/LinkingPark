package br.com.fiap.postech.linkingpark.service;

import br.com.fiap.postech.linkingpark.controller.exception.ControllerNotFoundException;
import br.com.fiap.postech.linkingpark.dto.FormaPagamentoDTO;
import br.com.fiap.postech.linkingpark.dto.MotoristaDTO;
import br.com.fiap.postech.linkingpark.dto.VeiculoDTO;
import br.com.fiap.postech.linkingpark.documents.Motorista;
import br.com.fiap.postech.linkingpark.documents.Veiculo;
import br.com.fiap.postech.linkingpark.repository.MotoristaRepository;
import br.com.fiap.postech.linkingpark.repository.VeiculoRepository;
import br.com.fiap.postech.linkingpark.security.JwtService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MotoristaService {
    @Autowired
    private MotoristaRepository motoristaRepository;
    @Autowired
    private VeiculoRepository veiculoRepository;
    @Autowired
    private FormaPagamentoService formaPagamentoService;
    @Autowired
    private JwtService jstService;

    public List<MotoristaDTO> findAll() {
        List<Motorista> motoristas = motoristaRepository.findAll();
        return motoristas.stream().map(this::toDTO).toList();
    }

    public Motorista get(String id) {
        return motoristaRepository.findById(id)
                .orElseThrow(() -> new ControllerNotFoundException("Motorista não encontrado com o ID: " + id));
    }

    public MotoristaDTO findById(String id) {
        return toDTO(get(id));
    }

    public MotoristaDTO save(MotoristaDTO motoristaDTO) {
        if (motoristaRepository.findByEmail(motoristaDTO.email()).isPresent()) {
            throw new ControllerNotFoundException("Já existe um motorista cadastrado com esse email.");
        }
        if (motoristaRepository.findByCpf(motoristaDTO.cpf()).isPresent()) {
            throw new ControllerNotFoundException("Já existe um motorista cadastrado com esse CPF.");
        }
        Motorista motorista = toEntity(motoristaDTO);
        motorista = motoristaRepository.save(motorista);
        return toDTO(motorista);
    }

    public MotoristaDTO update(String id, MotoristaDTO motoristaDTO) {
        Motorista motorista = get(id);

        if (StringUtils.isNotEmpty(motoristaDTO.nome())) {
            motorista.setNome(motoristaDTO.nome());
        }
        if (motoristaDTO.id() != null && !motorista.getId().equals(motoristaDTO.id())) {
            throw new ControllerNotFoundException("Não é possível alterar o id de um motorista.");
        }
        if (motoristaDTO.email() != null && !motorista.getEmail().equals(motoristaDTO.email())) {
            throw new ControllerNotFoundException("Não é possível alterar o email de um motorista.");
        }
        if (motoristaDTO.cpf() != null && !motorista.getCpf().equals(motoristaDTO.cpf())) {
            throw new ControllerNotFoundException("Não é possível alterar o CPF de um motorista.");
        }
        if (StringUtils.isNotEmpty(motoristaDTO.senha())) {
            motorista.setSenha(getEncryptedPassword(motoristaDTO.senha()));
        }
        if (StringUtils.isNotEmpty(motoristaDTO.telefone())) {
            motorista.setTelefone(motoristaDTO.telefone());
        }
        if (motoristaDTO.dataNascimento() != null) {
            motorista.setDataNascimento(motoristaDTO.dataNascimento());
        }
        if (StringUtils.isNotEmpty(motoristaDTO.sexo())) {
            motorista.setSexo(motoristaDTO.sexo());
        }
        if (motoristaDTO.formaPagamentoPreferencial() != null && motoristaDTO.formaPagamentoPreferencial().nome() != null) {
            motorista.setFormaPagamentoPreferencial(formaPagamentoService.findByNome(motoristaDTO.formaPagamentoPreferencial().nome()));
        }

        motorista = motoristaRepository.save(motorista);
        return toDTO(Boolean.FALSE, motorista);
    }

    public void delete(String id) {
        motoristaRepository.deleteById(id);
    }

    private Motorista toEntity(MotoristaDTO motoristaDTO) {
        return new Motorista(
                getEncryptedPassword(motoristaDTO.senha()),
                motoristaDTO.cpf(),
                motoristaDTO.nome(),
                motoristaDTO.email(),
                motoristaDTO.telefone(),
                motoristaDTO.dataNascimento(),
                motoristaDTO.sexo(),
                formaPagamentoService.findByNome(motoristaDTO.formaPagamentoPreferencial().nome())
        );
    }

    private String getEncryptedPassword(String senha) {
        if (senha != null) {
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            return encoder.encode(senha);
        }
        return null;
    }

    private MotoristaDTO toDTO(Motorista motorista) {
        return toDTO(Boolean.TRUE, motorista);
    }

    private MotoristaDTO toDTO(Boolean includeId, Motorista motorista) {
        return new MotoristaDTO(
                getId(includeId, motorista),
                motorista.getSenha(),
                motorista.getCpf(),
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
        return new VeiculoDTO(veiculo.getPlaca(), veiculo.getModelo(), veiculo.getCor());
    }

    private String getId(boolean includeId, Motorista motorista) {
        if (includeId) {
            return motorista.getId();
        }
        return null;
    }

    public void adicionaVeiculo(String id, VeiculoDTO veiculoDTO) {
        Motorista motorista = get(id);

        Veiculo veiculo = new Veiculo(veiculoDTO.placa(), veiculoDTO.modelo(), veiculoDTO.cor());
        veiculoRepository.save(veiculo);

        if (motorista.getVeiculos() == null) {
            motorista.setVeiculos(new ArrayList<>());
        }
        motorista.getVeiculos().add(veiculo);

        motoristaRepository.save(motorista);
    }

    public void deleteVeiculo(String id, String placa) {
        Motorista motorista = get(id);
        if (motorista.getVeiculos() != null) {
            List<Veiculo> veiculos = motorista.getVeiculos()
                    .stream()
                    .filter(v -> !v.getPlaca().equals(placa))
                    .collect(Collectors.toList());
            motorista.setVeiculos(veiculos);
            motoristaRepository.save(motorista);
        }
    }

    public List<VeiculoDTO> findAllVeiculos(String id) {
        Motorista motorista = get(id);
        if (motorista.getVeiculos() == null) {
            return null;
        }
        return motorista.getVeiculos().stream().map(this::toVeiculoDTO).toList();
    }

    public void deleteAll() {
        veiculoRepository.deleteAll();
        motoristaRepository.deleteAll();
    }

    public String login(MotoristaDTO motoristaDTO) throws Exception {
        Optional<Motorista> optionalMotorista = motoristaRepository.findByEmail(motoristaDTO.email());
        if (optionalMotorista.isEmpty()) {
            throw new Exception("Motorista informado não encontrado.");
        }
        Motorista u = optionalMotorista.get();
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        if (!encoder.matches(motoristaDTO.senha(), u.getSenha())) {
            throw new Exception("Senha do Motorista informado não confere.");
        }
        return jstService.generateToken(motoristaDTO.email());
    }
}