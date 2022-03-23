package br.com.alura.spring.data.jpa.services;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;
import java.util.Set;

import org.springframework.stereotype.Service;

import br.com.alura.spring.data.jpa.models.Funcionario;
import br.com.alura.spring.data.jpa.repositories.FuncionarioRepository;

@Service
public class RelatorioService {

    private boolean keepRunning;
    private final FuncionarioRepository funcionarioRepository;

    public RelatorioService(FuncionarioRepository funcionarioRepository) {
        this.funcionarioRepository = funcionarioRepository;
    }

    public void start(Scanner scanner) {
        keepRunning = true;

        while (keepRunning) {
            System.out.println();
            System.out.println("Qual ação você quer executar para Cargos?");
            System.out.println("0 - Sair");
            System.out.println(
                "1 - Relatório Funcionários: Derived (Buscas: Nome (~), Data de Contratação (<), Salário (>))"
            );
            System.out.println(
                "2 - Relatório Funcionários: JPQL (Buscas: Nome (~), Data de Contratação (<), Salário (>))"
            );
            System.out.println("3 - Relatório Funcionários: Projeção (Id, Nome, Salário)");
            System.out.println("4 - Relatório Funcionários: DTO (Id, Nome, Salário)");

            int action = scanner.nextInt();
            scanner.nextLine();

            switch (action) {
                case 1:
                    relatorioFuncionariosDerived(scanner);
                    break;
                case 2:
                    relatorioFuncionariosJpql(scanner);
                    break;
                case 3:
                    relatorioFuncionariosListaProjecao(scanner);
                    break;
                case 4:
                    relatorioFuncionariosListaDTO(scanner);
                    break;
                default:
                    keepRunning = false;
                    break;
            }
        }
    }

    private String readFuncionarioNome(Scanner scanner) {
        System.out.println("Nome do(a) Funcionário(a):");
        return "%" + scanner.nextLine().trim() + "%";
    }

    private LocalDate readFuncionarioDataContratacao(Scanner scanner) {
        System.out.println("Data de Contratação (DD/MM/AAAA):");
        try {
            return LocalDate.parse(
                scanner.nextLine().trim(), DateTimeFormatter.ofPattern("dd/MM/yyyy")
            );
        } catch (DateTimeParseException e) {
            return LocalDate.now();
        }
    }

    private double readFuncionarioSalario(Scanner scanner) {
        System.out.println("Salário (separador decimal: ponto):");
        try {
            return Double.parseDouble(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            return 0.0;
        }
    }

    private void relatorioFuncionariosDerived(Scanner scanner) {
        String nome = readFuncionarioNome(scanner);
        LocalDate dataContratacao = readFuncionarioDataContratacao(scanner);
        Double salario = readFuncionarioSalario(scanner);

        Set<Funcionario> funcionarios = funcionarioRepository.findAllByNomeIgnoreCaseLikeAndDataContratacaoLessThanAndSalarioGreaterThan(
            nome, dataContratacao, salario
        );

        funcionarios.forEach(System.out::println);
    }

    private void relatorioFuncionariosJpql(Scanner scanner) {
        String nome = readFuncionarioNome(scanner);
        LocalDate dataContratacao = readFuncionarioDataContratacao(scanner);
        Double salario = readFuncionarioSalario(scanner);

        Set<Funcionario> funcionarios = funcionarioRepository.funcionarioReport(
            nome, dataContratacao, salario
        );

        funcionarios.forEach(System.out::println);
    }

    private void relatorioFuncionariosListaProjecao(Scanner scanner) {
        funcionarioRepository.findAllIdNomeSalarioProjecao().forEach(func -> {
            NumberFormat nformatter = new DecimalFormat("R$#0.00");

            System.out.println(
                "id:      " + func.getId() + "\nnome:    " + func.getNome() + "\nsalario: "
                        + nformatter.format(func.getSalario()) + "\n"
            );
        });
    }

    private void relatorioFuncionariosListaDTO(Scanner scanner) {
        funcionarioRepository.findAllIdNomeSalarioDTO().forEach(System.out::println);
    }

}
