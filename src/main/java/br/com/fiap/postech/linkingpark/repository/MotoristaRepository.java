package br.com.fiap.postech.linkingpark.repository;

import br.com.fiap.postech.linkingpark.documents.Motorista;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MotoristaRepository extends MongoRepository<Motorista, String> {
    Optional<Motorista> findByEmail(String email);
    Optional<Motorista> findByCpf(Long cpf);
}
