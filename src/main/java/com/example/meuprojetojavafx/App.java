package com.example.meuprojetojavafx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {
            // Carrega o arquivo FXML de registro
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/meuprojetojavafx/view/MenuAdministrador.fxml"));

            // Carrega o layout a partir do arquivo FXML
            Parent root = loader.load();

            // Define o título da janela
            primaryStage.setTitle("Login");

            // Define a cena com o layout carregado do FXML
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);

            // Exibe a janela
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args); // Inicializa a aplicação JavaFX
    }
}
