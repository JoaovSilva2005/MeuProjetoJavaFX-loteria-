package com.example.meuprojetojavafx.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import java.io.IOException;

public class MenuAdministradorController {

    @FXML
    private Button btnCadastrarConcurso;
    @FXML
    private Button btnGerarNumeros;
    @FXML
    private Button btnVisualizarResultados;
    @FXML
    private Button btnVerificarAcumulo;
    @FXML
    private Button btnLogout;
    @FXML
    private Button btnCrudUsuarios;
    @FXML
    private Button btnCrudConcurso;
    @FXML
    private Button btnCrudAposta;

    @FXML
    private void abrirTelaCadastrarConcurso(ActionEvent event) {
        try {
            // Carregar a tela de cadastro de concurso
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/meuprojetojavafx/view/CadastroConcurso.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Cadastrar Concurso");

            // Carregar o conteúdo do FXML
            stage.setScene(new Scene(loader.load()));

            // Exibir a nova tela
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void finalizarConcurso(ActionEvent actionEvent) {
        try {
            // Carregar a tela de Finalizar Concurso
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/meuprojetojavafx/view/FinalizarConcurso.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Finalizar Concurso");

            // Carregar o conteúdo do FXML
            stage.setScene(new Scene(loader.load()));

            // Exibir a nova tela
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void abrirTelaExibirResultados(ActionEvent event) {
        try {
            // Carregar a tela de Exibir Resultados
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/meuprojetojavafx/view/ResultadosView.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Exibir Resultados");

            // Carregar o conteúdo do FXML
            stage.setScene(new Scene(loader.load()));

            // Exibir a nova tela
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void abrirTelaVisualizarResultados(ActionEvent event) {
        // Código para abrir a tela de Visualizar Resultados
        System.out.println("Abrindo tela de Visualizar Resultados");
    }

    @FXML
    private void abrirTelaVerificarAcumulo(ActionEvent event) {
        // Código para abrir a tela de Verificar Acúmulo do Concurso
        System.out.println("Abrindo tela de Verificar Acúmulo");
    }

    @FXML
    private void logout(ActionEvent event) {
        try {
            // Carrega o arquivo FXML da tela de login
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/meuprojetojavafx/view/Login.fxml"));
            Scene scene = new Scene(loader.load());

            // Obtém o estágio atual (janela)
            Stage stage = (Stage) btnLogout.getScene().getWindow();

            // Define a nova cena (tela de login)
            stage.setScene(scene);
            stage.setTitle("Tela de Login");
            stage.show();
        } catch (IOException e) {
            System.err.println("Erro ao carregar a tela de login: " + e.getMessage());
        }
    }

    // Abrir tela CRUD Usuários
    @FXML
    private void abrirTelaCrudUsuarios(ActionEvent event) {
        try {
            // Carregar a tela de CRUD Usuários
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/meuprojetojavafx/view/CrudUsuarios.fxml"));
            Stage stage = new Stage();
            stage.setTitle("CRUD Usuários");

            // Carregar o conteúdo do FXML
            stage.setScene(new Scene(loader.load()));

            // Exibir a nova tela
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Abrir tela CRUD Concurso
    @FXML
    private void abrirTelaCrudConcurso(ActionEvent event) {
        try {
            // Carregar a tela de CRUD Concurso
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/meuprojetojavafx/view/CrudConcurso.fxml"));
            Stage stage = new Stage();
            stage.setTitle("CRUD Concurso");

            // Carregar o conteúdo do FXML
            stage.setScene(new Scene(loader.load()));

            // Exibir a nova tela
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Abrir tela CRUD Aposta
    @FXML
    private void abrirTelaCrudAposta(ActionEvent event) {
        try {
            // Carregar a tela de CRUD Aposta
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/meuprojetojavafx/view/CrudApostasAdmin.fxml"));
            Stage stage = new Stage();
            stage.setTitle("CRUD Apostas");

            // Carregar o conteúdo do FXML
            stage.setScene(new Scene(loader.load()));

            // Exibir a nova tela
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
