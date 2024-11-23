package com.example.meuprojetojavafx.controller;

import com.example.meuprojetojavafx.model.Ticket;
import com.example.meuprojetojavafx.util.TicketUtils;
import com.example.meuprojetojavafx.util.UsuarioLogado;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class MinhasApostasController {
    @FXML
    private ListView<String> listViewApostas;

    @FXML
    public void initialize() {
        carregarApostas();
    }

    // Método para carregar as apostas feitas pelo usuário logado
    private void carregarApostas() {
        File file = new File("apostas.txt"); // Supondo que as apostas são salvas em um arquivo texto
        if (file.exists()) {
            List<Ticket> apostas = TicketUtils.carregarTicketsDoArquivo();

            // Filtrar as apostas para mostrar apenas as do usuário logado
            String usuarioLogado = UsuarioLogado.getUsuarioLogado().getNome(); // Obtém o nome do usuário logado
            List<Ticket> apostasUsuario = apostas.stream()
                    .filter(ticket -> ticket.getUsuario().equals(usuarioLogado))
                    .collect(Collectors.toList());

            // Adicionar as apostas filtradas na ListView
            if (!apostasUsuario.isEmpty()) {
                for (Ticket ticket : apostasUsuario) {
                    listViewApostas.getItems().add(ticket.toString());
                }
            } else {
                showError("Você ainda não fez nenhuma aposta.");
            }
        } else {
            showError("Nenhuma aposta encontrada.");
        }
    }

    // Método para exibir mensagens de erro
    private void showError(String mensagem) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Erro");
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }

    // Método para voltar para a tela principal
    @FXML
    private void voltarParaTelaPrincipal() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/meuprojetojavafx/view/MainMenu.fxml"));
            Scene scene = new Scene(loader.load());

            // Obter o stage da janela principal (botão) e mudar a cena
            Stage stage = (Stage) listViewApostas.getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("Tela Principal");
            stage.show();
        } catch (IOException e) {
            System.err.println("Erro ao carregar a tela principal: " + e.getMessage());
        }
    }
}
