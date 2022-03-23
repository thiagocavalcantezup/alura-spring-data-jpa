package br.com.alura.spring.data.jpa.services;

import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;
import java.util.Set;

import org.springframework.stereotype.Service;

import br.com.alura.spring.data.jpa.models.Funcionario;
import br.com.alura.spring.data.jpa.repositories.CargoRepository;
import br.com.alura.spring.data.jpa.repositories.FuncionarioRepository;
import br.com.alura.spring.data.jpa.repositories.UnidadeTrabalhoRepository;

@Service
public class RelatorioService {

    private boolean keepRunning;
    private final CargoRepository cargoRepository;
    private final FuncionarioRepository funcionarioRepository;
    private final UnidadeTrabalhoRepository unidadeTrabalhoRepository;

    public RelatorioService(CargoRepository cargoRepository,
                            FuncionarioRepository funcionarioRepository,
                            UnidadeTrabalhoRepository unidadeTrabalhoRepository) {
        this.cargoRepository = cargoRepository;
        this.funcionarioRepository = funcionarioRepository;
        this.unidadeTrabalhoRepository = unidadeTrabalhoRepository;
    }

    public void start(Scanner scanner) {
        keepRunning = true;

        while (keepRunning) {
            System.out.println();
            System.out.println("Qual ação você quer executar para Cargos?");
            System.out.println("0 - Sair");
            System.out.println(
                "1 - Relatório Funcionários (Nome (~), Data de Contratação (<), Salário (>))"
            );

            int action = scanner.nextInt();
            scanner.nextLine();

            switch (action) {
                case 1:
                    relatorioFuncionarios(scanner);
                    break;
                default:
                    keepRunning = false;
                    break;
            }
        }
    }

    private void relatorioFuncionarios(Scanner scanner) {
        System.out.println("Nome do(a) Funcionário(a):");
        String nome = "%" + scanner.nextLine().trim() + "%";

        System.out.println("Salário (separador decimal: ponto):");
        Double salario;
        try {
            salario = Double.parseDouble(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            salario = 0.0;
        }

        System.out.println("Data de Contratação (DD/MM/AAAA):");
        LocalDate dataContratacao;
        try {
            dataContratacao = LocalDate.parse(
                scanner.nextLine().trim(), DateTimeFormatter.ofPattern("dd/MM/yyyy")
            );
        } catch (DateTimeParseException e) {
            dataContratacao = LocalDate.now();
        }

        Set<Funcionario> funcionarios = funcionarioRepository.findAllByNomeIgnoreCaseLikeAndDataContratacaoLessThanAndSalarioGreaterThan(
            nome, dataContratacao, salario
        );

        funcionarios.forEach(System.out::println);
    }

}
