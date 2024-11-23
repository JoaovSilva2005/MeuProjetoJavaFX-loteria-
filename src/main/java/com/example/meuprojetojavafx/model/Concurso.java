package com.example.meuprojetojavafx.model;

import java.time.LocalDate;
import java.util.List;

public class Concurso {
    private String nome;
    private boolean aberto;
    private LocalDate dataSorteio; // Atributo para armazenar a data do sorteio
    private List<Integer> numerosSorteados;

    // Construtor com dois parâmetros (já existente)
    public Concurso(String nome, boolean aberto) {
        this.nome = nome;
        this.aberto = aberto;
    }

    // Novo construtor com três parâmetros
    public Concurso(String nome, LocalDate dataSorteio, boolean aberto) {
        this.nome = nome;
        this.dataSorteio = dataSorteio;
        this.aberto = aberto;
    }

    // Getters e Setters
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public boolean isAberto() {
        return aberto;
    }

    public void setAberto(boolean aberto) {
        this.aberto = aberto;
    }

    public LocalDate getDataSorteio() {
        return dataSorteio;
    }

    public void setDataSorteio(LocalDate dataSorteio) {
        this.dataSorteio = dataSorteio;
    }

    public List<Integer> getNumerosSorteados() {
        return numerosSorteados;
    }

    public void setNumerosSorteados(List<Integer> numerosSorteados) {
        this.numerosSorteados = numerosSorteados;
    }
}
