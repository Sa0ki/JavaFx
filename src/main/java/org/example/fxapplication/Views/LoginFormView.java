package org.example.fxapplication.Views;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class LoginFormView extends Application {

    @Override
    public void start(Stage stage) throws Exception{
        FXMLLoader fxmlLoader = new FXMLLoader(LoginFormView.class.getResource("loginForm.fxml"));
        stage.setTitle("Login");
        stage.setScene(new Scene(fxmlLoader.load(), 800, 650));
        stage.show();
    }
    public static void main(String[] args) {
        launch();
    }
}
