package DAO;

import Model.Customers;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;

public class customersDAO {

    public static ObservableList<Customers> getCustomers() {

        ObservableList<Customers> customers = FXCollections.observableArrayList();

        try {
            JDBC.openConnection();
            String q = "SELECT * FROM customers";
            dbQuery.Query(q);
            ResultSet resultSet = dbQuery.getResultSet();

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

    public static int addCustomer(Customers customer) throws SQLException{

        Connection connection = JDBC.openConnection();
        String q = "INSERT INTO customers(Customer_Name, Address, Postal_Code, Phone) VALUES(?,?,?,?,?,?,?)";
        dbQuery.setPreparedStatement(connection, q);
        PreparedStatement preparedStatement = dbQuery.getPreparedStatement();

        preparedStatement.setString(1, customer.getCustName());
        preparedStatement.setString(2, customer.getCustAddress());
        preparedStatement.setString(3, customer.getCustPostalCode());
        preparedStatement.setString(4, customer.getCustPhoneNum());

        int insertCust = preparedStatement.executeUpdate();
        preparedStatement.close();
        return insertCust;
    }

    public static int editCustomer(Customers customer) throws SQLException{

        Connection connection = JDBC.openConnection();
        String q = "UPDATE customers SET Customer_Name = ?, Address = ?, Postal_Code = ?, Phone = ? WHERE Customer_ID)";
        dbQuery.setPreparedStatement(connection, q);
        PreparedStatement preparedStatement = dbQuery.getPreparedStatement();

        preparedStatement.setString(1, customer.getCustName());
        preparedStatement.setString(2, customer.getCustAddress());
        preparedStatement.setString(3, customer.getCustPostalCode());
        preparedStatement.setString(4, customer.getCustPhoneNum());
        preparedStatement.setInt(5, customer.getCustID());

        int updateCust = preparedStatement.executeUpdate();
        preparedStatement.close();
        return updateCust;
    }

    public static int deleteCustomer(Customers customer) throws SQLException{

        Connection connection = JDBC.openConnection();
        String q = "DELETE FROM customers WHERE Customer_ID = ?";
        dbQuery.setPreparedStatement(connection, q);
        PreparedStatement preparedStatement = dbQuery.getPreparedStatement();

        preparedStatement.setInt(1, customer.getCustID());

        int deleteCust = preparedStatement.executeUpdate();
        preparedStatement.close();
        return deleteCust;
    }
}
