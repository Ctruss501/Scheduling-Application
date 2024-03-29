package DAO;

import Model.Divisions;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * This is the DAO class for divisions.
 */
public class divisionsDAO {

    /**
     * Querying the database to get all divisions from the database.
     * @return
     */
    public static ObservableList<Divisions> getDivisions() {

        ObservableList<Divisions> divisions = FXCollections.observableArrayList();

        try {
            JDBC.openConnection();
            String q = "SELECT * FROM first_level_divisions";
            dbQuery.Query(q);
            ResultSet resultSet = dbQuery.getResultSet();

            while (resultSet.next()) {
                int divID = resultSet.getInt("Division_ID");
                String divDivision = resultSet.getString("Division");
                int countryID = resultSet.getInt("Country_ID");
                Divisions result = new Divisions(divID, divDivision, countryID);
                divisions.add(result);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return divisions;
    }
}
