package br.com.alura.spring.data.jpa.models;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "funcionarios")
public class Funcionario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String nome;
    private String cpf;
    private double salario;
    private LocalDate dataContratacao;

    public Funcionario() {}

    public Funcionario(String nome, String cpf, double salario, LocalDate dataContratacao) {
        this.nome = nome;
        this.cpf = cpf;
        this.salario = salario;
        this.dataContratacao = dataContratacao;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public double getSalario() {
        return salario;
    }

    public void setSalario(double salario) {
        this.salario = salario;
    }

    public LocalDate getDataContratacao() {
        return dataContratacao;
    }

    public void setDataContratacao(LocalDate dataContratacao) {
        this.dataContratacao = dataContratacao;
    }

    @Override
    public String toString() {
        NumberFormat nformatter = new DecimalFormat("R$#0.00");
        DateTimeFormatter dformatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        return "id:              " + id + "\nnome:            " + nome + "\ncpf:             " + cpf
                + "\nsalario:         " + nformatter.format(salario) + "\ndataContratacao: "
                + dataContratacao.format(dformatter) + "\n";
    }

}
