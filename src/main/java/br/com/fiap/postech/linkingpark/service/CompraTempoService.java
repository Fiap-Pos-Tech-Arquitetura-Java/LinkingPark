package br.com.fiap.postech.linkingpark.service;

import br.com.fiap.postech.linkingpark.controller.exception.ControllerNotFoundException;
import br.com.fiap.postech.linkingpark.dto.CompraTempoDTO;
import br.com.fiap.postech.linkingpark.dto.FormaPagamentoDTO;
import br.com.fiap.postech.linkingpark.entities.*;
import br.com.fiap.postech.linkingpark.repository.CompraTempoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class CompraTempoService {
    @Value("${recarga.intervalo.emminutos}")
    private Long periodoRecarga;
    @Value("${alerta.intervalo.emminutos}")
    private Long periodoAlerta;
    @Value("${alert.tempo.antes.fim}")
    private Long delayAlerta;
    @Autowired
    private MotoristaService motoristaService;
    @Autowired
    private NotificacaoService notificacaoService;
    @Autowired
    private CompraTempoRepository compraTempoRepository;

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
                formaPagamentoDTO
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

        FormaPagamento formaPagamento = null;
        if (compraTempoDTO.formaPagamentoPreferencial() == null || compraTempoDTO.formaPagamentoPreferencial().id() == null ) {
            formaPagamento = motorista.getFormaPagamentoPreferencial();
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

        new AlertaTempoRestanteTimerTask(this, notificacaoService, compraTempo.getId(), delayAlerta, periodoAlerta);
        if ("VARIAVEL".equals(compraTempo.getTipo())) {
            new RecompraAutomaticaTimerTask(this, notificacaoService, compraTempo.getId(), periodoRecarga);
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
}
