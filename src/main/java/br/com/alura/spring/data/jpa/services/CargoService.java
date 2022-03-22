package br.com.alura.spring.data.jpa.services;

import java.util.Optional;
import java.util.Scanner;

import org.springframework.stereotype.Service;

import br.com.alura.spring.data.jpa.models.Cargo;
import br.com.alura.spring.data.jpa.repositories.CargoRepository;

@Service
public class CargoService {

    private boolean keepRunning;
    private final CargoRepository cargoRepository;

    public CargoService(CargoRepository cargoRepository) {
        this.cargoRepository = cargoRepository;
    }

    public void start(Scanner scanner) {
        keepRunning = true;

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
                    create(scanner);
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

    private Cargo newObj(Scanner scanner) {
        System.out.println("Descrição:");
        String descricao = scanner.nextLine().trim();
        return new Cargo(descricao);
    }

    private void create(Scanner scanner) {
        cargoRepository.save(newObj(scanner));
        System.out.println("Cargo salvo!");
    }

    private Cargo edit(Scanner scanner, Cargo cargo) {
        System.out.println("Nova Descrição:");
        String novaDescricao = scanner.nextLine().trim();
        cargo.setDescricao(novaDescricao);

        return cargo;
    }

    private void update(Scanner scanner, Cargo cargo) {
        cargoRepository.save(edit(scanner, cargo));
        System.out.println("Cargo atualizado!");
    }

    private void delete(Cargo cargo) {
        cargoRepository.delete(cargo);
        System.out.println("Cargo removido!");
    }

    private Optional<Cargo> findById(Scanner scanner) {
        System.out.println("ID:");
        long id = scanner.nextInt();
        scanner.nextLine();
        return cargoRepository.findById(id);
    }

    private Optional<Cargo> findByDescricao(Scanner scanner) {
        System.out.println("Descrição:");
        String descricao = scanner.nextLine().trim();
        return cargoRepository.findFirstByDescricaoIgnoreCase(descricao);
    }

    private void updateById(Scanner scanner) {
        updateOptional(scanner, findById(scanner));
    }

    private void updateByDescricao(Scanner scanner) {
        updateOptional(scanner, findByDescricao(scanner));
    }

    private void updateOptional(Scanner scanner, Optional<Cargo> cargoOptional) {
        if (cargoOptional.isPresent()) {
            update(scanner, cargoOptional.get());
        } else {
            System.out.println("Cargo não encontrado.");
        }
    }

    private void findAll() {
        cargoRepository.findAll().forEach(System.out::println);
    }

    private void deleteById(Scanner scanner) {
        deleteOptional(findById(scanner));
    }

    private void deleteByDescricao(Scanner scanner) {
        deleteOptional(findByDescricao(scanner));
    }

    private void deleteOptional(Optional<Cargo> cargoOptional) {
        if (cargoOptional.isPresent()) {
            delete(cargoOptional.get());
        } else {
            System.out.println("Cargo não encontrado.");
        }
    }

}
