package br.com.fiap.postech.linkingpark.repository;

import br.com.fiap.postech.linkingpark.entities.FormaPagamento;
import br.com.fiap.postech.linkingpark.entities.Motorista;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FormaPagamentoRepository extends JpaRepository<FormaPagamento, Long> {
    Optional<FormaPagamento> findByNome(String nome);

}
