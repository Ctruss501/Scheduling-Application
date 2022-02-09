package DAO;

import Model.Contacts;
import Model.Countries;
import Model.Customers;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * This is the DAO for contacts.
 */
public class contactsDAO {

    /**
     * Query the database for all contacts.
     * @return
     */
    public static ObservableList<Contacts> getContacts(){

        ObservableList<Contacts> contacts = FXCollections.observableArrayList();

        try {
            JDBC.openConnection();
            String q = "SELECT * FROM contacts";
            dbQuery.Query(q);
            ResultSet resultSet = dbQuery.getResultSet();

            while (resultSet.next()){
                int contactID = resultSet.getInt("Contact_ID");
                String contactName = resultSet.getString("Contact_Name");
                String contactEmail = resultSet.getString("Email");
                Contacts result = new Contacts(contactID,contactName,contactEmail);
                contacts.add(result);
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return contacts;
    }

    /*public static Contacts getContactsByName(String contactName) throws SQLException, Exception{

        try {
            JDBC.openConnection();
            String q = "SELECT * FROM countries WHERE Country = '" + contactName + "'";
            dbQuery.Query(q);
            Contacts result;
            ResultSet resultSet = dbQuery.getResultSet();

            while (resultSet.next()){
                int contactID = resultSet.getInt("Contact_ID");
                contactName = resultSet.getString("Contact_Name");
                String contactEmail = resultSet.getString("Email");
                result = new Contacts(contactID,contactName,contactEmail);
                return result;
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }*/
}
