package com.example.meuprojetojavafx.controller;

import com.example.meuprojetojavafx.model.Usuario;
import com.example.meuprojetojavafx.util.UsuarioUtils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.time.LocalDate;

public class CrudUsuarioController {

    @FXML
    private TableView<Usuario> tableViewUsuarios;
    @FXML
    private TableColumn<Usuario, String> columnUsuario;
    @FXML
    private TableColumn<Usuario, String> columnCpf;
    @FXML
    private TableColumn<Usuario, String> columnEmail;
    @FXML
    private TableColumn<Usuario, LocalDate> columnDataNascimento;

    @FXML
    private TextField campoUsuario;
    @FXML
    private TextField campoCpf;
    @FXML
    private TextField campoEmail;
    @FXML
    private PasswordField campoSenha;
    @FXML
    private DatePicker campoDataNascimento;
    @FXML
    private Button botaoAdicionar; // Declare o botão de adicionar

    private ObservableList<Usuario> usuarios;

    @FXML
    public void initialize() {
        // Carregar os usuários e popular a TableView
        usuarios = FXCollections.observableArrayList(UsuarioUtils.carregarUsuarios());

        columnUsuario.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getNome()));
        columnCpf.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getCpf()));
        columnEmail.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getEmail()));
        columnDataNascimento.setCellValueFactory(cellData -> new javafx.beans.property.SimpleObjectProperty<>(cellData.getValue().getDataNascimento()));

        tableViewUsuarios.setItems(usuarios);
    }

    // Método para adicionar o usuário
    @FXML
    private void adicionarUsuario() {
        String usuario = campoUsuario.getText();
        String cpf = campoCpf.getText();
        String email = campoEmail.getText();
        String senha = campoSenha.getText();
        LocalDate dataNascimento = campoDataNascimento.getValue();

        if (usuario.isEmpty() || cpf.isEmpty() || email.isEmpty() || senha.isEmpty() || dataNascimento == null) {
            showAlert(Alert.AlertType.ERROR, "Campos inválidos", "Preencha todos os campos.");
            return;
        }

        // Verificar se o CPF ou e-mail já estão cadastrados
        if (UsuarioUtils.isCpfDuplicado(cpf)) {
            showAlert(Alert.AlertType.WARNING, "CPF já cadastrado", "Este CPF já está em uso.");
            return;
        }

        if (UsuarioUtils.isEmailDuplicado(email)) {
            showAlert(Alert.AlertType.WARNING, "E-mail já cadastrado", "Este e-mail já está em uso.");
            return;
        }

        // Adicionando um tipo de usuário fictício para este exemplo (ajuste conforme necessário)
        String tipoUsuario = "Comum";  // Exemplo de tipo de usuário

        // Criar um novo usuário
        Usuario novoUsuario = new Usuario(usuario, cpf, email, senha, dataNascimento, tipoUsuario);

        // Salvar no arquivo
        UsuarioUtils.salvarUsuarioEmArquivo(novoUsuario);

        // Adicionar à lista e atualizar a TableView
        usuarios.add(novoUsuario);

        showAlert(Alert.AlertType.INFORMATION, "Usuário adicionado", "O usuário foi adicionado com sucesso.");

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
        campoUsuario.clear();
        campoCpf.clear();
        campoEmail.clear();
        campoSenha.clear();
        campoDataNascimento.setValue(null);
    }

    // Método para editar o usuário
    @FXML
    public void editarUsuario(ActionEvent actionEvent) {
        // Verifica se algum usuário foi selecionado na TableView
        Usuario usuarioSelecionado = tableViewUsuarios.getSelectionModel().getSelectedItem();

        if (usuarioSelecionado == null) {
            showAlert(Alert.AlertType.WARNING, "Seleção inválida", "Selecione um usuário para editar.");
            return;
        }

        // Preenche os campos com os dados do usuário selecionado
        campoUsuario.setText(usuarioSelecionado.getNome());
        campoCpf.setText(usuarioSelecionado.getCpf());
        campoEmail.setText(usuarioSelecionado.getEmail());
        campoSenha.setText(usuarioSelecionado.getSenha());
        campoDataNascimento.setValue(usuarioSelecionado.getDataNascimento());

        // Alterar o botão de adicionar para editar
        botaoAdicionar.setText("Salvar Alterações");

        // Quando o botão de salvar for pressionado, salva as alterações
        botaoAdicionar.setOnAction(event -> salvarAlteracoes(usuarioSelecionado));
    }

    // Método para salvar as alterações após editar
    private void salvarAlteracoes(Usuario usuarioSelecionado) {
        // Atualiza os dados no objeto selecionado
        usuarioSelecionado.setUsuario(campoUsuario.getText());
        usuarioSelecionado.setCpf(campoCpf.getText());
        usuarioSelecionado.setEmail(campoEmail.getText());
        usuarioSelecionado.setSenha(campoSenha.getText());
        usuarioSelecionado.setDataNascimento(campoDataNascimento.getValue());

        // Salva as alterações no arquivo
        UsuarioUtils.atualizarUsuarioNoArquivo(usuarioSelecionado);

        // Atualiza a TableView
        tableViewUsuarios.refresh();

        showAlert(Alert.AlertType.INFORMATION, "Alterações salvas", "As alterações foram salvas com sucesso.");

        // Limpa os campos
        limparCampos();
        botaoAdicionar.setText("Adicionar Usuário"); // Restaura o texto original do botão
    }

    // Método para excluir o usuário
    @FXML
    public void excluirUsuario(ActionEvent actionEvent) {
        // Verifica se algum usuário foi selecionado na TableView
        Usuario usuarioSelecionado = tableViewUsuarios.getSelectionModel().getSelectedItem();

        if (usuarioSelecionado == null) {
            showAlert(Alert.AlertType.WARNING, "Seleção inválida", "Selecione um usuário para excluir.");
            return;
        }

        // Pergunta ao usuário se tem certeza que deseja excluir
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmar Exclusão");
        alert.setHeaderText("Tem certeza que deseja excluir este usuário?");
        alert.setContentText("Esta ação não pode ser desfeita.");

        if (alert.showAndWait().get() == ButtonType.OK) {
            // Remove o usuário da lista
            usuarios.remove(usuarioSelecionado);

            // Exclui o usuário do arquivo
            UsuarioUtils.removerUsuarioDoArquivo(usuarioSelecionado);

            showAlert(Alert.AlertType.INFORMATION, "Usuário excluído", "O usuário foi excluído com sucesso.");
        }
    }
}
