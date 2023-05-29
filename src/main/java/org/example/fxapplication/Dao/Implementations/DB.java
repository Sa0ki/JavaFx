package org.example.fxapplication.Dao.Implementations;

import java.io.FileInputStream;
import java.sql.*;
import java.util.Properties;

public class DB {
    private static Connection connection = null;

    public static Connection getConnection(){
        if(connection == null){
            try{
                Properties properties = loadProperties();
                String url = properties.getProperty("url");
                String user = properties.getProperty("user");
                String password = properties.getProperty("password");
                connection = DriverManager.getConnection(url, user, password);
                System.out.println("-------------------------------> Connecté avec la base de données.\n");
            }catch(SQLException e){
                e.printStackTrace();
                System.err.println("-------------------------------> ERREUR -> Connexion avec la base de données.");
            }
        }
        return connection;
    }
    private static Properties loadProperties(){
        try(FileInputStream file = new FileInputStream("src/main/resources/db.properties")){
            Properties properties = new Properties();
            properties.load(file);
            return properties;
        }catch(Exception e){
            e.printStackTrace();
            System.err.println("-------------------------------> ERREUR -> Ouverture du fichier de propriétés.");
        }
        return null;
    }

    public static void closeConnection(){
        PreparedStatement statement = null;
        if(connection != null){
            try{
                statement = connection.prepareStatement("select pg_reload_conf()");
                statement.execute();
                statement = connection.prepareStatement("SET enable_seqscan = off");
                statement.execute();
                closeStatement(statement);
                connection.close();
                System.out.println("-------------------------------> Déconnecté de la base de données.");
            }catch(SQLException e){
                e.printStackTrace();
                System.err.println("-------------------------------> ERREUR -> Déconnexion de la base de données.");
            }
        }
    }

    public static void closeStatement(Statement statement){
        if(statement != null){
            try{
                statement.close();
            }catch(SQLException e){
                e.printStackTrace();
                System.out.println("-------------------------------> ERREUR -> Fermeture du statement.");
            }
        }
    }

    public static void closeResultSet(ResultSet resultSet){
        if(resultSet != null){
            try{
                resultSet.close();
            }catch(SQLException e){
                e.printStackTrace();
                System.out.println("ERREUR -> Fermeture du resultSet.");
            }
        }
    }
}
