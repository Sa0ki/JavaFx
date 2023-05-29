package org.example.fxapplication.Tables;

import org.example.fxapplication.Dao.Implementations.DB;

import java.sql.*;

public class Tables {
    private static Connection connection = DB.getConnection();
    public static void createTableClients(){
        if(exists("clients") == true){
            System.out.println("-------------------------------> La table 'clients' existe déjà.");
            return;
        }
        try{
            String query = "CREATE TABLE CLIENTS(id serial primary key, firstName varchar(25), lastName varchar(25), dob date, cin varchar(8), email varchar(30), password text)";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.execute();
            DB.closeStatement(statement);
            System.out.println("-------------------------------> La table 'clients' a été créée.");
        }catch(SQLException e){
            e.printStackTrace();
            System.out.println("-------------------------------> ERREUR -> Création de la table 'clients'.");
        }
    }

    public static void deleteTableClients(){
        if(exists("clients") == false){
            System.out.println("-------------------------------> Suppression impossible de la table 'clients', car elle n'existe pas.");
            return;
        }
        try{
            String query = "DROP TABLE clients";
            Statement statement = connection.createStatement();
            statement.execute(query);
            System.out.println("-------------------------------> La table 'clients' a été supprimée.");
            DB.closeStatement(statement);
        }catch(SQLException e){
            e.printStackTrace();
            System.out.println("-------------------------------> ERREUR -> Suppression de la table 'clients'.");
        }
    }

    public static Boolean exists(String tableName){
        PreparedStatement statement = null;
        try{
            String query = "SELECT EXISTS (SELECT 1 FROM pg_tables WHERE schemaname = 'public' AND tablename = ?)";
            statement = connection.prepareStatement(query);
            statement.setString(1, tableName);
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next())
                return resultSet.getBoolean("exists");
            return false;
        }catch(SQLException e){
            e.printStackTrace();
            System.out.println("-------------------------------> ERREUR -> Vérification de l'existence de la table '" + tableName + "'.");
            return false;
        }finally {
            DB.closeStatement(statement);
        }
    }
}
