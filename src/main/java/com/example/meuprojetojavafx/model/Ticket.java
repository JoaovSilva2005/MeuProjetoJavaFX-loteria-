package com.example.meuprojetojavafx.model;

import java.util.HashSet;
import java.util.Set;

public class Ticket {

    private String usuario;
    private String concurso;
    private Set<Integer> numerosApostados;
    private double valorAposta;

    // Tabela de preços com base na quantidade de números
    private static final double[] PRECOS = {3.00, 48.00, 408.00, 2448.00, 11628.00, 46512.00};

    /**
     * Construtor para criação de um novo ticket.
     *
     * @param usuario          o nome do usuário
     * @param concurso         o concurso associado
     * @param numerosApostados os números apostados
     */
    public Ticket(String usuario, String concurso, Set<Integer> numerosApostados) {
        // Validação dos números da aposta
        validarNumerosApostados(numerosApostados);

        this.usuario = usuario;
        this.concurso = concurso;
        this.numerosApostados = new HashSet<>(numerosApostados);
        this.valorAposta = calcularValorAposta(numerosApostados.size());
    }

    /**
     * Construtor para leitura de tickets salvos.
     *
     * @param usuario          o nome do usuário
     * @param concurso         o concurso associado
     * @param numerosApostados os números apostados
     * @param valorAposta      o valor da aposta
     */
    public Ticket(String usuario, String concurso, Set<Integer> numerosApostados, double valorAposta) {
        validarNumerosApostados(numerosApostados);

        this.usuario = usuario;
        this.concurso = concurso;
        this.numerosApostados = new HashSet<>(numerosApostados);
        this.valorAposta = valorAposta;
    }

    // Validação dos números apostados
    private void validarNumerosApostados(Set<Integer> numerosApostados) {
        int quantidadeNumeros = numerosApostados.size();

        if (quantidadeNumeros < 15 || quantidadeNumeros > 20) {
            throw new IllegalArgumentException("A aposta deve conter entre 15 e 20 números.");
        }
        if (numerosApostados.stream().anyMatch(num -> num < 1 || num > 25)) {
            throw new IllegalArgumentException("Os números da aposta devem estar entre 1 e 25.");
        }
    }

    // Método para calcular o valor da aposta com base na tabela de preços
    private double calcularValorAposta(int quantidadeNumeros) {
        return PRECOS[quantidadeNumeros - 15];
    }

    // Getters
    public String getUsuario() {
        return usuario;
    }

    public String getConcurso() {
        return concurso;
    }

    public Set<Integer> getNumerosApostados() {
        return new HashSet<>(numerosApostados); // Retorna uma cópia para evitar alterações externas
    }

    public double getValorAposta() {
        return valorAposta;
    }

    // Sobrescrita do método toString
    @Override
    public String toString() {
        return usuario + "|" + concurso + "|" + numerosApostados + "|" + valorAposta;
    }

    // Sobrescrita dos métodos equals e hashCode
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        Ticket ticket = (Ticket) obj;
        return usuario.equals(ticket.usuario) &&
                concurso.equals(ticket.concurso) &&
                numerosApostados.equals(ticket.numerosApostados);
    }

    @Override
    public int hashCode() {
        int result = usuario.hashCode();
        result = 31 * result + concurso.hashCode();
        result = 31 * result + numerosApostados.hashCode();
        return result;
    }
}
