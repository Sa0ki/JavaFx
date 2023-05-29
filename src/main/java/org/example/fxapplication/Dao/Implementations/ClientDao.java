package org.example.fxapplication.Dao.Implementations;

import org.example.fxapplication.Dao.Interfaces.IClientDao;
import org.example.fxapplication.Models.Client;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClientDao implements IClientDao{
    Connection connection = DB.getConnection();
    @Override
    public List<Client> getAllClients() {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try{
            String query = "select * from clients order by id";
            statement = connection.prepareStatement(query);
            resultSet = statement.executeQuery();

            List<Client> clientsList = new ArrayList<>();
            while(resultSet.next())
                clientsList.add(new Client(
                        resultSet.getLong("id"),
                        resultSet.getString("firstname"),
                        resultSet.getString("lastname"),
                        resultSet.getDate("dob").toLocalDate(),
                        resultSet.getString("cin"),
                        resultSet.getString("email"),
                        resultSet.getString("password")
                ));
            return clientsList;
        }catch(SQLException e){
            e.printStackTrace();
            System.out.println("-------------------------------> ERREUR -> getAllClient.");
            return null;
        }finally {
            DB.closeStatement(statement);
            DB.closeResultSet(resultSet);
        }
    }

    @Override
    public Boolean insertClient(Client client) {
        PreparedStatement statement = null;
        try{
            Client clientFound = getClientByEmail(client.getEmail());
            if(clientFound != null){
                System.out.println("-------------------------------> Client '" + client.getFirstName() + " " + client.getLastName() + "' existe déjà.");
                return false;
            }

            String query = "insert into clients (firstName, lastName, dob, cin, email, password) values(?, ?, ?, ?, ? , ?)";
            statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

            configureSetStatement(client, statement);

            int rowAffected = statement.executeUpdate();
            if(rowAffected > 0){
                ResultSet resultSet = statement.getGeneratedKeys();
                while(resultSet.next())
                    client.setId(resultSet.getLong("id"));

                DB.closeResultSet(resultSet);
            }
            System.out.println("-------------------------------> Client '" + client.getFirstName() + " " + client.getLastName() + "' a été inséré.");
            return true;
        }catch(SQLException e){
            e.printStackTrace();
            System.out.println("-------------------------------> ERREUR -> insertClient.");
            return false;
        }finally {
            DB.closeStatement(statement);
        }
    }
    @Override
    public Boolean updateClientById(Client client) {
        PreparedStatement statement = null;
        try{
            String query = "update clients set firstname = ?, lastname = ?, dob = ?, cin = ?, email = ?, password = ? where id = ?";
            statement = connection.prepareStatement(query);

            configureSetStatement(client, statement);
            statement.setLong(7, client.getId());

            statement.executeUpdate();
            System.out.println("-------------------------------> Client '" + client.getFirstName() + " " + client.getLastName() + "' a été modifié.");
            return true;
        }catch(SQLException e){
            e.printStackTrace();
            System.out.println("-------------------------------> ERREUR -> updateClient.");
            return false;
        }finally {
            DB.closeStatement(statement);
        }
    }

    @Override
    public Boolean deleteClientById(Long id) {
        PreparedStatement statement = null;
        try{
            String query = "delete from clients where id = ?";
            statement = connection.prepareStatement(query);

            statement.setLong(1, id);

            statement.executeUpdate();
            System.out.println("-------------------------------> Client '" + id + "' a été supprimé.");
            return true;
        }catch(SQLException e){
            e.printStackTrace();
            System.out.println("-------------------------------> ERREUR -> deleteClientById.");
            return false;
        }finally {
            DB.closeStatement(statement);
        }
    }

    @Override
    public Client getClientByEmail(String email) {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try{
            String query = "select * from clients where email = ?";
            statement = connection.prepareStatement(query);
            statement.setString(1, email);
            resultSet = statement.executeQuery();

            Client client = null;
            if(resultSet.next())
                client = new Client(
                        resultSet.getLong("id"),
                        resultSet.getString("firstname"),
                        resultSet.getString("lastname"),
                        resultSet.getDate("dob").toLocalDate(),
                        resultSet.getString("cin"),
                        resultSet.getString("email"),
                        resultSet.getString("password")
                );
            return client;
        }catch(SQLException e){
            e.printStackTrace();
            System.out.println("-------------------------------> ERREUR -> getAllClient.");
            return null;
        }finally {
            DB.closeStatement(statement);
            DB.closeResultSet(resultSet);
        }
    }

    private static void configureSetStatement(Client client, PreparedStatement statement){
        try{
            statement.setString(1, client.getFirstName());
            statement.setString(2, client.getLastName());
            statement.setDate(3, Date.valueOf(client.getDob()));
            statement.setString(4, client.getCin());
            statement.setString(5, client.getEmail());
            statement.setString(6, client.getPassword());
        }catch(SQLException e){
            e.printStackTrace();
            System.out.println("-------------------------------> ERREUR -> configureSetStatement.");
        }
    }
}
