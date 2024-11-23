package com.example.meuprojetojavafx.controller;

import com.example.meuprojetojavafx.model.Concurso;
import com.example.meuprojetojavafx.util.ConcursoUtils;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;

public class CadastroConcursoController {

    @FXML
    private TextField campoNomeConcurso;

    @FXML
    private DatePicker campoDataSorteio;

    @FXML
    private Button botaoCadastrar;

    @FXML
    private Button botaoVoltar;

    @FXML
    private void cadastrarConcurso() {
        System.out.println("Método cadastrarConcurso chamado!");

        // Coleta os dados dos campos
        String nome = campoNomeConcurso.getText();
        LocalDate dataSorteio = campoDataSorteio.getValue();

        // Valida os campos obrigatórios
        if (nome.isEmpty() || dataSorteio == null) {
            showError("Todos os campos são obrigatórios!");
            return;
        }

        // Verifica se já existe um concurso com a mesma data de sorteio
        if (isDataSorteioDuplicada(dataSorteio)) {
            showError("Já existe um concurso com essa data de sorteio.");
            return;
        }

        // Verifica se a data de sorteio não é no passado
        if (dataSorteio.isBefore(LocalDate.now())) {
            showError("A data de sorteio não pode ser no passado.");
            return;
        }

        // Cria o novo concurso com os dados inseridos
        Concurso novoConcurso = new Concurso(nome, dataSorteio, true);  // 'true' indica que o concurso está aberto

        // Salva o concurso em um arquivo
        ConcursoUtils.salvarConcursoEmArquivo(novoConcurso);

        // Mensagem de sucesso
        showSuccess("Concurso cadastrado com sucesso!");

        // Limpa os campos após salvar
        campoNomeConcurso.clear();
        campoDataSorteio.setValue(null);
    }

    // Método para verificar se a data de sorteio já está cadastrada
    private boolean isDataSorteioDuplicada(LocalDate dataSorteio) {
        // Verifica se já existe um concurso com a mesma data
        for (Concurso concurso : ConcursoUtils.carregarConcursos()) {
            if (concurso.getDataSorteio().equals(dataSorteio)) {
                return true;
            }
        }
        return false;
    }

    // Método para exibir mensagens de erro
    private void showError(String mensagem) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erro");
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }

    // Método para exibir mensagens de sucesso
    private void showSuccess(String mensagem) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Sucesso");
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }

    @FXML
    private void voltarParaTelaPrincipal() {
        try {
            // Fecha o stage atual
            Stage stage = (Stage) botaoVoltar.getScene().getWindow();
            stage.close();  // Fecha o stage (janela) atual

        } catch (Exception e) {
            System.err.println("Erro ao fechar a janela: " + e.getMessage());
        }
    }
}
