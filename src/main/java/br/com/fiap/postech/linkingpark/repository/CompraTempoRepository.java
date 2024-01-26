package br.com.fiap.postech.linkingpark.repository;

import br.com.fiap.postech.linkingpark.entities.CompraTempo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompraTempoRepository extends JpaRepository<CompraTempo, Long> {

}
