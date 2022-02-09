package DAO;

import java.sql.*;

import static DAO.JDBC.connection;

/**
 * This is the DAO class for querying the database.
 */
public class dbQuery {

    private static String sqlQuery;
    private static Statement statement;
    private static ResultSet resultSet;
    private static PreparedStatement preparedStatement;

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

    /*public static void executeUpdate(String execute) throws SQLException, ClassNotFoundException{
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
    }*/

    /**
     * Prepared statement to execute when inserting, updating and deleting.
     * @param connection
     * @param sqlStatement
     * @throws SQLException
     */
    public static void setPreparedStatement(Connection connection, String sqlStatement) throws SQLException{

        preparedStatement = connection.prepareStatement(sqlStatement);
    }

    public static PreparedStatement getPreparedStatement(){
        return preparedStatement;
    }
}
