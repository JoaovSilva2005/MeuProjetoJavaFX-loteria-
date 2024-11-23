package com.example.meuprojetojavafx.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import java.io.IOException;

public class MainMenuController {

    @FXML
    private Button btnRealizarAposta;
    @FXML
    private Button btnVisualizarApostas;
    @FXML
    private Button btnVisualizarResultados;
    @FXML
    private Button btnSair;

    @FXML
    private void abrirTelaRealizarAposta(ActionEvent event) {
        try {
            // Carregar a tela CadastroAposta.fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/meuprojetojavafx/view/CadastroAposta.fxml"));
            Scene scene = new Scene(loader.load());

            // Criar um novo stage (janela) para a tela de Realizar Aposta
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("Cadastro de Aposta");
            stage.show();

            // Fechar a tela principal
            Stage currentStage = (Stage) btnRealizarAposta.getScene().getWindow();
            currentStage.close();

        } catch (IOException e) {
            System.err.println("Erro ao abrir a tela de Realizar Aposta: " + e.getMessage());
        }
    }

    @FXML
    private void abrirTelaVisualizarApostas(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/meuprojetojavafx/view/MinhasApostas.fxml"));
            Scene scene = new Scene(loader.load());
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("Minhas Apostas");
            stage.show();

            Stage currentStage = (Stage) btnVisualizarApostas.getScene().getWindow();
            currentStage.close();
        } catch (IOException e) {
            System.err.println("Erro ao abrir a tela de Minhas Apostas: " + e.getMessage());
            e.printStackTrace();  // Adicione esta linha para imprimir mais detalhes
        }
    }  // Aqui fechamos o método corretamente!


    @FXML
    private void abrirTelaVisualizarResultados(ActionEvent event) {
    }


    @FXML
    private void sair(ActionEvent event) {
        try {
            // Carrega o arquivo FXML da tela de login
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/meuprojetojavafx/view/Login.fxml"));
            Scene scene = new Scene(loader.load());

            // Obtém o estágio atual (janela)
            Stage stage = (Stage) btnSair.getScene().getWindow();

            // Define a nova cena (tela de login)
            stage.setScene(scene);
            stage.setTitle("Tela de Login");
            stage.show();
        } catch (IOException e) {
            System.err.println("Erro ao carregar a tela de login: " + e.getMessage());
        }
    }
}
