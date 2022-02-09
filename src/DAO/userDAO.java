package DAO;

import Model.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * This is the DAO class for users.
 */
public class userDAO {

    /**
     * Querying the database to get all users.
     */
    public static ObservableList<User> getUsers(){

        ObservableList<User> users = FXCollections.observableArrayList();

        try {
            JDBC.openConnection();
            String q = "SELECT * FROM users";
            dbQuery.Query(q);
            ResultSet resultSet = dbQuery.getResultSet();

            while (resultSet.next()) {
                int userID = resultSet.getInt("User_ID");
                String username = resultSet.getString("User_Name");
                String password = resultSet.getString("Password");
                User result = new User(userID, username, password);
                users.add(result);
            }
            JDBC.closeConnection();
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return users;
    }

    /*public static ObservableList<User> getUsersFromUsername(){

        ObservableList<User> users = FXCollections.observableArrayList();

        try {
            JDBC.openConnection();
            String q = "SELECT User_Name FROM users";
            dbQuery.Query(q);
            ResultSet resultSet = dbQuery.getResultSet();

            while (resultSet.next()) {
                //int userID = resultSet.getInt("User_ID");
                String usernameA = resultSet.getString("User_Name");
               //String password = resultSet.getString("Password");
                User result = new User(usernameA);
                users.add(result);
            }
            JDBC.closeConnection();
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return users;
    }*/

}
