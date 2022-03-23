package br.com.alura.spring.data.jpa.services;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import br.com.alura.spring.data.jpa.models.Funcionario;
import br.com.alura.spring.data.jpa.repositories.FuncionarioRepository;
import br.com.alura.spring.data.jpa.specifications.FuncionarioSpecification;

@Service
public class RelatorioDinamicoService {

    private boolean keepRunning;
    private final FuncionarioRepository funcionarioRepository;

    public RelatorioDinamicoService(FuncionarioRepository funcionarioRepository) {
        this.funcionarioRepository = funcionarioRepository;
    }

    public void start(Scanner scanner) {
        keepRunning = true;

        while (keepRunning) {
            System.out.println();
            System.out.println("Qual ação você quer executar para Cargos?");
            System.out.println("0 - Sair");
            System.out.println(
                "1 - Relatório Funcionários (buscas: Nome (~), CPF (=), Salário(>), Data de Contratação(<) - NULL para ignorar)"
            );
            int action = scanner.nextInt();
            scanner.nextLine();

            switch (action) {
                case 1:
                    relatorioDinamicoFuncionarioNome(scanner);
                    break;
                default:
                    keepRunning = false;
                    break;
            }
        }
    }

    public void relatorioDinamicoFuncionarioNome(Scanner scanner) {
        System.out.println("Nome do(a) Funcionário(a):");
        String nome = scanner.nextLine().trim();

        if (nome.equalsIgnoreCase("NULL")) {
            nome = null;
        }

        System.out.println("CPF do(a) Funcionário(a):");
        String cpf = scanner.nextLine().trim();

        if (cpf.equalsIgnoreCase("NULL")) {
            cpf = null;
        }

        System.out.println("Salário do(a) Funcionário(a):");
        Double salario;
        try {
            salario = Double.parseDouble(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            salario = null;
        }

        System.out.println("Data de Contratação (DD/MM/AAAA) do(a) Funcionário(a):");
        LocalDate dataContratacao;
        try {
            dataContratacao = LocalDate.parse(
                scanner.nextLine().trim(), DateTimeFormatter.ofPattern("dd/MM/yyyy")
            );
        } catch (DateTimeParseException e) {
            dataContratacao = null;
        }

        List<Funcionario> funcionarios = funcionarioRepository.findAll(
            Specification.where(
                FuncionarioSpecification.nome(nome)
                                        .and(FuncionarioSpecification.cpf(cpf))
                                        .and(FuncionarioSpecification.salario(salario))
                                        .and(
                                            FuncionarioSpecification.dataContratacao(
                                                dataContratacao
                                            )
                                        )
            )
        );

        funcionarios.forEach(System.out::println);
    }

}
