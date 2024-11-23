package com.example.meuprojetojavafx.util;

import com.example.meuprojetojavafx.model.Ticket;
import com.example.meuprojetojavafx.model.Usuario;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.HashSet;

public class TicketUtils {

    private static final String ARQUIVO_APOSTAS = "apostas.txt"; // Caminho do arquivo

    // Método para salvar a aposta em arquivo
    public static void salvarTicketEmArquivo(Set<Integer> numerosApostados, String concurso) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ARQUIVO_APOSTAS, true))) {
            // Obtendo o nome do usuário logado
            String usuarioLogado = UsuarioLogado.getUsuarioLogado().getNome();

            // Criando o ticket com os dados fornecidos
            Ticket ticket = new Ticket(usuarioLogado, concurso, numerosApostados);

            // Salvando a aposta no formato: usuario|concurso|numerosApostados|valorAposta
            writer.write(ticket.toString());
            writer.newLine(); // Nova linha para próxima aposta
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void removerTicketDoArquivo(Ticket ticketToRemove) {
        // Carregar todas as apostas do arquivo
        List<Ticket> tickets = carregarTicketsDoArquivo();

        // Filtrar a aposta que deve ser removida (excluindo-a da lista)
        tickets.removeIf(ticket -> ticket.equals(ticketToRemove));

        // Reescrever o arquivo com a lista atualizada
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ARQUIVO_APOSTAS))) {
            for (Ticket ticket : tickets) {
                writer.write(ticket.toString());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    // Método para carregar as apostas de arquivo
    public static List<Ticket> carregarTicketsDoArquivo() {
        List<Ticket> tickets = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(ARQUIVO_APOSTAS))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] partes = line.split("\\|"); // Agora usando o pipe como delimitador

                if (partes.length == 4) {  // Verifique se temos os 4 campos
                    String usuario = partes[0];
                    String concurso = partes[1];

                    // Remove os colchetes da string de números e converte para Set<Integer>
                    String numerosStr = partes[2].replace("[", "").replace("]", "");
                    String[] numerosArray = numerosStr.split(", ");
                    Set<Integer> numerosApostados = new HashSet<>();
                    for (String numero : numerosArray) {
                        numerosApostados.add(Integer.parseInt(numero));
                    }

                    // Valor da aposta
                    double valorAposta = Double.parseDouble(partes[3]);

                    // Cria o ticket a partir dos dados lidos
                    tickets.add(new Ticket(usuario, concurso, numerosApostados, valorAposta));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return tickets;
    }
}
