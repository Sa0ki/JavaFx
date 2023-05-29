package org.example.fxapplication.Services;

import org.example.fxapplication.Dao.Implementations.ClientDao;
import org.example.fxapplication.Models.Client;

import java.util.List;

public class ClientService {
    private static ClientDao clientDao = new ClientDao();

    public List<Client> getAllClients(){
        return clientDao.getAllClients();
    }

    public Boolean insertClient(Client client){
        return clientDao.insertClient(client);
    }

    public Boolean updateClient(Client client){
        return clientDao.updateClientById(client);
    }

    public Boolean deleteClientById(Long id){
        return clientDao.deleteClientById(id);
    }

    public Client getClientByEmail(String email){
        return clientDao.getClientByEmail(email);
    }
}
