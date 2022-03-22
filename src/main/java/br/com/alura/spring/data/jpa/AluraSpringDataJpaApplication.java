package br.com.alura.spring.data.jpa;

import java.util.Scanner;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import br.com.alura.spring.data.jpa.services.CargoService;

@SpringBootApplication
public class AluraSpringDataJpaApplication implements CommandLineRunner {

    private boolean keepRunning = true;
    private CargoService cargoService;

    public AluraSpringDataJpaApplication(CargoService cargoService) {
        this.cargoService = cargoService;
    }

    public static void main(String[] args) {
        SpringApplication.run(AluraSpringDataJpaApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        Scanner scanner = new Scanner(System.in);

        while (keepRunning) {
            System.out.println("Qual ação você quer executar?");
            System.out.println("0 - Sair");
            System.out.println("1 - Cargo");

            int action = scanner.nextInt();

            switch (action) {
                case 1:
                    cargoService.start(scanner);
                default:
                    keepRunning = false;
                    break;
            }
        }
    }

}
