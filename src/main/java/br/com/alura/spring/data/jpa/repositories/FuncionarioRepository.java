package br.com.alura.spring.data.jpa.repositories;

import java.util.Optional;
import java.util.Set;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import br.com.alura.spring.data.jpa.models.Funcionario;

@Repository
public interface FuncionarioRepository extends CrudRepository<Funcionario, Long> {

    Optional<Funcionario> findFirstByNomeIgnoreCase(String nome);

    Optional<Funcionario> findFirstByCpf(String cpf);

    Set<Funcionario> findAllByCargoId(long id);

    Set<Funcionario> findAllByCargoDescricao(String descricao);

}
