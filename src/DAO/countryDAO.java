package DAO;

import Model.Countries;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Data Access Object class for the countries table.
 */
public class countryDAO {

    /**
     * Database query to return all countries from countries table.
     * @return Countries observable list.
     * @throws SQLException
     * @throws Exception
     */
    public static ObservableList<Countries> getCountries() throws SQLException, Exception{
        ObservableList<Countries> countries = FXCollections.observableArrayList();
        JDBC.openConnection();
        String q = "SELECT * FROM countries";
        dbQuery.Query(q);
        ResultSet resultSet = dbQuery.getResultSet();

        while (resultSet.next()){
            int countryID = resultSet.getInt("Country_ID");
            String countryName = resultSet.getString("Country");
            Countries result = new Countries(countryID, countryName);
            countries.add(result);
        }
        JDBC.closeConnection();
        return countries;
    }

    /**
     * Database query to return countries by name from countries table.
     * @param countryName
     * @return Countries observable list.
     * @throws SQLException
     * @throws Exception
     */
    public static Countries getCountryFromName(String countryName) throws SQLException, Exception{
        JDBC.openConnection();
        String q = "SELECT * FROM countries WHERE Country = '" + countryName + "'";
        dbQuery.Query(q);
        Countries result;
        ResultSet resultSet = dbQuery.getResultSet();

        while (resultSet.next()){
            int countryID_A = resultSet.getInt("Country_ID");
            String countryName_A= resultSet.getString("Country");
            result = new Countries(countryID_A, countryName_A);
            return result;
        }
        JDBC.closeConnection();
        return null;
    }

    /**
     * Database query to return countries by id from countries table.
     * @param countryID
     * @return Countries observable list.
     * @throws SQLException
     * @throws Exception
     */
    public static Countries getCountryFromID(int countryID) throws SQLException, Exception{
        JDBC.openConnection();
        String q = "SELECT * FROM countries WHERE Country_ID = '" + countryID + "'";
        dbQuery.Query(q);
        Countries result;
        ResultSet resultSet = dbQuery.getResultSet();

        while (resultSet.next()){
            int countryID_B = resultSet.getInt("Country_ID");
            String countryName_B= resultSet.getString("Country");
            result = new Countries(countryID_B, countryName_B);
            return result;
        }
        JDBC.closeConnection();
        return null;
    }
}