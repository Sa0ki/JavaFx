package org.example.fxapplication.Controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableListBase;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;
import org.example.fxapplication.Cache.DataCache;
import org.example.fxapplication.Models.Client;
import org.example.fxapplication.Services.ClientService;
import org.example.fxapplication.Services.FileService;
import org.example.fxapplication.Services.MainService;
import org.example.fxapplication.Views.ClientFormView;
import org.example.fxapplication.Views.LoginFormView;
import java.awt.Desktop;

import java.io.File;
import java.time.LocalDate;
import java.util.List;

public class GetAllClientsController {
    @FXML
    private TableView<Client> clientsList;
    @FXML
    private Button deleteButton;
    @FXML
    private Button newClientButton;
    @FXML
    private Button saveToFileButton;
    @FXML
    private Label message;
    @FXML
    private Button logoutButton;
    @FXML
    private Button onSaveToExcelFileButtonClick;
    @FXML
    private Button onImportToExcelFileButtonClick;
    private static ClientService clientService = new ClientService();
    private static MainService mainService = new MainService();
    private static FileService fileService = new FileService();
    private ClientFormView clientFormView = new ClientFormView();
    @FXML
    public void initialize(){
         ObservableList<Client> clientsObservableList = FXCollections.observableArrayList();

        newClientButton.setOnAction(event -> {
            try {
                clientFormView.start((Stage)newClientButton.getScene().getWindow());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        deleteButton.setOnAction(event -> {
            Client clientSelected = clientsList.getSelectionModel().getSelectedItem();
            if(clientSelected != null){
                clientsObservableList.remove(clientSelected);
                clientService.deleteClientById(clientSelected.getId());
            }
        });

        clientsList.setEditable(true);

        if(DataCache.getClientsObservableList() == null){
            clientService.getAllClients().forEach(client -> clientsObservableList.add(client));
            DataCache.setClientsObservableList(clientsObservableList);
        }
        else
            DataCache.getClientsObservableList().forEach(client -> clientsObservableList.add(client));

        this.clientsList.setItems(clientsObservableList);

        TableColumn<Client, Long> idColumn = buildIdColumn();
        TableColumn<Client, String> firstNameColumn = buildFirstNameColumn();
        TableColumn<Client, String> lastNameColumn = buildLastNameColumn();
        TableColumn<Client, LocalDate> dobColumn = buildDobColumn();
        TableColumn<Client, String> cinColumn = buildCinColumn();
        TableColumn<Client, String> emailColumn = buildEmailColumn();
        TableColumn<Client, String> passwordColumn = buildPasswordColumn();

        clientsList.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        clientsList.getColumns().addAll(idColumn, firstNameColumn, lastNameColumn, dobColumn, cinColumn, emailColumn, passwordColumn);
    }

    public TableColumn<Client, Long> buildIdColumn(){
        TableColumn<Client, Long> idColumn = new TableColumn("ID");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        idColumn.setPrefWidth(50);
        return idColumn;
    }

    public TableColumn<Client, String> buildFirstNameColumn(){
        TableColumn<Client, String> firstNameColumn = new TableColumn<Client, String>("FIRST NAME");
        firstNameColumn.setCellValueFactory(new PropertyValueFactory<Client, String>("firstName"));
        firstNameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        firstNameColumn.setPrefWidth(100);
        firstNameColumn.setOnEditCommit(event -> {
            Client client = event.getRowValue();
            client.setFirstName(event.getNewValue());
            clientService.updateClient(client);
        });
        return firstNameColumn;
    }
    public TableColumn<Client, String> buildLastNameColumn(){
        TableColumn<Client, String> lastNameColumn = new TableColumn("LAST NAME");
        lastNameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        lastNameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        lastNameColumn.setPrefWidth(100);
        lastNameColumn.setOnEditCommit(event -> {
            Client client = event.getRowValue();
            client.setLastName(event.getNewValue());
            clientService.updateClient(client);
        });
        return lastNameColumn;
    }
    public TableColumn<Client, LocalDate> buildDobColumn(){
        TableColumn<Client, LocalDate> dobColumn = new TableColumn("DOB");
        dobColumn.setCellValueFactory(new PropertyValueFactory<>("dob"));
        dobColumn.setPrefWidth(100);
        dobColumn.setOnEditCommit(event -> {
            Client client = event.getRowValue();
            client.setDob(event.getNewValue());
            clientService.updateClient(client);
        });
        return dobColumn;
    }

    public TableColumn<Client, String> buildCinColumn(){
        TableColumn<Client, String> cinColumn = new TableColumn("CIN");
        cinColumn.setCellValueFactory(new PropertyValueFactory<>("cin"));
        cinColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        cinColumn.setPrefWidth(100);
        cinColumn.setOnEditCommit(event -> {
            Client client = event.getRowValue();
            client.setCin(event.getNewValue());
            clientService.updateClient(client);
        });
        return cinColumn;
    }

    public TableColumn<Client, String> buildEmailColumn(){
        TableColumn<Client, String> emailColumn = new TableColumn("EMAIL");
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        emailColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        emailColumn.setPrefWidth(150);
        emailColumn.setOnEditCommit(event -> {
            Client client = event.getRowValue();
            client.setEmail(event.getNewValue());
            clientService.updateClient(client);
        });
        return emailColumn;
    }
    public TableColumn<Client, String> buildPasswordColumn(){
        TableColumn<Client, String> passwordColumn = new TableColumn("PASSWORD");
        passwordColumn.setCellValueFactory(new PropertyValueFactory<>("password"));
        passwordColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        passwordColumn.setPrefWidth(178);
        passwordColumn.setOnEditCommit(event -> {
            Client client = event.getRowValue();
            client.setPassword(event.getNewValue());
            clientService.updateClient(client);
            clientsList.refresh();
        });
        return passwordColumn;
    }

    @FXML
    public void onSaveToFileButtonClick(){
        mainService.saveToTxtFile("sauvegarde_clients.txt");
        openFile("src/main/resources/Files/Sauvegardes/sauvegarde_clients.txt");
    }

    @FXML
    private void onSaveToExcelFileButtonClick(){
        mainService.saveToExcelFile("sauvegarde_clients.xlsx");
        openFile("src/main/resources/Files/sauvegarde_clients.xlsx");
    }

    @FXML
    private void onImportToExcelFileButtonClick(){
        fileService.readFromExcelFile("sauvegarde_clients.xlsx")
                .forEach(client -> clientService.insertClient(client));
        ObservableList<Client> list = FXCollections.observableArrayList();
        fileService.readFromExcelFile("sauvegarde_clients.xlsx").forEach(client -> {
            list.add(client);
            clientService.updateClient(client);
        });
        clientsList.setItems(list);
        clientsList.refresh();
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

    private void openFile(String filePath){
        if(Desktop.isDesktopSupported()){
            Desktop desktop = Desktop.getDesktop();
            if(desktop.isSupported(Desktop.Action.OPEN))
                try{
                    desktop.open(new File(filePath));
                }catch(Exception e){
                    e.printStackTrace();
                }
            else
                System.out.println("Problème d'ouverture de fichier.");
        }
        else
            System.out.println("Problème au niveau du desktop.");
    }
}
