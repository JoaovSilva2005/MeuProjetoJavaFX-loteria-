package com.example.meuprojetojavafx.controller;

import com.example.meuprojetojavafx.model.Concurso;
import com.example.meuprojetojavafx.util.ConcursoUtils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.time.LocalDate;

public class CrudConcursoController {

    @FXML
    private TableView<Concurso> tableViewConcursos;
    @FXML
    private TableColumn<Concurso, String> columnNome;
    @FXML
    private TableColumn<Concurso, LocalDate> columnDataSorteio;

    @FXML
    private TextField campoNome;
    @FXML
    private DatePicker campoDataSorteio;
    @FXML
    private Button botaoAdicionar;

    private ObservableList<Concurso> concursos;

    @FXML
    public void initialize() {
        // Carregar os concursos e popular a TableView
        concursos = FXCollections.observableArrayList(ConcursoUtils.carregarConcursos());

        columnNome.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getNome()));
        columnDataSorteio.setCellValueFactory(cellData -> new javafx.beans.property.SimpleObjectProperty<>(cellData.getValue().getDataSorteio()));

        tableViewConcursos.setItems(concursos);
    }

    // Método para adicionar um novo concurso
    @FXML
    private void adicionarConcurso() {
        String nome = campoNome.getText();
        LocalDate dataSorteio = campoDataSorteio.getValue();

        if (nome.isEmpty() || dataSorteio == null) {
            showAlert(Alert.AlertType.ERROR, "Campos inválidos", "Preencha todos os campos.");
            return;
        }

        // Verificar se o nome já está cadastrado
        if (ConcursoUtils.isNomeDuplicado(nome)) {
            showAlert(Alert.AlertType.WARNING, "Nome duplicado", "Já existe um concurso com este nome.");
            return;
        }

        // Criar um novo concurso (passando 'true' para indicar que o concurso está aberto)
        Concurso novoConcurso = new Concurso(nome, dataSorteio, true);  // 'true' indica que o concurso está aberto

        // Salvar no arquivo
        ConcursoUtils.salvarConcursoEmArquivo(novoConcurso);

        // Adicionar à lista e atualizar a TableView
        concursos.add(novoConcurso);

        showAlert(Alert.AlertType.INFORMATION, "Concurso adicionado", "O concurso foi adicionado com sucesso.");

        // Limpar os campos
        limparCampos();
    }

    private void showAlert(Alert.AlertType tipo, String titulo, String mensagem) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }

    private void limparCampos() {
        campoNome.clear();
        campoDataSorteio.setValue(null);
    }

    // Método para editar o concurso
    @FXML
    public void editarConcurso(ActionEvent actionEvent) {
        Concurso concursoSelecionado = tableViewConcursos.getSelectionModel().getSelectedItem();

        if (concursoSelecionado == null) {
            showAlert(Alert.AlertType.WARNING, "Seleção inválida", "Selecione um concurso para editar.");
            return;
        }

        campoNome.setText(concursoSelecionado.getNome());
        campoDataSorteio.setValue(concursoSelecionado.getDataSorteio());

        botaoAdicionar.setText("Salvar Alterações");
        botaoAdicionar.setOnAction(event -> salvarAlteracoes(concursoSelecionado));
    }

    private void salvarAlteracoes(Concurso concursoSelecionado) {
        concursoSelecionado.setNome(campoNome.getText());
        concursoSelecionado.setDataSorteio(campoDataSorteio.getValue());

        ConcursoUtils.atualizarConcursoNoArquivo(concursoSelecionado);

        tableViewConcursos.refresh();

        showAlert(Alert.AlertType.INFORMATION, "Alterações salvas", "As alterações foram salvas com sucesso.");

        limparCampos();
        botaoAdicionar.setText("Adicionar Concurso");
        botaoAdicionar.setOnAction(event -> adicionarConcurso());
    }

    // Método para excluir o concurso
    @FXML
    public void excluirConcurso(ActionEvent actionEvent) {
        Concurso concursoSelecionado = tableViewConcursos.getSelectionModel().getSelectedItem();

        if (concursoSelecionado == null) {
            showAlert(Alert.AlertType.WARNING, "Seleção inválida", "Selecione um concurso para excluir.");
            return;
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmar Exclusão");
        alert.setHeaderText("Tem certeza que deseja excluir este concurso?");
        alert.setContentText("Esta ação não pode ser desfeita.");

        if (alert.showAndWait().get() == ButtonType.OK) {
            concursos.remove(concursoSelecionado);
            // Passando o nome do concurso ao invés do objeto
            ConcursoUtils.removerConcursoDoArquivo(concursoSelecionado.getNome());
            showAlert(Alert.AlertType.INFORMATION, "Concurso excluído", "O concurso foi excluído com sucesso.");
        }
    }

}
