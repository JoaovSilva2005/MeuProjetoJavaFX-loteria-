package com.example.meuprojetojavafx.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.ArrayList;

public class Sorteio {

    private static final String CAMINHO_RESULTADOS = "resultado_sorteio.txt";  // Caminho do arquivo de resultados
    private static final String CAMINHO_DETALHES_ARQUIVO = "detalhes_sorteio.txt";  // Novo arquivo para detalhes do sorteio

    // Método para gerar os números do sorteio
    public List<Integer> realizarSorteio() {
        List<Integer> numeros = new ArrayList<>();
        for (int i = 1; i <= 25; i++) {  // Lista de números de 1 a 25
            numeros.add(i);
        }
        Collections.shuffle(numeros);  // Embaralha a lista
        return numeros.subList(0, 15);  // Pega os 15 primeiros números sorteados
    }

    // Método para salvar os números sorteados em um arquivo de resultados
    public void salvarResultado(String nomeConcurso, List<Integer> numerosSorteados) {
        try {
            // Ordena os números sorteados
            Collections.sort(numerosSorteados);

            // Definir o arquivo onde será salvo o resultado
            File arquivo = new File(CAMINHO_RESULTADOS);

            // Se o arquivo não existir, cria um novo arquivo
            if (!arquivo.exists()) {
                arquivo.createNewFile();
            }

            // Escrever os números no arquivo, junto com o nome do concurso
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(arquivo, true))) {  // 'true' para adicionar ao final do arquivo
                writer.write("Concurso: " + nomeConcurso);  // Escrever o nome do concurso
                writer.newLine();  // Quebra de linha
                writer.write("Resultado do sorteio: " + formatarNumeros(numerosSorteados));  // Escrever os números sorteados
                writer.newLine();  // Quebra de linha
            }

            System.out.println("Resultado do sorteio salvo com sucesso!");
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Erro ao salvar o resultado do sorteio.");
        }
    }

    // Método para salvar os detalhes do sorteio em um arquivo de detalhes
    public void salvarDetalhesSorteio(String nomeConcurso, List<Integer> numerosSorteados, double premioTotal, double valorArrecadado, List<String> ganhadores) {
        try {
            // Ordena os números sorteados
            Collections.sort(numerosSorteados);

            // Definir o arquivo onde serão salvos os detalhes do sorteio
            File arquivoDetalhes = new File(CAMINHO_DETALHES_ARQUIVO);

            // Se o arquivo não existir, cria um novo arquivo
            if (!arquivoDetalhes.exists()) {
                arquivoDetalhes.createNewFile();
            }

            // Escrever os detalhes no arquivo
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(arquivoDetalhes, true))) {  // 'true' para adicionar ao final do arquivo
                writer.write("Concurso: " + nomeConcurso);
                writer.newLine();
                writer.write("Números sorteados: " + formatarNumeros(numerosSorteados));
                writer.newLine();
                writer.write("Valor do prêmio total: R$" + premioTotal);
                writer.newLine();
                writer.write("Valor arrecadado: R$" + valorArrecadado);
                writer.newLine();
                writer.write("Ganhadores: " + String.join("; ", ganhadores));  // Lista de ganhadores
                writer.newLine();
                writer.write("Premiação acumulada: R$" + (premioTotal * 0.25));  // Exemplo de premiação acumulada
                writer.newLine();
                writer.newLine();  // Quebra de linha entre sorteios
            }

            System.out.println("Detalhes do sorteio salvos com sucesso!");
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Erro ao salvar os detalhes do sorteio.");
        }
    }

    // Método auxiliar para formatar os números sorteados
    private String formatarNumeros(List<Integer> numeros) {
        StringBuilder sb = new StringBuilder();
        for (Integer numero : numeros) {
            sb.append(numero).append(", ");
        }
        return sb.length() > 0 ? sb.substring(0, sb.length() - 2) : "";  // Remove a última vírgula
    }

    // Método principal para testar a classe
    public static void main(String[] args) {
        Sorteio sorteio = new Sorteio();

        // Gerando os números sorteados
        List<Integer> numerosSorteados = sorteio.realizarSorteio();

        // Salvar o resultado do sorteio no arquivo de resultados
        sorteio.salvarResultado("Concurso 1", numerosSorteados);

        // Detalhes do sorteio (exemplo)
        double premioTotal = 1000000.00;  // Valor do prêmio
        double valorArrecadado = 2000000.00;  // Valor arrecadado
        List<String> ganhadores = new ArrayList<>();
        ganhadores.add("João - 6 acertos");
        ganhadores.add("Maria - 5 acertos");

        // Salvar os detalhes no arquivo de detalhes
        sorteio.salvarDetalhesSorteio("Concurso 1", numerosSorteados, premioTotal, valorArrecadado, ganhadores);
    }
}
