package org.example.fxapplication.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.example.fxapplication.Cache.DataCache;
import org.example.fxapplication.Models.Client;
import org.example.fxapplication.Security.PasswordEncoder;
import org.example.fxapplication.Services.ClientService;
import org.example.fxapplication.Views.GetAllClientsView;
import org.example.fxapplication.Views.LoginFormView;

public class ClientFormController {
    @FXML
    private TextField firstNameField;
    @FXML
    private TextField lastNameField;
    @FXML
    private DatePicker dobField;
    @FXML
    private TextField cinField;
    @FXML
    private TextField emailField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Label message;
    @FXML
    Button addButton;
    @FXML
    Button menuButton;
    @FXML
    private Button logoutButton;
    private static ClientService clientService = new ClientService();
    private GetAllClientsView getAllClientsView = new GetAllClientsView();

    @FXML
    private void onAddButtonClick(ActionEvent actionEvent) throws Exception {
        if(clientService.insertClient(new Client(
                1L,
                firstNameField.getText(),
                lastNameField.getText(),
                dobField.getValue(),
                cinField.getText(),
                emailField.getText(),
                PasswordEncoder.encode(passwordField.getText())
        )) == true)
            message.setText("Client added.");
        else
            message.setText("Client already exists.");

        DataCache.setClientsObservableList(null);
        getAllClientsView.start((Stage) addButton.getScene().getWindow());
    }
    @FXML
    private void onMenuButtonClick(){
        try{
            getAllClientsView.start((Stage)menuButton.getScene().getWindow());
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    @FXML
    public void onLogoutButtonCLick(){
        LoginFormView loginFormView = new LoginFormView();
        try{
            loginFormView.start((Stage)logoutButton.getScene().getWindow());
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
