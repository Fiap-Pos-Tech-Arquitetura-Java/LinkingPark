package br.com.fiap.postech.linkingpark.repository;

import br.com.fiap.postech.linkingpark.documents.Veiculo;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VeiculoRepository extends MongoRepository<Veiculo, String> {

}
