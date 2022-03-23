package br.com.alura.spring.data.jpa.services;

import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Optional;
import java.util.Scanner;

import org.springframework.stereotype.Service;

import br.com.alura.spring.data.jpa.models.Cargo;
import br.com.alura.spring.data.jpa.models.Funcionario;
import br.com.alura.spring.data.jpa.models.UnidadeTrabalho;
import br.com.alura.spring.data.jpa.repositories.CargoRepository;
import br.com.alura.spring.data.jpa.repositories.FuncionarioRepository;
import br.com.alura.spring.data.jpa.repositories.UnidadeTrabalhoRepository;

@Service
public class FuncionarioService {

    private boolean keepRunning;
    private final FuncionarioRepository funcionarioRepository;
    private final CargoRepository cargoRepository;
    private final UnidadeTrabalhoRepository unidadeTrabalhoRepository;

    public FuncionarioService(FuncionarioRepository funcionarioRepository,
                              CargoRepository cargoRepository,
                              UnidadeTrabalhoRepository unidadeTrabalhoRepository) {
        this.funcionarioRepository = funcionarioRepository;
        this.cargoRepository = cargoRepository;
        this.unidadeTrabalhoRepository = unidadeTrabalhoRepository;
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
            System.out.println(" 6 - Listar Funcionários (Todos)");
            System.out.println(" 7 - Listar Funcionários (busca Nome (~))");
            System.out.println(" 8 - Listar Funcionários (busca Data de Contratacão (>))");
            System.out.println(" 9 - Listar Funcionários por Cargo (busca ID)");
            System.out.println("10 - Listar Funcionários por Cargo (busca Descrição)");
            System.out.println("11 - Listar Funcionários por Unidade (busca ID)");
            System.out.println("12 - Listar Funcionários por Unidade (busca Descrição)");
            System.out.println("13 - Listar Funcionários por Unidade (busca Endereço)");
            System.out.println("14 - Remover Funcionário (busca ID)");
            System.out.println("15 - Remover Funcionário (busca Nome)");
            System.out.println("16 - Remover Funcionário (busca CPF)");
            System.out.println("17 - Adicionar Unidade (busca ID)");
            System.out.println("18 - Adicionar Unidade (busca Descrição)");
            System.out.println("19 - Adicionar Unidade (busca Endereço)");
            System.out.println("20 - Remover Unidade (busca ID)");
            System.out.println("21 - Remover Unidade (busca Descrição)");
            System.out.println("22 - Remover Unidade (busca Endereço)");

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
                    findAllByNome(scanner);
                    break;
                case 8:
                    findAllByDataContratacao(scanner);
                    break;
                case 9:
                    findAllByCargoId(scanner);
                    break;
                case 10:
                    findAllByCargoDescricao(scanner);
                    break;
                case 11:
                    findAllByUnidadeTrabalhoId(scanner);
                    break;
                case 12:
                    findAllByUnidadeTrabalhoDescricao(scanner);
                    break;
                case 13:
                    findAllByUnidadeTrabalhoEndereco(scanner);
                    break;
                case 14:
                    deleteById(scanner);
                    break;
                case 15:
                    deleteByNome(scanner);
                    break;
                case 16:
                    deleteByCpf(scanner);
                    break;
                case 17:
                    addUnidadeTrabalhoByUnidadeTrabalhoId(scanner);
                    break;
                case 18:
                    addUnidadeTrabalhoByUnidadeTrabalhoDescricao(scanner);
                    break;
                case 19:
                    addUnidadeTrabalhoByUnidadeTrabalhoEndereco(scanner);
                    break;
                case 20:
                    removeUnidadeTrabalhoByUnidadeTrabalhoId(scanner);
                    break;
                case 21:
                    removeUnidadeTrabalhoByUnidadeTrabalhoDescricao(scanner);
                    break;
                case 22:
                    removeUnidadeTrabalhoByUnidadeTrabalhoEndereco(scanner);
                    break;
                default:
                    keepRunning = false;
                    break;
            }
        }
    }

    private Funcionario newObj(Scanner scanner) {
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

        return new Funcionario(nome, cpf, salario, dataContratacao);
    }

    private void create(Funcionario funcionario) {
        funcionarioRepository.save(funcionario);
        System.out.println("Funcionário(a) salvo(a)!");
    }

    private Funcionario edit(Scanner scanner, Funcionario funcionario) {
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

        return funcionario;
    }

    private void update(Scanner scanner, Funcionario funcionario) {
        funcionarioRepository.save(edit(scanner, funcionario));
        System.out.println("Funcionário(a) atualizado(a)!");
    }

    private void delete(Funcionario funcionario) {
        funcionarioRepository.delete(funcionario);
        System.out.println("Funcionário(a) removido(a)!");
    }

    private Optional<Funcionario> findById(Scanner scanner) {
        System.out.println("ID do(a) Funcionário(a):");
        long id = scanner.nextInt();
        scanner.nextLine();
        return funcionarioRepository.findById(id);
    }

    private Optional<Funcionario> findByNome(Scanner scanner) {
        System.out.println("Nome do(a) Funcionário(a):");
        String nome = scanner.nextLine().trim();
        return funcionarioRepository.findFirstByNomeIgnoreCase(nome);
    }

    private Optional<Funcionario> findByCpf(Scanner scanner) {
        System.out.println("CPF do(a) Funcionário(a):");
        String cpf = scanner.nextLine().trim().replaceAll("[^0-9]", "");
        return funcionarioRepository.findFirstByCpf(cpf);
    }

    private Optional<Cargo> findCargoById(Scanner scanner) {
        System.out.println("ID do Cargo:");
        long id = scanner.nextInt();
        scanner.nextLine();
        return cargoRepository.findById(id);
    }

    private Optional<Cargo> findCargoByDescricao(Scanner scanner) {
        System.out.println("Descrição do Cargo:");
        String descricao = scanner.nextLine().trim();
        return cargoRepository.findFirstByDescricaoIgnoreCase(descricao);
    }

    private Optional<UnidadeTrabalho> findUnidadeTrabalhoById(Scanner scanner) {
        System.out.println("ID da Unidade:");
        long id = scanner.nextInt();
        scanner.nextLine();
        return unidadeTrabalhoRepository.findById(id);
    }

    private Optional<UnidadeTrabalho> findUnidadeTrabalhoByDescricao(Scanner scanner) {
        System.out.println("Descrição da Unidade:");
        String descricao = scanner.nextLine().trim();
        return unidadeTrabalhoRepository.findFirstByDescricaoIgnoreCase(descricao);
    }

    private Optional<UnidadeTrabalho> findUnidadeTrabalhoByEndereco(Scanner scanner) {
        System.out.println("Endereço da Unidade:");
        String endereeco = scanner.nextLine().trim();
        return unidadeTrabalhoRepository.findFirstByEnderecoIgnoreCase(endereeco);
    }

    private void saveByCargoId(Scanner scanner) {
        saveByCargoOptional(newObj(scanner), findCargoById(scanner));
    }

    private void saveByCargoDescricao(Scanner scanner) {
        saveByCargoOptional(newObj(scanner), findCargoByDescricao(scanner));
    }

    private void saveByCargoOptional(Funcionario funcionario, Optional<Cargo> cargoOptional) {
        if (cargoOptional.isPresent()) {
            funcionario.setCargo(cargoOptional.get());
            create(funcionario);
        } else {
            System.out.println("Cargo não encontrado.");
        }
    }

    private void updateById(Scanner scanner) {
        updateOptional(scanner, findById(scanner));
    }

    private void updateByNome(Scanner scanner) {
        updateOptional(scanner, findByNome(scanner));
    }

    private void updateByCpf(Scanner scanner) {
        updateOptional(scanner, findByCpf(scanner));
    }

    private void updateOptional(Scanner scanner, Optional<Funcionario> funcionarioOptional) {
        if (funcionarioOptional.isPresent()) {
            update(scanner, funcionarioOptional.get());
        } else {
            System.out.println("Funcionário(a) não encontrado.");
        }
    }

    private void findAll() {
        funcionarioRepository.findAll().forEach(System.out::println);
    }

    private void findAllByNome(Scanner scanner) {
        System.out.println("Nome do(a) Funcionário(a):");
        String nome = scanner.nextLine().trim();
        funcionarioRepository.findAllByNomeIgnoreCaseLike("%" + nome + "%")
                             .forEach(System.out::println);
    }

    private void findAllByDataContratacao(Scanner scanner) {
        System.out.println("Data de Contratação (DD/MM/AAAA):");
        LocalDate dataContratacao;
        try {
            dataContratacao = LocalDate.parse(
                scanner.nextLine().trim(), DateTimeFormatter.ofPattern("dd/MM/yyyy")
            );
        } catch (DateTimeParseException e) {
            dataContratacao = LocalDate.of(Year.MIN_VALUE, Month.JANUARY, 1);
        }

        funcionarioRepository.findAllByDataContratacaoMaiorQue(dataContratacao)
                             .forEach(System.out::println);
    }

    private void findAllByCargoId(Scanner scanner) {
        Optional<Cargo> cargoOptional = findCargoById(scanner);

        if (cargoOptional.isPresent()) {
            funcionarioRepository.findAllByCargoId(cargoOptional.get().getId())
                                 .forEach(System.out::println);
        } else {
            System.out.println("Cargo não encontrado.");
        }
    }

    private void findAllByCargoDescricao(Scanner scanner) {
        Optional<Cargo> cargoOptional = findCargoByDescricao(scanner);

        if (cargoOptional.isPresent()) {
            funcionarioRepository.findAllByCargoDescricao(cargoOptional.get().getDescricao())
                                 .forEach(System.out::println);
        } else {
            System.out.println("Cargo não encontrado.");
        }
    }

    private void findAllByUnidadeTrabalhoId(Scanner scanner) {
        Optional<UnidadeTrabalho> unidadeTrabalhoOptional = findUnidadeTrabalhoById(scanner);

        if (unidadeTrabalhoOptional.isPresent()) {
            funcionarioRepository.findAllByUnidadesTrabalho_Id(
                unidadeTrabalhoOptional.get().getId()
            ).forEach(System.out::println);
        } else {
            System.out.println("Unidade não encontrada.");
        }
    }

    private void findAllByUnidadeTrabalhoDescricao(Scanner scanner) {
        Optional<UnidadeTrabalho> unidadeTrabalhoOptional = findUnidadeTrabalhoByDescricao(scanner);

        if (unidadeTrabalhoOptional.isPresent()) {
            funcionarioRepository.findAllByUnidadesTrabalho_Descricao(
                unidadeTrabalhoOptional.get().getDescricao()
            ).forEach(System.out::println);
        } else {
            System.out.println("Unidade não encontrada.");
        }
    }

    private void findAllByUnidadeTrabalhoEndereco(Scanner scanner) {
        Optional<UnidadeTrabalho> unidadeTrabalhoOptional = findUnidadeTrabalhoByEndereco(scanner);

        if (unidadeTrabalhoOptional.isPresent()) {
            funcionarioRepository.findAllByUnidadesTrabalho_Endereco(
                unidadeTrabalhoOptional.get().getEndereco()
            ).forEach(System.out::println);
        } else {
            System.out.println("Unidade não encontrada.");
        }
    }

    private void deleteById(Scanner scanner) {
        deleteOptional(findById(scanner));
    }

    private void deleteByNome(Scanner scanner) {
        deleteOptional(findByNome(scanner));
    }

    private void deleteByCpf(Scanner scanner) {
        deleteOptional(findByCpf(scanner));
    }

    private void deleteOptional(Optional<Funcionario> funcionarioOptional) {
        if (funcionarioOptional.isPresent()) {
            delete(funcionarioOptional.get());
        } else {
            System.out.println("Funcionário(a) não encontrado.");
        }
    }

    private void addUnidadeTrabalhoByUnidadeTrabalhoId(Scanner scanner) {
        addUnidadeTrabalhoById(scanner, findUnidadeTrabalhoById(scanner));
    }

    private void addUnidadeTrabalhoByUnidadeTrabalhoDescricao(Scanner scanner) {
        addUnidadeTrabalhoById(scanner, findUnidadeTrabalhoByDescricao(scanner));
    }

    private void addUnidadeTrabalhoByUnidadeTrabalhoEndereco(Scanner scanner) {
        addUnidadeTrabalhoById(scanner, findUnidadeTrabalhoByEndereco(scanner));
    }

    private void addUnidadeTrabalhoById(Scanner scanner,
                                        Optional<UnidadeTrabalho> unidadeTrabalhoOptional) {
        Optional<Funcionario> funcionarioOptional = findById(scanner);

        if (funcionarioOptional.isPresent()) {
            if (unidadeTrabalhoOptional.isPresent()) {
                Funcionario funcionario = funcionarioOptional.get();
                UnidadeTrabalho unidadeTrabalho = unidadeTrabalhoOptional.get();
                unidadeTrabalho.getFuncionarios().add(funcionario);
                funcionario.getUnidadesTrabalho().add(unidadeTrabalho);
                unidadeTrabalhoRepository.save(unidadeTrabalho);
                funcionarioRepository.save(funcionario);
                System.out.println("Unidade adicionada!");
            } else {
                System.out.println("Unidade não encontrada.");
            }
        } else {
            System.out.println("Funcionário(a) não encontrado.");
        }
    }

    private void removeUnidadeTrabalhoByUnidadeTrabalhoId(Scanner scanner) {
        removeUnidadeTrabalhoById(scanner, findUnidadeTrabalhoById(scanner));
    }

    private void removeUnidadeTrabalhoByUnidadeTrabalhoDescricao(Scanner scanner) {
        removeUnidadeTrabalhoById(scanner, findUnidadeTrabalhoByDescricao(scanner));
    }

    private void removeUnidadeTrabalhoByUnidadeTrabalhoEndereco(Scanner scanner) {
        removeUnidadeTrabalhoById(scanner, findUnidadeTrabalhoByEndereco(scanner));
    }

    private void removeUnidadeTrabalhoById(Scanner scanner,
                                           Optional<UnidadeTrabalho> unidadeTrabalhoOptional) {
        Optional<Funcionario> funcionarioOptional = findById(scanner);

        if (funcionarioOptional.isPresent()) {
            if (unidadeTrabalhoOptional.isPresent()) {
                Funcionario funcionario = funcionarioOptional.get();
                UnidadeTrabalho unidadeTrabalho = unidadeTrabalhoOptional.get();
                unidadeTrabalho.getFuncionarios().remove(funcionario);
                funcionario.getUnidadesTrabalho().remove(unidadeTrabalho);
                unidadeTrabalhoRepository.save(unidadeTrabalho);
                funcionarioRepository.save(funcionario);
                System.out.println("Unidade removida!");
            } else {
                System.out.println("Unidade não encontrada.");
            }
        } else {
            System.out.println("Funcionário(a) não encontrado.");
        }
    }

}
