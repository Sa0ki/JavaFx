package org.example.fxapplication.Security;

import org.example.fxapplication.Models.Client;
import org.example.fxapplication.Services.ClientService;

public class Authentication {
    private static ClientService clientService = new ClientService();
    public static Boolean login(String email, String password){
        Client client = clientService.getClientByEmail(email);
        if(client == null)
            return false;
        if(! PasswordEncoder.comparePasswords(password, client.getPassword()))
            return false;
        return true;
    }
}
