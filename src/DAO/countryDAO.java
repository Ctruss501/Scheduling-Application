package DAO;

import Model.Countries;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * This is the DAO for countries.
 */
public class countryDAO {

    /**
     * Query the database for all countries.
     * @throws SQLException
     * @throws Exception
     */
    public static ObservableList<Countries> getCountries(){

        ObservableList<Countries> countries = FXCollections.observableArrayList();

        try {
            JDBC.openConnection();
            String q = "SELECT * FROM countries";
            dbQuery.Query(q);
            ResultSet resultSet = dbQuery.getResultSet();

            while (resultSet.next()) {
                int countryID = resultSet.getInt("Country_ID");
                String countryName = resultSet.getString("Country");
                Countries result = new Countries(countryID, countryName);
                countries.add(result);
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return countries;
    }
}
