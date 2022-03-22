package br.com.alura.spring.data.jpa.services;

import java.util.Optional;
import java.util.Scanner;

import org.springframework.stereotype.Service;

import br.com.alura.spring.data.jpa.models.Cargo;
import br.com.alura.spring.data.jpa.repositories.CargoRepository;

@Service
public class CargoService {

    private boolean keepRunning = true;
    private final CargoRepository cargoRepository;

    public CargoService(CargoRepository cargoRepository) {
        this.cargoRepository = cargoRepository;
    }

    public void start(Scanner scanner) {
        while (keepRunning) {
            System.out.println("Qual ação você quer executar para Cargos?");
            System.out.println("0 - Sair");
            System.out.println("1 - Salvar Cargo");
            System.out.println("2 - Atualizar Cargo (busca ID)");
            System.out.println("3 - Atualizar Cargo (busca Descrição)");
            System.out.println("4 - Listar Cargos");

            int action = scanner.nextInt();
            scanner.nextLine();

            switch (action) {
                case 1:
                    save(scanner);
                    break;
                case 2:
                    updateById(scanner);
                    break;
                case 3:
                    updateByDescricao(scanner);
                    break;
                case 4:
                    findAll();
                    break;
                default:
                    keepRunning = false;
                    break;
            }
        }
    }

    private void save(Scanner scanner) {
        System.out.println("Descrição:");
        String descricao = scanner.nextLine().trim();
        Cargo cargo = new Cargo(descricao);
        cargoRepository.save(cargo);
        System.out.println("Salvo!");
    }

    private void update(Scanner scanner, Cargo cargo) {
        System.out.println("Nova Descrição:");
        String novaDescricao = scanner.nextLine().trim();
        cargo.setDescricao(novaDescricao);
        cargoRepository.save(cargo);
        System.out.println("Salvo!");
    }

    private void updateById(Scanner scanner) {
        System.out.println("ID:");
        long id = scanner.nextInt();
        scanner.nextLine();
        Optional<Cargo> cargoOptional = cargoRepository.findById(id);

        if (cargoOptional.isPresent()) {
            update(scanner, cargoOptional.get());
        } else {
            System.out.println("Registro não encontrado.");
        }
    }

    private void updateByDescricao(Scanner scanner) {
        System.out.println("Descrição:");
        String descricao = scanner.nextLine().trim();
        Optional<Cargo> cargoOptional = cargoRepository.findByDescricao(descricao.toUpperCase());

        if (cargoOptional.isPresent()) {
            update(scanner, cargoOptional.get());
        } else {
            System.out.println("Registro não encontrado.");
        }
    }

    private void findAll() {
        for (Cargo cargo : cargoRepository.findAll()) {
            System.out.println(cargo.getId() + " " + cargo.getDescricao());
        }
    }

}
