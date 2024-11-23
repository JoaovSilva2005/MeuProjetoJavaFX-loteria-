package com.example.meuprojetojavafx.util;

import com.example.meuprojetojavafx.model.Concurso;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

public class ConcursoUtils {

    private static final String CAMINHO_ARQUIVO = "concursos.txt";
    private static final String CAMINHO_RESULTADO_ARQUIVO = "resultado_sorteio.txt"; // Arquivo para salvar o resultado do sorteio

    // Carregar os nomes dos concursos
    public static List<String> carregarConcursosNomes() {
        List<String> nomesConcursos = new ArrayList<>();
        List<Concurso> concursos = carregarConcursos(); // Carregar todos os concursos

        // Extrair os nomes dos concursos
        for (Concurso concurso : concursos) {
            nomesConcursos.add(concurso.getNome());
        }

        return nomesConcursos;
    }

    // Carregar concursos do arquivo
    public static List<Concurso> carregarConcursos() {
        List<Concurso> concursos = new ArrayList<>();
        File arquivo = new File(CAMINHO_ARQUIVO);

        if (!arquivo.exists()) {
            return concursos;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(arquivo))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                String[] partes = linha.split(",");
                if (partes.length == 3) {  // Verifica se a linha possui 3 elementos
                    String nome = partes[0];
                    // Valida a data antes de usá-la
                    LocalDate dataSorteio = null;
                    try {
                        if (partes[1] != null && !partes[1].trim().isEmpty()) {
                            dataSorteio = LocalDate.parse(partes[1]);
                        }
                    } catch (DateTimeParseException e) {
                        System.err.println("Data inválida para o concurso: " + partes[1]);
                        continue;
                    }
                    boolean aberto = Boolean.parseBoolean(partes[2]);
                    if (nome != null && dataSorteio != null) {
                        concursos.add(new Concurso(nome, dataSorteio, aberto));
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Erro ao carregar concursos: " + e.getMessage());
        }

        return concursos;
    }

    // Salvar um novo concurso no arquivo
    public static void salvarConcursoEmArquivo(Concurso concurso) {
        if (concurso == null || concurso.getNome() == null || concurso.getDataSorteio() == null) {
            System.err.println("Erro: concurso ou dados inválidos.");
            return;
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(CAMINHO_ARQUIVO, true))) {
            writer.write(concurso.getNome() + "," + concurso.getDataSorteio() + "," + concurso.isAberto());
            writer.newLine();
        } catch (IOException e) {
            System.err.println("Erro ao salvar concurso: " + e.getMessage());
        }
    }

    // Atualizar um concurso no arquivo
    public static void atualizarConcursoNoArquivo(Concurso concurso) {
        List<Concurso> concursos = carregarConcursos();
        concursos.removeIf(c -> c.getNome().equals(concurso.getNome()));
        concursos.add(concurso);
        salvarTodosConcursos(concursos);
    }

    // Salvar todos os concursos de volta ao arquivo
    private static void salvarTodosConcursos(List<Concurso> concursos) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(CAMINHO_ARQUIVO))) {
            for (Concurso concurso : concursos) {
                writer.write(concurso.getNome() + "," + concurso.getDataSorteio() + "," + concurso.isAberto());
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Erro ao salvar todos os concursos: " + e.getMessage());
        }
    }

    // Salvar o resultado do sorteio em um arquivo separado
    public static void salvarResultadoSorteioEmArquivo(String resultado) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(CAMINHO_RESULTADO_ARQUIVO, true))) {
            writer.write(resultado);
            writer.newLine();  // Adiciona uma nova linha após o resultado
        } catch (IOException e) {
            System.err.println("Erro ao salvar o resultado do sorteio: " + e.getMessage());
        }
    }

    // Método para verificar se o nome do concurso é duplicado
    public static boolean isNomeDuplicado(String nome) {
        return carregarConcursos().stream().anyMatch(c -> c.getNome().equals(nome));
    }

    public static void removerConcursoDoArquivo(String nome) {
    }
}
