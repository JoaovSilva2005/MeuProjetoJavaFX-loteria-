module com.example.meuprojetojavafx {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires com.almasb.fxgl.all;
    requires java.desktop;

    exports com.example.meuprojetojavafx;
    exports com.example.meuprojetojavafx.controller;
    exports com.example.meuprojetojavafx.model;

    opens com.example.meuprojetojavafx to javafx.fxml;
    opens com.example.meuprojetojavafx.controller to javafx.fxml;
    opens com.example.meuprojetojavafx.model to javafx.fxml;
}
