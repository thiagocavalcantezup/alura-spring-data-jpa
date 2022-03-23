package br.com.alura.spring.data.jpa;

import java.util.Scanner;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import br.com.alura.spring.data.jpa.services.CargoService;
import br.com.alura.spring.data.jpa.services.FuncionarioService;
import br.com.alura.spring.data.jpa.services.RelatorioService;
import br.com.alura.spring.data.jpa.services.UnidadeTrabalhoService;

@SpringBootApplication
public class AluraSpringDataJpaApplication implements CommandLineRunner {

    private boolean keepRunning;
    private CargoService cargoService;
    private FuncionarioService funcionarioService;
    private UnidadeTrabalhoService unidadeTrabalhoService;
    private RelatorioService relatorioService;

    public AluraSpringDataJpaApplication(CargoService cargoService,
                                         FuncionarioService funcionarioService,
                                         UnidadeTrabalhoService unidadeTrabalhoService,
                                         RelatorioService relatorioService) {
        this.cargoService = cargoService;
        this.funcionarioService = funcionarioService;
        this.unidadeTrabalhoService = unidadeTrabalhoService;
        this.relatorioService = relatorioService;
    }

    public static void main(String[] args) {
        SpringApplication.run(AluraSpringDataJpaApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        keepRunning = true;

        while (keepRunning) {
            System.out.println();
            System.out.println("Qual ação você quer executar?");
            System.out.println("0 - Sair");
            System.out.println("1 - Cargo");
            System.out.println("2 - Funcionário");
            System.out.println("3 - Unidade de Trabalho");
            System.out.println("4 - Relatórios");

            int action = scanner.nextInt();
            scanner.nextLine();

            switch (action) {
                case 1:
                    cargoService.start(scanner);
                    break;
                case 2:
                    funcionarioService.start(scanner);
                    break;
                case 3:
                    unidadeTrabalhoService.start(scanner);
                    break;
                case 4:
                    relatorioService.start(scanner);
                    break;
                default:
                    keepRunning = false;
                    break;
            }
        }
    }

}
