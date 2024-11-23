package com.example.meuprojetojavafx.controller;

import com.example.meuprojetojavafx.model.Concurso;
import com.example.meuprojetojavafx.model.Ticket;
import com.example.meuprojetojavafx.util.TicketUtils;
import com.example.meuprojetojavafx.util.UsuarioLogado;
import com.example.meuprojetojavafx.util.ConcursoUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class CadastroApostaController {

    @FXML
    private ComboBox<String> comboBoxConcurso;
    @FXML
    private GridPane gridPaneNumeros;
    @FXML
    private Label labelQuantidadeNumeros;
    @FXML
    private Label labelValorAposta;
    @FXML
    private Button salvarButton;

    private Set<Integer> numerosApostados = new HashSet<>();
    private double valorAposta = 0.0;

    @FXML
    public void initialize() {
        // Carregar os nomes dos concursos do arquivo
        List<String> concursos = ConcursoUtils.carregarConcursosNomes(); // Método para carregar os nomes dos concursos

        // Adiciona os concursos ao ComboBox
        comboBoxConcurso.getItems().addAll(concursos);
        comboBoxConcurso.setValue(concursos.isEmpty() ? null : concursos.get(0)); // Seleciona o primeiro concurso, se houver algum

        // Configura o GridPane para números
        for (int i = 1; i <= 25; i++) {
            CheckBox checkBox = new CheckBox(String.valueOf(i));
            checkBox.setOnAction(event -> atualizarNumerosSelecionados());
            gridPaneNumeros.add(checkBox, (i - 1) % 5, (i - 1) / 5); // Organiza os números em uma grid de 5x5
        }

        // Atualiza a quantidade de números e o valor da aposta inicialmente
        atualizarNumerosSelecionados();
    }

    // Método para atualizar os números selecionados
    private void atualizarNumerosSelecionados() {
        numerosApostados.clear();
        for (javafx.scene.Node node : gridPaneNumeros.getChildren()) {
            if (node instanceof CheckBox) {
                CheckBox checkBox = (CheckBox) node;
                if (checkBox.isSelected()) {
                    numerosApostados.add(Integer.parseInt(checkBox.getText()));
                }
            }
        }

        // Atualiza a quantidade de números
        labelQuantidadeNumeros.setText("Números Selecionados: " + numerosApostados.size());

        // Atualiza o valor da aposta
        valorAposta = calcularValorAposta(numerosApostados.size());
        labelValorAposta.setText("Valor da Aposta: R$ " + String.format("%.2f", valorAposta));
    }

    // Método para calcular o valor da aposta com base na quantidade de números
    private double calcularValorAposta(int quantidadeNumeros) {
        switch (quantidadeNumeros) {
            case 15:
                return 3.00;
            case 16:
                return 48.00;
            case 17:
                return 408.00;
            case 18:
                return 2448.00;
            case 19:
                return 11628.00;
            case 20:
                return 46512.00;
            default:
                return 0.0; // Não permite apostas inválidas
        }
    }

    // Método para verificar se a aposta já foi feita pelo usuário logado
    private boolean verificarApostaDuplicada(Set<Integer> numerosApostados, String concurso) {
        // Carrega todas as apostas do arquivo
        List<Ticket> ticketsExistentes = TicketUtils.carregarTicketsDoArquivo();

        // Obtém o nome do usuário logado
        String usuarioLogado = UsuarioLogado.getUsuarioLogado().getNome();

        // Verifica se a aposta já foi feita pelo mesmo usuário
        for (Ticket ticket : ticketsExistentes) {
            // Verifica se o concurso e os números são os mesmos
            if (ticket.getConcurso().equals(concurso) && ticket.getNumerosApostados().equals(numerosApostados)) {
                // Se a aposta for do mesmo usuário, retorna verdadeiro (indica duplicação)
                if (ticket.getUsuario().equals(usuarioLogado)) {
                    return true; // Aposta duplicada do mesmo usuário
                }
            }
        }

        return false; // Aposta não duplicada para o mesmo usuário
    }

    // Método para salvar a aposta
    @FXML
    private void salvarAposta(ActionEvent event) {
        if (numerosApostados.size() < 15 || numerosApostados.size() > 20) {
            showError("A aposta deve ter entre 15 e 20 números.");
            return;
        }

        // Verifica se o concurso está fechado
        String concurso = comboBoxConcurso.getValue(); // Obtendo o valor do concurso
        List<Concurso> concursos = ConcursoUtils.carregarConcursos(); // Carrega os concursos
        boolean concursoFechado = false;

        // Verifica se o concurso selecionado está fechado
        for (Concurso c : concursos) {
            if (c.getNome().equals(concurso) && !c.isAberto()) {
                concursoFechado = true;
                break;
            }
        }

        if (concursoFechado) {
            showError("As apostas estão fechadas para este concurso.");
            return; // Impede a criação da aposta
        }

        // Verifica se a aposta já foi feita
        if (verificarApostaDuplicada(numerosApostados, concurso)) {
            showError("Esta aposta já foi feita anteriormente.");
            return;
        }

        // Criar o ticket com os números selecionados
        TicketUtils.salvarTicketEmArquivo(numerosApostados, concurso); // Passando os parâmetros corretamente
        showSuccess("Aposta salva com sucesso!");
    }

    // Método para gerar uma aposta aleatória
    @FXML
    private void gerarApostaAleatoria(ActionEvent event) {
        Random random = new Random();
        numerosApostados.clear();

        // Gera uma aposta aleatória com entre 15 e 20 números
        while (numerosApostados.size() < 15 + random.nextInt(6)) { // Entre 15 e 20 números
            int numero = random.nextInt(25) + 1;
            numerosApostados.add(numero);
        }

        // Atualiza a seleção na interface
        for (javafx.scene.Node node : gridPaneNumeros.getChildren()) {
            if (node instanceof CheckBox) {
                CheckBox checkBox = (CheckBox) node;
                checkBox.setSelected(numerosApostados.contains(Integer.parseInt(checkBox.getText())));
            }
        }

        // Atualiza a quantidade de números e o valor da aposta
        atualizarNumerosSelecionados();
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

    // Método para voltar para a tela principal
    @FXML
    private void voltarParaTelaPrincipal() {
        try {
            // Carregar a tela principal (MainMenu.fxml)
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/meuprojetojavafx/view/MainMenu.fxml"));
            Scene scene = new Scene(loader.load());

            // Obter o stage (janela) atual e configurar a nova cena
            Stage stage = (Stage) gridPaneNumeros.getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("Tela Principal");
            stage.show();
        } catch (IOException e) {
            System.err.println("Erro ao carregar a tela principal: " + e.getMessage());
        }
    }
}
