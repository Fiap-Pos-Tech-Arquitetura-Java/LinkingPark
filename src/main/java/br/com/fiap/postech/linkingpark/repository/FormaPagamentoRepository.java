package br.com.fiap.postech.linkingpark.repository;

import br.com.fiap.postech.linkingpark.documents.FormaPagamento;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FormaPagamentoRepository extends MongoRepository<FormaPagamento, String> {
    Optional<FormaPagamento> findByNome(String nome);



}
