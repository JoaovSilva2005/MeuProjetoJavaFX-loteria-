package com.example.meuprojetojavafx.controller;

import com.example.meuprojetojavafx.model.Concurso;
import com.example.meuprojetojavafx.util.ConcursoUtils;
import com.example.meuprojetojavafx.util.Sorteio;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.util.List;

public class FinalizarConcursoController {

    @FXML
    private CheckBox checkBoxManual;

    @FXML
    private Button btnSalvarSorteio;

    @FXML
    private Button btnCancelar;

    @FXML
    private Label labelFinalizarConcurso;

    @FXML
    private ListView<String> listViewConcursos; // Lista para exibir os concursos

    private boolean sorteioRealizado = false;
    private List<Integer> numerosSorteados;
    private Concurso concursoSelecionado;

    // Método para carregar os concursos ao iniciar a tela de Finalizar Concurso
    private void carregarConcursos() {
        listViewConcursos.getItems().clear(); // Limpar a lista antes de adicionar novos itens
        List<Concurso> concursos = ConcursoUtils.carregarConcursos();
        if (concursos.isEmpty()) {
            labelFinalizarConcurso.setText("Nenhum concurso aberto para finalizar.");
        } else {
            // Caso existam concursos abertos, exibe na lista
            for (Concurso concurso : concursos) {
                if (concurso.isAberto()) {
                    listViewConcursos.getItems().add(concurso.getNome()); // Adiciona o nome do concurso na lista
                }
            }
            labelFinalizarConcurso.setText("Concursos carregados. Escolha o sorteio.");
        }
    }

    @FXML
    private void gerarSorteioAutomatico() {
        if (sorteioRealizado) {
            labelFinalizarConcurso.setText("O sorteio já foi realizado!");
            return;
        }

        // Gerar sorteio automático
        Sorteio sorteio = new Sorteio();
        this.numerosSorteados = sorteio.realizarSorteio();
        labelFinalizarConcurso.setText("Números sorteados automaticamente: " + numerosSorteados);

        // Habilitar o botão de salvar sorteio
        btnSalvarSorteio.setDisable(false);
    }

    @FXML
    private void salvarSorteio() {
        if (sorteioRealizado) {
            labelFinalizarConcurso.setText("O sorteio já foi realizado!");
            return;
        }

        // Se o sorteio foi feito manualmente
        if (checkBoxManual.isSelected()) {
            this.numerosSorteados = gerarSorteioManual();
            labelFinalizarConcurso.setText("Números sorteados manualmente: " + numerosSorteados);
        }

        // Verifica se algum número foi sorteado
        if (numerosSorteados == null || numerosSorteados.isEmpty()) {
            labelFinalizarConcurso.setText("Nenhum número sorteado!");
            return;
        }

        // Se um concurso foi selecionado
        if (concursoSelecionado != null) {
            concursoSelecionado.setNumerosSorteados(numerosSorteados);
            concursoSelecionado.setAberto(false);  // Marcar como fechado

            // Salvar sorteio no arquivo
            ConcursoUtils.atualizarConcursoNoArquivo(concursoSelecionado);
            Sorteio sorteio = new Sorteio();
            sorteio.salvarResultado(concursoSelecionado.getNome(), numerosSorteados);

            sorteioRealizado = true;
            labelFinalizarConcurso.setText("Sorteio realizado e salvo com sucesso!");
        } else {
            labelFinalizarConcurso.setText("Nenhum concurso selecionado para finalizar.");
        }

        // Desabilitar o botão de sorteio após o processo
        btnSalvarSorteio.setDisable(true);
    }

    @FXML
    private void selecionarConcurso() {
        String concursoNomeSelecionado = listViewConcursos.getSelectionModel().getSelectedItem();

        if (concursoNomeSelecionado != null) {
            // Procurar o concurso com o nome selecionado
            List<Concurso> concursos = ConcursoUtils.carregarConcursos();
            for (Concurso concurso : concursos) {
                if (concurso.getNome().equals(concursoNomeSelecionado)) {
                    concursoSelecionado = concurso;
                    break;
                }
            }
            labelFinalizarConcurso.setText("Concurso " + concursoSelecionado.getNome() + " selecionado. Pronto para sorteio.");
        }
    }

    private List<Integer> gerarSorteioManual() {
        // Para o sorteio manual, simula-se uma seleção de números (poderia ser preenchido com o input do usuário)
        return List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15);
    }

    @FXML
    private void voltarParaTelaPrincipal() {
        Stage stage = (Stage) btnCancelar.getScene().getWindow();
        stage.close();  // Fecha a tela atual
    }

    // Método de inicialização da tela
    @FXML
    public void initialize() {
        carregarConcursos();  // Carregar concursos ao inicializar a tela
        // Desabilitar o botão de salvar sorteio até que o sorteio seja realizado
        btnSalvarSorteio.setDisable(true);

        // Configurar o evento de seleção de concurso
        listViewConcursos.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> selecionarConcurso());
    }
}
