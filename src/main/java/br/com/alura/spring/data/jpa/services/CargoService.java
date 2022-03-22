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
            System.out.println();
            System.out.println("Qual ação você quer executar para Cargos?");
            System.out.println("0 - Sair");
            System.out.println("1 - Salvar Cargo");
            System.out.println("2 - Atualizar Cargo (busca ID)");
            System.out.println("3 - Atualizar Cargo (busca Descrição)");
            System.out.println("4 - Listar Cargos");
            System.out.println("5 - Remover Cargo (busca ID)");
            System.out.println("6 - Remover Cargo (busca Descrição)");

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
                case 5:
                    deleteById(scanner);
                    break;
                case 6:
                    deleteByDescricao(scanner);
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
        Optional<Cargo> cargoOptional = cargoRepository.findFirstByDescricao(
            descricao.toUpperCase()
        );

        if (cargoOptional.isPresent()) {
            update(scanner, cargoOptional.get());
        } else {
            System.out.println("Registro não encontrado.");
        }
    }

    private void update(Scanner scanner, Cargo cargo) {
        System.out.println("Nova Descrição:");
        String novaDescricao = scanner.nextLine().trim();
        cargo.setDescricao(novaDescricao);
        cargoRepository.save(cargo);
        System.out.println("Salvo!");
    }

    private void findAll() {
        cargoRepository.findAll().forEach(System.out::println);
    }

    private void deleteById(Scanner scanner) {
        System.out.println("ID:");
        long id = scanner.nextInt();
        scanner.nextLine();
        Optional<Cargo> cargoOptional = cargoRepository.findById(id);

        if (cargoOptional.isPresent()) {
            delete(cargoOptional.get());
        } else {
            System.out.println("Registro não encontrado.");
        }
    }

    private void deleteByDescricao(Scanner scanner) {
        System.out.println("Descrição:");
        String descricao = scanner.nextLine().trim();
        Optional<Cargo> cargoOptional = cargoRepository.findFirstByDescricao(
            descricao.toUpperCase()
        );

        if (cargoOptional.isPresent()) {
            delete(cargoOptional.get());
        } else {
            System.out.println("Registro não encontrado.");
        }
    }

    private void delete(Cargo cargo) {
        cargoRepository.delete(cargo);
        System.out.println("Removido!");
    }

}
