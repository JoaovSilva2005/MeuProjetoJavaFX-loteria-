package com.example.meuprojetojavafx.controller;

import com.example.meuprojetojavafx.model.Ticket;
import com.example.meuprojetojavafx.util.TicketUtils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.scene.control.Alert.AlertType;

import java.util.Optional;

public class ApostasAdminController {

    @FXML
    private ListView<String> listViewApostas;

    private ObservableList<Ticket> tickets; // Lista de tickets carregados do arquivo

    @FXML
    public void initialize() {
        carregarApostas();
    }

    // Método para carregar todas as apostas
    private void carregarApostas() {
        tickets = FXCollections.observableArrayList(TicketUtils.carregarTicketsDoArquivo());
        listViewApostas.setItems(FXCollections.observableArrayList(tickets.stream()
                .map(Ticket::toString)
                .toList())); // Exibe as apostas no ListView
    }

    // Método para criar uma nova aposta
    @FXML
    private void criarAposta() {
        showInfo("Funcionalidade de criação de aposta em desenvolvimento!");
        // Aqui, você pode abrir uma nova janela para receber os dados da nova aposta.
    }

    // Método para editar a aposta selecionada
    @FXML
    private void editarAposta() {
        Ticket ticketSelecionado = obterApostaSelecionada();
        if (ticketSelecionado == null) {
            showError("Selecione uma aposta para editar.");
            return;
        }

        showInfo("Funcionalidade de edição de aposta em desenvolvimento!");
        // Aqui, você pode abrir uma nova janela para editar os dados da aposta selecionada.
    }

    // Método para excluir a aposta selecionada
    @FXML
    private void excluirAposta() {
        Ticket ticketSelecionado = obterApostaSelecionada();
        if (ticketSelecionado == null) {
            showError("Selecione uma aposta para excluir.");
            return;
        }

        // Confirmação da exclusão
        Alert confirmacao = new Alert(AlertType.CONFIRMATION);
        confirmacao.setTitle("Confirmar Exclusão");
        confirmacao.setHeaderText("Excluir Aposta");
        confirmacao.setContentText("Você tem certeza que deseja excluir esta aposta?");
        Optional<ButtonType> resultado = confirmacao.showAndWait();

        if (resultado.isPresent() && resultado.get() == ButtonType.OK) {
            // Remove o ticket do arquivo
            TicketUtils.removerTicketDoArquivo(ticketSelecionado);
            carregarApostas(); // Atualiza a lista de apostas
            showInfo("Aposta excluída com sucesso!");
        }
    }

    // Método auxiliar para obter a aposta selecionada
    private Ticket obterApostaSelecionada() {
        int indexSelecionado = listViewApostas.getSelectionModel().getSelectedIndex();
        if (indexSelecionado >= 0) {
            return tickets.get(indexSelecionado);
        }
        return null;
    }

    // Métodos utilitários para exibir mensagens
    private void showError(String mensagem) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Erro");
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }

    private void showInfo(String mensagem) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Informação");
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }
}
