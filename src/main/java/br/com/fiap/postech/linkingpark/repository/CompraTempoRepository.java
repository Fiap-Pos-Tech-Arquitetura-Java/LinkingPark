package br.com.fiap.postech.linkingpark.repository;

import br.com.fiap.postech.linkingpark.documents.CompraTempo;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompraTempoRepository extends MongoRepository<CompraTempo, String> {

}
