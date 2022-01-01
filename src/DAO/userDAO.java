package DAO;

import Model.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Data Access Object class for the users table.
 */
public class userDAO {


    /**
     * Database query to return all users from users table.
     * @return Users observable list.
     * @throws SQLException
     * @throws Exception
     */
    public static ObservableList<User> getUsers() throws SQLException, Exception{
        ObservableList<User> users = FXCollections.observableArrayList();
        JDBC.openConnection();
        String q = "SELECT * FROM users";
        dbQuery.Query(q);
        ResultSet resultSet = dbQuery.getResultSet();

        while (resultSet.next()){
            int userID = resultSet.getInt("User_ID");
            String username = resultSet.getString("User_Name");
            String password = resultSet.getString("Password");
            User result = new User(userID, username, password);
            users.add(result);
        }
        JDBC.closeConnection();
        return users;
    }

    /**
     * Database query to return users by name from users table
     * @param username
     * @return Users observable list.
     * @throws SQLException
     * @throws Exception
     */
    public static User getUserFromUsername(String username) throws SQLException, Exception{
        JDBC.openConnection();
        String q = "SELECT * FROM users WHERE User_Name = '" + username + "'";
        dbQuery.Query(q);
        User result;
        ResultSet resultSet = dbQuery.getResultSet();

        while (resultSet.next()){
            int userID = resultSet.getInt("User_ID");
            String usernameA = resultSet.getString("User_Name");
            String password = resultSet.getString("Password");
            result = new User(userID, usernameA, password);
            return result;
        }
        JDBC.closeConnection();
        return null;
    }
}
