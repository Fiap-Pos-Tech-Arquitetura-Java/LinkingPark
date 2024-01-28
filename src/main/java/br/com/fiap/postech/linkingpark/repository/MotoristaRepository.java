package br.com.fiap.postech.linkingpark.repository;

import br.com.fiap.postech.linkingpark.documents.Motorista;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MotoristaRepository extends MongoRepository<Motorista, String> {

}
