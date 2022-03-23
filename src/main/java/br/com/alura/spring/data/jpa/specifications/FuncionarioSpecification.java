package br.com.alura.spring.data.jpa.specifications;

import java.time.LocalDate;

import org.springframework.data.jpa.domain.Specification;

import br.com.alura.spring.data.jpa.models.Funcionario;

public class FuncionarioSpecification {

    public static Specification<Funcionario> nome(String nome) {
        return (root, criteriaQuery, criteriaBuilder) -> {
            if (nome == null) {
                return criteriaBuilder.conjunction();
            }

            return criteriaBuilder.like(root.get("nome"), "%" + nome + "%");
        };
    }

    public static Specification<Funcionario> cpf(String cpf) {
        return (root, criteriaQuery, criteriaBuilder) -> {
            if (cpf == null) {
                return criteriaBuilder.conjunction();
            }

            return criteriaBuilder.equal(root.get("cpf"), cpf);
        };
    }

    public static Specification<Funcionario> dataContratacao(LocalDate dataContratacao) {
        return (root, criteriaQuery, criteriaBuilder) -> {
            if (dataContratacao == null) {
                return criteriaBuilder.conjunction();
            }

            return criteriaBuilder.lessThan(root.get("dataContratacao"), dataContratacao);
        };
    }

    public static Specification<Funcionario> salario(Double salario) {
        return (root, criteriaQuery, criteriaBuilder) -> {
            if (salario == null) {
                return criteriaBuilder.conjunction();
            }

            return criteriaBuilder.greaterThan(root.get("salario"), salario);
        };
    }

}
