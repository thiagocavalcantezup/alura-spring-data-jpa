package br.com.alura.spring.data.jpa.services;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Optional;
import java.util.Scanner;

import org.springframework.stereotype.Service;

import br.com.alura.spring.data.jpa.models.Cargo;
import br.com.alura.spring.data.jpa.models.Funcionario;
import br.com.alura.spring.data.jpa.repositories.CargoRepository;
import br.com.alura.spring.data.jpa.repositories.FuncionarioRepository;

@Service
public class FuncionarioService {

    private boolean keepRunning;
    private final FuncionarioRepository funcionarioRepository;
    private final CargoRepository cargoRepository;

    public FuncionarioService(FuncionarioRepository funcionarioRepository,
                              CargoRepository cargoRepository) {
        this.funcionarioRepository = funcionarioRepository;
        this.cargoRepository = cargoRepository;
    }

    public void start(Scanner scanner) {
        keepRunning = true;

        while (keepRunning) {
            System.out.println();
            System.out.println("Qual ação você quer executar para Funcionários?");
            System.out.println(" 0 - Sair");
            System.out.println(" 1 - Salvar Funcionário (ID do Cargo)");
            System.out.println(" 2 - Salvar Funcionário (Descricao do Cargo)");
            System.out.println(" 3 - Atualizar Funcionário (busca ID)");
            System.out.println(" 4 - Atualizar Funcionário (busca Nome)");
            System.out.println(" 5 - Atualizar Funcionário (busca CPF)");
            System.out.println(" 6 - Listar Todos os Funcionários");
            System.out.println(" 7 - Listar Funcionários por Cargo (busca ID)");
            System.out.println(" 8 - Listar Funcionários por Cargo (busca Descrição)");
            System.out.println(" 9 - Remover Funcionário (busca ID)");
            System.out.println("10 - Remover Funcionário (busca Nome)");
            System.out.println("11 - Remover Funcionário (busca CPF)");

            int action = scanner.nextInt();
            scanner.nextLine();

            switch (action) {
                case 1:
                    saveByCargoId(scanner);
                    break;
                case 2:
                    saveByCargoDescricao(scanner);
                    break;
                case 3:
                    updateById(scanner);
                    break;
                case 4:
                    updateByNome(scanner);
                    break;
                case 5:
                    updateByCpf(scanner);
                    break;
                case 6:
                    findAll();
                    break;
                case 7:
                    findAllByCargoId(scanner);
                    break;
                case 8:
                    findAllByCargoDescricao(scanner);
                    break;
                case 9:
                    deleteById(scanner);
                    break;
                case 10:
                    deleteByNome(scanner);
                    break;
                case 11:
                    deleteByCpf(scanner);
                    break;
                default:
                    keepRunning = false;
                    break;
            }
        }
    }

    private void saveByCargoId(Scanner scanner) {
        System.out.println("Nome:");
        String nome = scanner.nextLine().trim();
        System.out.println("CPF:");
        String cpf = scanner.nextLine().trim().replaceAll("[^0-9]", "");
        System.out.println("Salário (separador decimal: ponto):");
        double salario = Double.parseDouble(scanner.nextLine().trim());
        System.out.println("Data de Contratação (DD/MM/AAAA):");
        LocalDate dataContratacao = LocalDate.parse(
            scanner.nextLine().trim(), DateTimeFormatter.ofPattern("dd/MM/yyyy")
        );

        System.out.println("ID do Cargo:");
        long id = scanner.nextInt();
        scanner.nextLine();
        Optional<Cargo> cargoOptional = cargoRepository.findById(id);

        if (cargoOptional.isPresent()) {
            Funcionario funcionario = new Funcionario(
                nome, cpf, salario, dataContratacao, cargoOptional.get()
            );
            funcionarioRepository.save(funcionario);
            System.out.println("Salvo!");
        } else {
            System.out.println("Cargo não encontrado.");
        }
    }

    private void saveByCargoDescricao(Scanner scanner) {
        System.out.println("Nome:");
        String nome = scanner.nextLine().trim();
        System.out.println("CPF:");
        String cpf = scanner.nextLine().trim().replaceAll("[^0-9]", "");
        System.out.println("Salário (separador decimal: ponto):");
        double salario = Double.parseDouble(scanner.nextLine().trim());
        System.out.println("Data de Contratação (DD/MM/AAAA):");
        LocalDate dataContratacao = LocalDate.parse(
            scanner.nextLine().trim(), DateTimeFormatter.ofPattern("dd/MM/yyyy")
        );

        System.out.println("Descrição do Cargo:");
        String descricao = scanner.nextLine().trim();
        Optional<Cargo> cargoOptional = cargoRepository.findFirstByDescricaoIgnoreCase(descricao);

        if (cargoOptional.isPresent()) {
            Funcionario funcionario = new Funcionario(
                nome, cpf, salario, dataContratacao, cargoOptional.get()
            );
            funcionarioRepository.save(funcionario);
            System.out.println("Salvo!");
        } else {
            System.out.println("Cargo não encontrado.");
        }
    }

    private void updateById(Scanner scanner) {
        System.out.println("ID:");
        long id = scanner.nextInt();
        scanner.nextLine();
        Optional<Funcionario> funcionarioOptional = funcionarioRepository.findById(id);

        if (funcionarioOptional.isPresent()) {
            update(scanner, funcionarioOptional.get());
        } else {
            System.out.println("Registro não encontrado.");
        }
    }

    private void updateByNome(Scanner scanner) {
        System.out.println("Nome:");
        String nome = scanner.nextLine().trim();
        Optional<Funcionario> funcionarioOptional = funcionarioRepository.findFirstByNomeIgnoreCase(
            nome
        );

        if (funcionarioOptional.isPresent()) {
            update(scanner, funcionarioOptional.get());
        } else {
            System.out.println("Registro não encontrado.");
        }
    }

    private void updateByCpf(Scanner scanner) {
        System.out.println("CPF:");
        String cpf = scanner.nextLine().trim().replaceAll("[^0-9]", "");
        Optional<Funcionario> funcionarioOptional = funcionarioRepository.findFirstByCpf(cpf);

        if (funcionarioOptional.isPresent()) {
            update(scanner, funcionarioOptional.get());
        } else {
            System.out.println("Registro não encontrado.");
        }
    }

    private void update(Scanner scanner, Funcionario funcionario) {
        System.out.println("Novo Nome (deixe em branco para manter):");
        String novoNome = scanner.nextLine().trim();

        System.out.println("Novo CPF (deixe em branco para manter):");
        String novoCpf = scanner.nextLine().trim().replaceAll("[^0-9]", "");

        System.out.println(
            "Novo Salário (separador decimal: ponto) (deixe em branco para manter):"
        );
        Double novoSalario;
        try {
            novoSalario = Double.parseDouble(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            novoSalario = null;
        }

        System.out.println("Nova Data de Contratação (DD/MM/AAAA) (deixe em branco para manter):");
        LocalDate novaDataContratacao;
        try {
            novaDataContratacao = LocalDate.parse(
                scanner.nextLine().trim(), DateTimeFormatter.ofPattern("dd/MM/yyyy")
            );
        } catch (DateTimeParseException e) {
            novaDataContratacao = null;
        }

        if (!novoNome.isEmpty()) {
            funcionario.setNome(novoNome);
        }

        if (!novoCpf.isEmpty()) {
            funcionario.setCpf(novoCpf);
        }

        if (novoSalario != null) {
            funcionario.setSalario(novoSalario);
        }

        if (novaDataContratacao != null) {
            funcionario.setDataContratacao(novaDataContratacao);
        }

        funcionarioRepository.save(funcionario);
        System.out.println("Salvo!");
    }

    private void findAll() {
        funcionarioRepository.findAll().forEach(System.out::println);
    }

    private void findAllByCargoId(Scanner scanner) {
        System.out.println("ID do Cargo:");
        long id = scanner.nextInt();
        scanner.nextLine();
        Optional<Cargo> cargoOptional = cargoRepository.findById(id);

        if (cargoOptional.isPresent()) {
            funcionarioRepository.findAllByCargoId(id).forEach(System.out::println);
        } else {
            System.out.println("Cargo não encontrado.");
        }

    }

    private void findAllByCargoDescricao(Scanner scanner) {
        System.out.println("Descrição do Cargo:");
        String descricao = scanner.nextLine().trim();
        Optional<Cargo> cargoOptional = cargoRepository.findFirstByDescricaoIgnoreCase(descricao);

        if (cargoOptional.isPresent()) {
            funcionarioRepository.findAllByCargoDescricao(descricao).forEach(System.out::println);
        } else {
            System.out.println("Cargo não encontrado.");
        }
    }

    private void deleteById(Scanner scanner) {
        System.out.println("ID:");
        long id = scanner.nextInt();
        scanner.nextLine();
        Optional<Funcionario> funcionarioOptional = funcionarioRepository.findById(id);

        if (funcionarioOptional.isPresent()) {
            delete(funcionarioOptional.get());
        } else {
            System.out.println("Registro não encontrado.");
        }
    }

    private void deleteByNome(Scanner scanner) {
        System.out.println("Nome:");
        String nome = scanner.nextLine().trim();
        Optional<Funcionario> funcionarioOptional = funcionarioRepository.findFirstByNomeIgnoreCase(
            nome
        );

        if (funcionarioOptional.isPresent()) {
            delete(funcionarioOptional.get());
        } else {
            System.out.println("Registro não encontrado.");
        }
    }

    private void deleteByCpf(Scanner scanner) {
        System.out.println("CPF:");
        String cpf = scanner.nextLine().trim().replaceAll("[^0-9]", "");
        Optional<Funcionario> funcionarioOptional = funcionarioRepository.findFirstByCpf(cpf);

        if (funcionarioOptional.isPresent()) {
            delete(funcionarioOptional.get());
        } else {
            System.out.println("Registro não encontrado.");
        }
    }

    private void delete(Funcionario funcionario) {
        funcionarioRepository.delete(funcionario);
        System.out.println("Removido!");
    }

}
