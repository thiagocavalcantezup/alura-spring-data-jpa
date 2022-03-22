package br.com.alura.spring.data.jpa.services;

import java.util.Scanner;

import org.springframework.stereotype.Service;

import br.com.alura.spring.data.jpa.models.Cargo;
import br.com.alura.spring.data.jpa.repositories.CargoRepository;

@Service
public class CargoService {

    private final CargoRepository cargoRepository;

    public CargoService(CargoRepository cargoRepository) {
        this.cargoRepository = cargoRepository;
    }

    public void start(Scanner scanner) {
        save(scanner);
    }

    private void save(Scanner scanner) {
        System.out.println("Descrição:");
        String descricao = scanner.next();
        Cargo cargo = new Cargo(descricao);
        cargoRepository.save(cargo);
        System.out.println("Salvo!");
    }

}
