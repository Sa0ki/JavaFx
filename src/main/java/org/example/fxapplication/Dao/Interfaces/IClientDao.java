package org.example.fxapplication.Dao.Interfaces;

import org.example.fxapplication.Models.Client;

import java.util.List;

public interface IClientDao {
    public List<Client> getAllClients();
    public Boolean insertClient(Client client);
    public Boolean updateClientById(Client client);
    public Boolean deleteClientById(Long id);
    public Client getClientByEmail(String email);
}
