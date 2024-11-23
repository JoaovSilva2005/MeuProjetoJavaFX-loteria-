package com.example.meuprojetojavafx.util;

import com.example.meuprojetojavafx.model.Ticket;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class ComparadorDeApostas {

    private static final Map<Integer, Double> TABELA_PREMIACAO_FIXA = Map.of(
            11, 6.00,
            12, 12.00,
            13, 30.00
    );

    public static String verificarVencedores(String nomeConcurso) {
        List<Ticket> tickets = TicketUtils.carregarTicketsDoArquivo();
        Map<String, List<Integer>> resultados = carregarResultadosDoArquivo();

        if (!resultados.containsKey(nomeConcurso)) {
            System.out.println("Nenhum resultado encontrado para o concurso: " + nomeConcurso);
            return nomeConcurso;
        }

        List<Integer> numerosSorteados = resultados.get(nomeConcurso);
        Collections.sort(numerosSorteados);
        System.out.println("Números sorteados: " + numerosSorteados);

        List<Ticket> apostasDoConcurso = tickets.stream()
                .filter(ticket -> ticket.getConcurso().equals(nomeConcurso))
                .collect(Collectors.toList());

        double arrecadacao = calcularArrecadacao(apostasDoConcurso);
        double premioBruto = arrecadacao * 0.4335;
        System.out.printf("Arrecadação total: R$ %.2f%n", arrecadacao);
        System.out.printf("Premiação bruta: R$ %.2f%n", premioBruto);

        double premioFixo = calcularPremiosFixos(apostasDoConcurso, numerosSorteados);

        double premioRestante = premioBruto - premioFixo;

        distribuirPremiosVariaveis(apostasDoConcurso, numerosSorteados, premioRestante);

        return nomeConcurso;
    }

    private static double calcularArrecadacao(List<Ticket> tickets) {
        return tickets.stream()
                .mapToDouble(Ticket::getValorAposta)
                .sum();
    }

    private static double calcularPremiosFixos(List<Ticket> tickets, List<Integer> numerosSorteados) {
        double totalPremioFixo = 0;

        for (Ticket ticket : tickets) {
            long acertos = ticket.getNumerosApostados().stream()
                    .filter(numerosSorteados::contains)
                    .count();

            if (TABELA_PREMIACAO_FIXA.containsKey((int) acertos)) {
                double premio = TABELA_PREMIACAO_FIXA.get((int) acertos);
                totalPremioFixo += premio;
                System.out.printf("Usuário %s acertou %d números e ganhou R$ %.2f%n",
                        ticket.getUsuario(), acertos, premio);
            }
        }

        return totalPremioFixo;
    }

    private static void distribuirPremiosVariaveis(List<Ticket> tickets, List<Integer> numerosSorteados, double premioRestante) {
        List<Ticket> ganhadores15 = tickets.stream()
                .filter(ticket -> ticket.getNumerosApostados().stream().filter(numerosSorteados::contains).count() == 15)
                .collect(Collectors.toList());

        List<Ticket> ganhadores14 = tickets.stream()
                .filter(ticket -> ticket.getNumerosApostados().stream().filter(numerosSorteados::contains).count() == 14)
                .collect(Collectors.toList());

        if (!ganhadores15.isEmpty()) {
            double premioPorGanhador15 = (premioRestante * 0.68) / ganhadores15.size();
            ganhadores15.forEach(ticket -> System.out.printf("Usuário %s acertou 15 números e ganhou R$ %.2f%n",
                    ticket.getUsuario(), premioPorGanhador15));
        }

        if (!ganhadores14.isEmpty()) {
            double premioPorGanhador14 = (premioRestante * 0.32) / ganhadores14.size();
            ganhadores14.forEach(ticket -> System.out.printf("Usuário %s acertou 14 números e ganhou R$ %.2f%n",
                    ticket.getUsuario(), premioPorGanhador14));
        }
    }

    private static Map<String, List<Integer>> carregarResultadosDoArquivo() {
        Map<String, List<Integer>> resultados = new HashMap<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("resultado_sorteio.txt"))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                if (linha.startsWith("Concurso:")) {
                    String nomeConcurso = linha.split(": ")[1];
                    linha = reader.readLine();
                    if (linha != null && linha.startsWith("Resultado do sorteio:")) {
                        String numerosStr = linha.split(": ")[1];
                        List<Integer> numeros = Arrays.stream(numerosStr.replace("[", "").replace("]", "").split(", "))
                                .map(Integer::parseInt)
                                .collect(Collectors.toList());
                        Collections.sort(numeros);
                        resultados.put(nomeConcurso, numeros);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return resultados;
    }

    public static void main(String[] args) {
        verificarVencedores("Concurso1");
    }
}
