package com.example.meuprojetojavafx.controller;

import com.example.meuprojetojavafx.model.Usuario;
import com.example.meuprojetojavafx.util.UsuarioLogado;
import com.example.meuprojetojavafx.util.UsuarioUtils;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {

    @FXML
    private PasswordField campoSenha;

    @FXML
    private Button botaoLogin;

    @FXML
    private Button campoCriarRegistro;

    @FXML
    private TextField campoUsuario;

    @FXML
    public void initialize() {
        // Configura a ação do botão de login
        botaoLogin.setOnAction(event -> realizarLogin());

        // Configura a ação para redirecionar para a tela de registro
        campoCriarRegistro.setOnAction(event -> carregarTelaRegistro());
    }

    private void realizarLogin() {
        // Coleta os dados dos campos de login
        String usuario = campoUsuario.getText();
        String senha = campoSenha.getText();

        // Verifica se o usuário e a senha foram preenchidos
        if (usuario.isEmpty() || senha.isEmpty()) {
            exibirMensagemErro("Por favor, preencha ambos os campos de usuário e senha.");
            return;
        }

        // Verifica se o usuário existe e a senha está correta
        Usuario usuarioEncontrado = UsuarioUtils.buscarUsuarioPorNome(usuario);
        if (usuarioEncontrado == null) {
            exibirMensagemErro("Usuário não encontrado.");
            return;
        }

        // Verifica se a senha está correta
        if (!usuarioEncontrado.getSenha().equals(senha)) {
            exibirMensagemErro("Senha incorreta.");
            return;
        }

        // Define o usuário logado
        UsuarioLogado.setUsuarioLogado(usuarioEncontrado);

        // Cria um booleano para verificar se o usuário é admin
        boolean isAdmin = "admin".equalsIgnoreCase(usuarioEncontrado.getTipoUsuario());

        if (isAdmin) {
            exibirMensagemSucesso("Login bem-sucedido como Admin!");
            // Carrega a tela de administração
            carregarTela("/com/example/meuprojetojavafx/view/MenuAdministrador.fxml", "Administração");
        } else {
            exibirMensagemSucesso("Login bem-sucedido como Usuário!");
            // Carrega a tela comum de usuário
            carregarTela("/com/example/meuprojetojavafx/view/MainMenu.fxml", "Tela Principal");
        }
    }



    private void exibirMensagemErro(String mensagem) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erro");
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }

    private void exibirMensagemSucesso(String mensagem) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Sucesso");
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }

    private void carregarTelaRegistro() {
        carregarTela("/com/example/meuprojetojavafx/view/Registro.fxml", "Tela de Registro");
    }

    private void carregarTela(String caminhoFXML, String titulo) {
        try {
            // Carrega o arquivo FXML da tela
            FXMLLoader loader = new FXMLLoader(getClass().getResource(caminhoFXML));
            Scene scene = new Scene(loader.load());

            // Obtém o estágio atual (janela)
            Stage stage = (Stage) botaoLogin.getScene().getWindow();

            // Define a nova cena
            stage.setScene(scene);
            stage.setTitle(titulo);
            stage.show();
        } catch (IOException e) {
            exibirMensagemErro("Erro ao carregar a tela: " + e.getMessage());
        }
    }
}
