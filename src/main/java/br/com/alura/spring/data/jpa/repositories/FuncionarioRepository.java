package br.com.alura.spring.data.jpa.repositories;

import java.time.LocalDate;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import br.com.alura.spring.data.jpa.models.Funcionario;

@Repository
public interface FuncionarioRepository extends PagingAndSortingRepository<Funcionario, Long> {

    Optional<Funcionario> findFirstByNomeIgnoreCase(String nome);

    Optional<Funcionario> findFirstByCpf(String cpf);

    Set<Funcionario> findAllByNomeIgnoreCaseLike(String nome);

    Set<Funcionario> findAllByCargoId(long id);

    Set<Funcionario> findAllByCargoDescricao(String descricao);

    Set<Funcionario> findAllByUnidadesTrabalho_Id(long id);

    Set<Funcionario> findAllByUnidadesTrabalho_Descricao(String descricao);

    Set<Funcionario> findAllByUnidadesTrabalho_Endereco(String endereco);

    /* Funcionario report query */

    // Derived
    Set<Funcionario> findAllByNomeIgnoreCaseLikeAndDataContratacaoLessThanAndSalarioGreaterThan(String nome,
                                                                                                LocalDate dataContratacao,
                                                                                                double salario);

    // JPQL
    @Query("SELECT f FROM Funcionario f WHERE f.nome LIKE :nome AND f.dataContratacao < :dataContratacao AND f.salario > :salario")
    Set<Funcionario> funcionarioReport(String nome, LocalDate dataContratacao, double salario);

    /* ************************ */

    // Native Query
    @Query(value = "SELECT * FROM funcionarios AS f WHERE f.data_contratacao > :dataContratacao", nativeQuery = true)
    Set<Funcionario> findAllByDataContratacaoMaiorQue(LocalDate dataContratacao);

}
