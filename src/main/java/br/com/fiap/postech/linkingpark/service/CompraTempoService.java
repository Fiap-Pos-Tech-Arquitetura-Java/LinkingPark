package br.com.fiap.postech.linkingpark.service;

import br.com.fiap.postech.linkingpark.controller.exception.ControllerNotFoundException;
import br.com.fiap.postech.linkingpark.dto.CompraTempoDTO;
import br.com.fiap.postech.linkingpark.dto.FormaPagamentoDTO;
import br.com.fiap.postech.linkingpark.dto.MotoristaDTO;
import br.com.fiap.postech.linkingpark.entities.*;
import br.com.fiap.postech.linkingpark.message.sender.QueueSender;
import br.com.fiap.postech.linkingpark.repository.CompraTempoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class CompraTempoService {
    @Autowired
    private FormaPagamentoService formaPagamentoService;
    @Autowired
    private MotoristaService motoristaService;
    @Autowired
    private CompraTempoRepository compraTempoRepository;
    @Autowired
    private QueueSender queueSender;
    @Value("${tarifa}")
    private String tarifa;
    @Value("${compratempo.minimo.emminutos}")
    private Integer tempoMinimoTipoFixo;

    private CompraTempoDTO toDTO(CompraTempo compraTempo) {
        return toDTO(Boolean.TRUE, compraTempo);
    }

    private CompraTempoDTO toDTO(Boolean includeId, CompraTempo compraTempo) {
        FormaPagamentoDTO formaPagamentoDTO = null;
        if (compraTempo.getFormaPagamentoPreferencial() != null) {
            formaPagamentoDTO = new FormaPagamentoDTO(
                    compraTempo.getFormaPagamentoPreferencial().getId(),
                    compraTempo.getFormaPagamentoPreferencial().getNome()
            );
        }
        return new CompraTempoDTO(
                getId(includeId, compraTempo),
                compraTempo.getMotorista().getId(),
                compraTempo.getVeiculo().getPlaca(),
                compraTempo.getStatus(),
                compraTempo.getTipo(),
                compraTempo.getTempoEmMinutos(),
                formaPagamentoDTO,
                compraTempo.getTarifa() != null ? compraTempo.getTarifa().toString() : null,
                compraTempo.getValorTotalPago() != null ? compraTempo.getValorTotalPago().toString() : null
        );
    }

    private Long getId(boolean includeId, CompraTempo compraTempo) {
        if (includeId) {
            return compraTempo.getId();
        }
        return null;
    }

    private CompraTempo toEntity(CompraTempoDTO compraTempoDTO) {
        Motorista motorista = motoristaService.get(compraTempoDTO.idMotorista());

        Optional<Veiculo> optionalVeiculo = motorista.getVeiculos().stream()
                .filter(v -> v.getPlaca().equals(compraTempoDTO.placa()))
                .findFirst();

        if (optionalVeiculo.isEmpty()) {
            throw new ControllerNotFoundException("Placa " + compraTempoDTO.placa()
                    + " não está cadastrada para o Motorista não encontrado com o ID: " + compraTempoDTO.idMotorista());
        }

        FormaPagamento formaPagamento;
        if (compraTempoDTO.formaPagamentoPreferencial() == null || compraTempoDTO.formaPagamentoPreferencial().id() == null ) {
            formaPagamento = motorista.getFormaPagamentoPreferencial();
        } else {
            formaPagamento = formaPagamentoService.get(compraTempoDTO.formaPagamentoPreferencial().id());
        }

        if ("VARIAVEL".equals(compraTempoDTO.tipo())) {
            if (formaPagamentoService.findByNome("PIX").equals(formaPagamento)) {
                throw new ControllerNotFoundException("A opção PIX só está disponível para períodos de estacionamento FIXO.");
            }
        } else if ("FIXO".equals(compraTempoDTO.tipo())) {
            if (compraTempoDTO.tempoEmMinutos() < tempoMinimoTipoFixo) {
                throw new ControllerNotFoundException("Estacionamento FIXO só permite a compra de no mínimo 20 minutos.");
            }
        }

        return new CompraTempo(motorista, optionalVeiculo.get(), "PENDENTE", compraTempoDTO.tipo(),
                compraTempoDTO.tempoEmMinutos(), formaPagamento, LocalDateTime.now());
    }

    public CompraTempoDTO save(CompraTempoDTO compraTempoDTO) {
        CompraTempo compraTempo = toEntity(compraTempoDTO);

        //if ("FIXO".equals(compraTempo.getTipo())) {
            //new RecompraAutomaticaTimerTask(this, compraTempo);
        //} else
        if ("VARIAVEL".equals(compraTempo.getTipo())) {
            compraTempo.setTempoEmMinutos(0);
            compraTempo.adicionaUmaHora();

        }
        compraTempo = compraTempoRepository.save(compraTempo);

        if ("FIXO".equals(compraTempo.getTipo())) {
            queueSender.sendAlertaTempoInicial(compraTempo);
        } else if ("VARIAVEL".equals(compraTempo.getTipo())) {
            queueSender.sendRecompraAutomatica(compraTempo);
        }

        return toDTO(compraTempo);
    }

    public void save(CompraTempo compraTempo) {
        compraTempoRepository.save(compraTempo);
    }

    public CompraTempoDTO finaliza(Long id) {
        try {
            CompraTempo compraTempo = compraTempoRepository.getReferenceById(id);
            compraTempo.setStatus("FINALIZADO");
            compraTempo.setTarifa(Double.valueOf(tarifa));
            compraTempo = compraTempoRepository.save(compraTempo);
            return toDTO(Boolean.FALSE, compraTempo);
        } catch (EntityNotFoundException e) {
            throw new ControllerNotFoundException("Motorista não encontrado com o ID: " + id);
        }
    }

    public CompraTempo get(Long id) {
        return compraTempoRepository.findById(id)
                .orElseThrow(() -> new ControllerNotFoundException("Compra Tempo não encontrado com o ID: " + id));
    }
    public CompraTempoDTO findById(Long id) {
        return toDTO(get(id));
    }
    public List<CompraTempoDTO> findAll() {
        List<CompraTempo> compraTempo = compraTempoRepository.findAll();
        return compraTempo.stream().map(this::toDTO).toList();
    }
}
