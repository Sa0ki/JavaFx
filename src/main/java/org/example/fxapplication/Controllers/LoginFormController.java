package org.example.fxapplication.Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.example.fxapplication.Security.Authentication;
import org.example.fxapplication.Views.GetAllClientsView;

public class LoginFormController {
    @FXML
    private TextField emailField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Button loginButton;
    @FXML
    private Label message;

    @FXML
    private void onLoginButtonClick(){
        if(Authentication.login(emailField.getText(), passwordField.getText()) == true){
            GetAllClientsView getAllClientsView = new GetAllClientsView();
            try{
                getAllClientsView.start((Stage) loginButton.getScene().getWindow());
            }catch(Exception e){
                e.printStackTrace();
            }
        }
        else
            message.setText("Email or password incorrect.");
    }
}
