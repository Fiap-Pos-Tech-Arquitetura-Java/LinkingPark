package br.com.fiap.postech.standardparking.repository;

import br.com.fiap.postech.standardparking.entities.Motorista;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MotoristaRepository extends JpaRepository<Motorista, Long> {

}
