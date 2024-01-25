package br.com.fiap.postech.linkingpark.service;

import br.com.fiap.postech.linkingpark.dto.FormaPagamentoDTO;
import br.com.fiap.postech.linkingpark.dto.MotoristaDTO;
import br.com.fiap.postech.linkingpark.entities.FormaPagamento;
import br.com.fiap.postech.linkingpark.entities.Motorista;
import br.com.fiap.postech.linkingpark.repository.FormaPagamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FormaPagamentoService {

    @Autowired
    private FormaPagamentoRepository formaPagamentoRepository;

    private FormaPagamentoDTO toDTO(FormaPagamento motorista) {
        return toDTO(Boolean.TRUE, motorista);
    }

    private FormaPagamentoDTO toDTO(Boolean includeId, FormaPagamento formaPagamento) {
        return new FormaPagamentoDTO(
                getId(includeId, formaPagamento),
                formaPagamento.getNome()
        );
    }

    private Long getId(boolean includeId, FormaPagamento formaPagamento) {
        if (includeId) {
            return formaPagamento.getId();
        }
        return null;
    }

    private FormaPagamento toEntity(FormaPagamentoDTO formaPagamentoDTO) {
        return new FormaPagamento(formaPagamentoDTO.nome());
    }

    public List<FormaPagamentoDTO> findAll() {
        List<FormaPagamento> motoristas = formaPagamentoRepository.findAll();
        return motoristas.stream().map(this::toDTO).toList();
    }
    public FormaPagamentoDTO save(FormaPagamentoDTO formaPagamentoDTO) {
        FormaPagamento formaPagamento = toEntity(formaPagamentoDTO);
        formaPagamento = formaPagamentoRepository.save(formaPagamento);
        return toDTO(formaPagamento);
    }
}
