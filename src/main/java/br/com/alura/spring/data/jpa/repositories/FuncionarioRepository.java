package br.com.alura.spring.data.jpa.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import br.com.alura.spring.data.jpa.models.Funcionario;

@Repository
public interface FuncionarioRepository extends CrudRepository<Funcionario, Long> {

    Optional<Funcionario> findFirstByNomeIgnoreCase(String nome);

    Optional<Funcionario> findFirstByCpf(String cpf);

}
