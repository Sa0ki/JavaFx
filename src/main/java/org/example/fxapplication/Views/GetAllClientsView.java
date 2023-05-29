package org.example.fxapplication.Views;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class GetAllClientsView extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(GetAllClientsView.class.getResource("getAllClients.fxml"));
        stage.setTitle("Clients");
        stage.setScene(new Scene(fxmlLoader.load(), 800, 650));
        stage.show();
    }
    public static void main(String[] args) {
        launch();
    }
}
