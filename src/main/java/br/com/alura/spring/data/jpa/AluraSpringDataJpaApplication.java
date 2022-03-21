package br.com.alura.spring.data.jpa;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import br.com.alura.spring.data.jpa.models.Cargo;
import br.com.alura.spring.data.jpa.repositories.CargoRepository;

@SpringBootApplication
public class AluraSpringDataJpaApplication implements CommandLineRunner {

    private final CargoRepository cargoRepository;

    public AluraSpringDataJpaApplication(CargoRepository cargoRepository) {
        this.cargoRepository = cargoRepository;
    }

    public static void main(String[] args) {
        SpringApplication.run(AluraSpringDataJpaApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        Cargo cargo = new Cargo("DESENVOLVEDOR DE SOFTWARE");
        cargoRepository.save(cargo);
    }

}
