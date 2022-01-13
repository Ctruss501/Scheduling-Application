package DAO;

import javax.sql.rowset.CachedRowSet;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import static DAO.JDBC.connection;
import DAO.JDBC;

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

    /**
     * For inserting, updating, and deleting
     * @param execute
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public static void executeUpdate(String execute) throws SQLException, ClassNotFoundException{
        try {
            JDBC.openConnection();
            statement = connection.createStatement();
            statement.executeUpdate(execute);
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        finally {
            if(statement != null){
                statement.close();
            }
            JDBC.closeConnection();
        }
    }
}
