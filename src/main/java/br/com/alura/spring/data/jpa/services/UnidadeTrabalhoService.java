package br.com.alura.spring.data.jpa.services;

import java.util.Optional;
import java.util.Scanner;

import org.springframework.stereotype.Service;

import br.com.alura.spring.data.jpa.models.UnidadeTrabalho;
import br.com.alura.spring.data.jpa.repositories.UnidadeTrabalhoRepository;

@Service
public class UnidadeTrabalhoService {

    private boolean keepRunning;
    private final UnidadeTrabalhoRepository unidadeTrabalhoRepository;

    public UnidadeTrabalhoService(UnidadeTrabalhoRepository unidadeTrabalhoRepository) {
        this.unidadeTrabalhoRepository = unidadeTrabalhoRepository;
    }

    public void start(Scanner scanner) {
        keepRunning = true;

        while (keepRunning) {
            System.out.println();
            System.out.println("Qual ação você quer executar para Unidades de Trabalho?");
            System.out.println("0 - Sair");
            System.out.println("1 - Salvar Unidade de Trabalho");
            System.out.println("2 - Atualizar Unidade de Trabalho (busca ID)");
            System.out.println("3 - Atualizar Unidade de Trabalho (busca Descrição)");
            System.out.println("4 - Atualizar Unidade de Trabalho (busca Endereço)");
            System.out.println("5 - Listar Unidades de Trabalho");
            System.out.println("6 - Remover Unidade de Trabalho (busca ID)");
            System.out.println("7 - Remover Unidade de Trabalho (busca Descrição)");
            System.out.println("8 - Remover Unidade de Trabalho (busca Endereço)");

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
                    updateByEndereco(scanner);
                    break;
                case 5:
                    findAll();
                    break;
                case 6:
                    deleteById(scanner);
                    break;
                case 7:
                    deleteByDescricao(scanner);
                    break;
                case 8:
                    deleteByEndereco(scanner);
                    break;
                default:
                    keepRunning = false;
                    break;
            }
        }
    }

    private UnidadeTrabalho newObj(Scanner scanner) {
        System.out.println("Descrição:");
        String descricao = scanner.nextLine().trim();
        System.out.println("Endereço:");
        String endereco = scanner.nextLine().trim();

        return new UnidadeTrabalho(descricao, endereco);
    }

    private void create(Scanner scanner) {
        unidadeTrabalhoRepository.save(newObj(scanner));
        System.out.println("Unidade salva!");
    }

    private UnidadeTrabalho edit(Scanner scanner, UnidadeTrabalho unidadeTrabalho) {
        System.out.println("Nova Descrição (deixe em branco para manter):");
        String novaDescricao = scanner.nextLine().trim();
        System.out.println("Novo Endereço (deixe em branco para manter):");
        String novoEndereco = scanner.nextLine().trim();

        if (!novaDescricao.isEmpty()) {
            unidadeTrabalho.setDescricao(novaDescricao);
        }

        if (!novoEndereco.isEmpty()) {
            unidadeTrabalho.setEndereco(novoEndereco);
        }

        return unidadeTrabalho;
    }

    private void update(Scanner scanner, UnidadeTrabalho unidadeTrabalho) {
        unidadeTrabalhoRepository.save(edit(scanner, unidadeTrabalho));
        System.out.println("Unidade atualizada!");
    }

    private void delete(UnidadeTrabalho unidadeTrabalho) {
        unidadeTrabalhoRepository.delete(unidadeTrabalho);
        System.out.println("Unidade removida!");
    }

    private Optional<UnidadeTrabalho> findById(Scanner scanner) {
        System.out.println("ID:");
        long id = scanner.nextInt();
        scanner.nextLine();
        return unidadeTrabalhoRepository.findById(id);
    }

    private Optional<UnidadeTrabalho> findByDescricao(Scanner scanner) {
        System.out.println("Descrição:");
        String descricao = scanner.nextLine().trim();
        return unidadeTrabalhoRepository.findFirstByDescricaoIgnoreCase(descricao);
    }

    private Optional<UnidadeTrabalho> findByEndereco(Scanner scanner) {
        System.out.println("Endereço:");
        String endereco = scanner.nextLine().trim();
        return unidadeTrabalhoRepository.findFirstByEnderecoIgnoreCase(endereco);
    }

    private void updateById(Scanner scanner) {
        updateOptional(scanner, findById(scanner));
    }

    private void updateByDescricao(Scanner scanner) {
        updateOptional(scanner, findByDescricao(scanner));
    }

    private void updateByEndereco(Scanner scanner) {
        updateOptional(scanner, findByEndereco(scanner));
    }

    private void updateOptional(Scanner scanner,
                                Optional<UnidadeTrabalho> unidadeTrabalhoOptional) {
        if (unidadeTrabalhoOptional.isPresent()) {
            update(scanner, unidadeTrabalhoOptional.get());
        } else {
            System.out.println("Unidade não encontrada.");
        }
    }

    private void findAll() {
        unidadeTrabalhoRepository.findAll().forEach(System.out::println);
    }

    private void deleteById(Scanner scanner) {
        deleteOptional(findById(scanner));
    }

    private void deleteByDescricao(Scanner scanner) {
        deleteOptional(findByDescricao(scanner));
    }

    private void deleteByEndereco(Scanner scanner) {
        deleteOptional(findByEndereco(scanner));
    }

    private void deleteOptional(Optional<UnidadeTrabalho> unidadeTrabalhoOptional) {
        if (unidadeTrabalhoOptional.isPresent()) {
            delete(unidadeTrabalhoOptional.get());
        } else {
            System.out.println("Unidade não encontrada.");
        }
    }

}
