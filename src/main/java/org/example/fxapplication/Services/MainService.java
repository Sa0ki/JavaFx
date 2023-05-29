package org.example.fxapplication.Services;

import org.example.fxapplication.Models.Client;

import java.util.List;

public class MainService {
    private ClientService clientService = new ClientService();
    private FileService fileService = new FileService();

    public Boolean saveToFile(String fileName){
        int indexPoint = fileName.lastIndexOf(".");
        String extension = fileName.substring(indexPoint + 1);

        switch (extension){
            case "txt":
                System.out.println("----------------------------------------------------> Sauvegarde TXT.");
                return saveToTxtFile(fileName);
            case "json":
                System.out.println("----------------------------------------------------> Sauvegarde JSON.");
                return saveToJsonFile(fileName);
            case "xlsx":
                System.out.println("----------------------------------------------------> Sauvegarde EXCEL.");
                return saveToExcelFile(fileName);
            default:
                return false;
        }
    }
    public Boolean saveToTxtFile(String fileName){
        List<Client> clientsList = clientService.getAllClients();
         return fileService.writeListIntoTxtFile(clientsList,fileName);
    }
    public Boolean saveToJsonFile(String fileName){
        List<Client> clientsList = clientService.getAllClients();
        return fileService.writeListToJsonFile(clientsList, fileName);
    }
    public Boolean saveToExcelFile(String fileName){
        List<Client> clientsList = clientService.getAllClients();
        return fileService.writeListIntoExcelFile(clientsList, fileName);
    }

}
