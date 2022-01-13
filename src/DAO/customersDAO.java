package DAO;

import Model.Customers;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;

public class customersDAO {

    public static ObservableList<Customers> getCustomers(){

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
    }
}
