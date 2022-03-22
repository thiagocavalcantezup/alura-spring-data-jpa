package br.com.alura.spring.data.jpa.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import br.com.alura.spring.data.jpa.models.UnidadeTrabalho;

@Repository
public interface UnidadeTrabalhoRepository extends CrudRepository<UnidadeTrabalho, Long> {

    Optional<UnidadeTrabalho> findFirstByDescricaoIgnoreCase(String descricao);

    Optional<UnidadeTrabalho> findFirstByEnderecoIgnoreCase(String endereco);

}
