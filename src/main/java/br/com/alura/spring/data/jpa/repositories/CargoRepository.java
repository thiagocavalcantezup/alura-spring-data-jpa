package br.com.alura.spring.data.jpa.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import br.com.alura.spring.data.jpa.models.Cargo;

@Repository
public interface CargoRepository extends CrudRepository<Cargo, Long> {

    Optional<Cargo> findFirstByDescricaoIgnoreCase(String descricao);

}
