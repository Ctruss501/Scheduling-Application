package DAO;

import java.sql.ResultSet;
import java.sql.Statement;
import static DAO.JDBC.connection;

public class dbQuery {

    private static String sqlQuery;
    private static Statement statement;
    private static ResultSet resultSet;

    public static void Query(String query) {
        sqlQuery = query;

        try {
            statement = connection.createStatement();

            if (sqlQuery.toLowerCase().startsWith("select"))
                resultSet = statement.executeQuery(query);

            if (sqlQuery.toLowerCase().startsWith("delete") || sqlQuery.toLowerCase().startsWith("insert") || sqlQuery.toLowerCase().startsWith("update"))
                statement.executeUpdate(query);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public static ResultSet getResultSet() {
        return resultSet;
    }
}
