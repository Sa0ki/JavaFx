package org.example.fxapplication.Cache;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.example.fxapplication.Models.Client;

public class DataCache {
    private static ObservableList<Client> clientsObservableList;

    public static ObservableList<Client> getClientsObservableList() {
        return clientsObservableList;
    }

    public static void setClientsObservableList(ObservableList<Client> clients) {
        clientsObservableList = clients;
    }
}
