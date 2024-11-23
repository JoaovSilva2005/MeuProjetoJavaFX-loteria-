package com.example.meuprojetojavafx.controller;

import com.example.meuprojetojavafx.model.Usuario;
import com.example.meuprojetojavafx.util.UsuarioUtils;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.time.Period;

public class RegistroController {

    @FXML
    private TextField campoUsuario;

    @FXML
    private PasswordField campoSenha;

    @FXML
    private Button botaoCadastro;

    @FXML
    private Button botaoPossuiCadastro;

    @FXML
    private PasswordField campoConfSenha;

    @FXML
    private TextField campoCPF;

    @FXML
    private DatePicker campoNascimento;

    @FXML
    private TextField campoEmail;

    @FXML
    public void initialize() {
        // Ação do botão "Cadastrar"
        botaoCadastro.setOnAction(event -> registrarUsuario());

        // Ação do botão "Já tem conta? Logar"
        botaoPossuiCadastro.setOnAction(event -> carregarTelaLogin());
    }

    private void registrarUsuario() {
        // Coleta os dados dos campos
        String usuario = campoUsuario.getText();
        String senha = campoSenha.getText();
        String confSenha = campoConfSenha.getText();
        String cpf = campoCPF.getText();
        String email = campoEmail.getText();
        LocalDate dataNascimento = campoNascimento.getValue(); // Este já é um LocalDate

        // Defina o tipo de usuário
        String tipoUsuario = "cliente"; // Exemplo, se for fixo

        // Validação simples: Verifica se algum campo está vazio
        if (usuario.isEmpty() || senha.isEmpty() || confSenha.isEmpty() || cpf.isEmpty() || email.isEmpty() || dataNascimento == null) {
            showError("Todos os campos devem ser preenchidos!");
            return;
        }

        // Verifica se algum campo contém caracteres inválidos (como ';;;')
        if (contémCaracteresInvalidos(usuario, senha, cpf, email)) {
            showError("Campos não podem conter caracteres inválidos (como ';;;').");
            return;
        }

        // Verifica se as senhas coincidem
        if (!senha.equals(confSenha)) {
            showError("As senhas não coincidem!");
            return;
        }

        // Verifica se o CPF já está cadastrado
        if (UsuarioUtils.isCpfDuplicado(cpf)) {
            showError("CPF já cadastrado!");
            return;
        }

        // Verifica se o e-mail já está cadastrado
        if (UsuarioUtils.isEmailDuplicado(email)) {
            showError("E-mail já cadastrado!");
            return;
        }

        // Verifica se o usuário é maior de idade
        if (!isMaiorDeIdade(dataNascimento)) {
            showError("Você precisa ser maior de idade para se cadastrar.");
            return;
        }

        // Cria o objeto Usuario com todos os parâmetros necessários
        Usuario novoUsuario = new Usuario(usuario, cpf, email, senha, dataNascimento, tipoUsuario);

        // Salva o usuário no arquivo
        UsuarioUtils.salvarUsuarioEmArquivo(novoUsuario);

        showSuccess("Usuário cadastrado com sucesso!");

        // Carrega a tela de login após o cadastro bem-sucedido
        carregarTelaLogin();
    }

    private boolean contémCaracteresInvalidos(String usuario, String senha, String cpf, String email) {
        // Verifica se os campos contêm caracteres inválidos como ';;;'
        String[] campos = {usuario, senha, cpf, email};
        for (String campo : campos) {
            if (campo.contains(";;;")) {
                return true;
            }
        }
        return false;
    }

    private boolean isMaiorDeIdade(LocalDate dataNascimento) {
        // Calcula a idade com base na data de nascimento
        int idade = Period.between(dataNascimento, LocalDate.now()).getYears();
        return idade >= 18; // Verifica se a idade é maior ou igual a 18
    }

    private void showError(String mensagem) {
        // Exibe uma mensagem de erro para o usuário
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erro");
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }

    private void showSuccess(String mensagem) {
        // Exibe uma mensagem de sucesso para o usuário
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Sucesso");
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }

    private void carregarTelaLogin() {
        try {
            // Carrega o arquivo FXML da tela de login
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/meuprojetojavafx/view/Login.fxml"));
            Scene scene = new Scene(loader.load());

            // Obtém o estágio atual (janela)
            Stage stage = (Stage) botaoCadastro.getScene().getWindow();

            // Define a nova cena (tela de login)
            stage.setScene(scene);
            stage.setTitle("Tela de Login");
            stage.show();
        } catch (IOException e) {
            System.err.println("Erro ao carregar a tela de login: " + e.getMessage());
        }
    }
}
