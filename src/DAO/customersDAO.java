package DAO;

import Model.Countries;
import Model.Customers;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;


public class customersDAO {

    public static ObservableList<Customers> getCustomers() {

        ObservableList<Customers> customers = FXCollections.observableArrayList();

        try {
            JDBC.openConnection();
            String q = "SELECT * FROM customers, first_level_divisions, countries WHERE customers.Division_ID = first_level_divisions.Division_ID AND first_level_divisions.Country_ID = countries.Country_ID";
            dbQuery.Query(q);
            ResultSet resultSet = dbQuery.getResultSet();

            while (resultSet.next()){
                int custID = resultSet.getInt("Customer_ID");
                String custName = resultSet.getString("Customer_Name");
                String custAddress = resultSet.getString("Address");
                String custPostal = resultSet.getString("Postal_Code");
                String countryID = resultSet.getString(19);
                String custPhone = resultSet.getString("Phone");
                String divID = resultSet.getString(12);
                Customers result = new Customers(custID, custName, custAddress, custPostal, custPhone, countryID, divID);
                customers.add(result);
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return customers;

        /*Connection connection = JDBC.openConnection();
        String q = "SELECT * FROM customers";
        dbQuery.setPreparedStatement(connection, q);
        PreparedStatement preparedStatement = dbQuery.getPreparedStatement();
        preparedStatement.executeQuery();
        ResultSet resultSet = preparedStatement.getResultSet();

        while (resultSet.next()){
            int custID = resultSet.getInt("Customer_ID");
            String custName = resultSet.getString("Customer_Name");
            String custAddress = resultSet.getString("Address");
            String custPostal = resultSet.getString("Postal_Code");
            String custPhone = resultSet.getString("Phone");
            int divID = resultSet.getInt("Division_ID");
            Customers result = new Customers(custID, custName, custAddress, custPostal, custPhone, divID);
            customers.add(result);
        }
        preparedStatement.close();
        return customers;*/
    }

    public static void addCustomer(String custName, String custAddress, String custPostal, String custPhone, String countryName, int divDivision) throws SQLException{

        try {
            Connection connection = JDBC.openConnection();
            String q = "INSERT INTO customers VALUES(NULL, ?, ?, ?, ?, NOW(), 'user', NOW(), 'user', ?);";
            dbQuery.setPreparedStatement(connection, q);
            PreparedStatement preparedStatement = dbQuery.getPreparedStatement();

            preparedStatement.setString(1, custName);
            preparedStatement.setString(2, custAddress);
            preparedStatement.setString(3, custPostal);
            preparedStatement.setString(4, custPhone);
            preparedStatement.setInt(5, divDivision);

            preparedStatement.execute();
            preparedStatement.close();
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }

    public static void editCustomer(int custID, String custName, String custAddress, String custPostal, String custPhone, String countryName, int divDivision) throws SQLException{

        try {
            Connection connection = JDBC.openConnection();
            String q = "UPDATE customers SET Customer_Name = ?, Address = ?, Postal_Code = ?, Phone = ?, Division_ID = ?, Last_Update = NOW() WHERE Customer_ID = ?;";
            dbQuery.setPreparedStatement(connection, q);
            PreparedStatement preparedStatement = dbQuery.getPreparedStatement();

            preparedStatement.setString(1, custName);
            preparedStatement.setString(2, custAddress);
            preparedStatement.setString(3, custPostal);
            preparedStatement.setString(4, custPhone);
            preparedStatement.setInt(5, divDivision);
            preparedStatement.setInt(6, custID);

            preparedStatement.executeUpdate();
            preparedStatement.close();
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }

    public static void deleteCustomer(Customers customer) throws SQLException{

        try {
            Connection connection = JDBC.openConnection();
            String q = "DELETE FROM customers WHERE Customer_ID = ?";
            dbQuery.setPreparedStatement(connection, q);
            PreparedStatement preparedStatement = dbQuery.getPreparedStatement();

            preparedStatement.setInt(1, customer.getCustID());

            preparedStatement.execute();
            preparedStatement.close();
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }
}
