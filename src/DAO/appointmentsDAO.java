package DAO;

import Model.Appointments;
import Model.Customers;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.*;
import java.time.format.DateTimeFormatter;

public class appointmentsDAO {

    public static ObservableList<Appointments> getAppointments(){

        ObservableList<Appointments> appointments = FXCollections.observableArrayList();
        try {
            JDBC.openConnection();
            String q = "SELECT * FROM appointments, users, customers, contacts WHERE appointments.Customer_ID = customers.Customer_ID " +
                    "AND appointments.User_ID = users.User_ID AND appointments.Contact_ID = contacts.Contact_ID";
            dbQuery.Query(q);
            ResultSet resultSet = dbQuery.getResultSet();
            //ZonedDateTime zonedDateTime = ZonedDateTime.now();
            //DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss a z");
            //String start = zonedDateTime.format(dateTimeFormatter);
            //String end = zonedDateTime.format(dateTimeFormatter);

            while (resultSet.next()) {
                int appointmentID = resultSet.getInt("Appointment_ID");
                String title = resultSet.getString("Title");
                String description = resultSet.getString("Description");
                String location = resultSet.getString("Location");
                String type = resultSet.getString("Type");
                //DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy H:mm").withZone(ZoneOffset.UTC);
                LocalDateTime start = resultSet.getTimestamp("Start").toLocalDateTime();
                LocalDateTime end = resultSet.getTimestamp("End").toLocalDateTime();
                String customer = resultSet.getString(23);
                String user = resultSet.getString(16);
                String contact = resultSet.getString(33);
                Appointments result = new Appointments(appointmentID, title, description,
                        location, type, start, end, customer, user, contact);
                appointments.add(result);
            }
            JDBC.closeConnection();
            //return appointments;
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return appointments;
    }

    public static ObservableList<Appointments> getAppointmentsByWeek(){

        ObservableList<Appointments> appointments = FXCollections.observableArrayList();

        try {
            JDBC.openConnection();
            //String q = "SELECT * FROM appointments WHERE Appointment_ID = '" + appointmentId + "'" + "AND YEARWEEK(Week) = YEARWEEK(NOW))";
            String q = "SELECT * FROM appointments, users, customers, contacts WHERE YEARWEEK(Start) = YEARWEEK(NOW()) AND appointments.Customer_ID = customers.Customer_ID " +
                    "AND appointments.User_ID = users.User_ID AND appointments.Contact_ID = contacts.Contact_ID";
            dbQuery.Query(q);
            //Appointments result;
            ResultSet resultSet = dbQuery.getResultSet();
            //DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy hh:mm:ss a z");

            while (resultSet.next()) {
                int appointmentID = resultSet.getInt("Appointment_ID");
                String title = resultSet.getString("Title");
                String description = resultSet.getString("Description");
                String location = resultSet.getString("Location");
                String type = resultSet.getString("Type");
                //DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy H:mm").withZone(ZoneOffset.UTC);
                //ZonedDateTime start = ZonedDateTime.parse(String.format(resultSet.getString("Start"), dateTimeFormatter));
                //ZonedDateTime end = ZonedDateTime.parse(String.format(resultSet.getString("End"), dateTimeFormatter));
                LocalDateTime start = resultSet.getTimestamp("Start").toLocalDateTime();
                LocalDateTime end = resultSet.getTimestamp("End").toLocalDateTime();
                String customer = resultSet.getString(23);
                String user = resultSet.getString(16);
                String contact = resultSet.getString(33);
                Appointments result = new Appointments(appointmentID, title, description,
                        location, type, start, end, customer, user, contact);
                appointments.add(result);
            }
            JDBC.closeConnection();
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return appointments;
    }

    public static ObservableList<Appointments> getAppointmentsByMonth(){

        ObservableList<Appointments> appointments = FXCollections.observableArrayList();

        try {
            JDBC.openConnection();
            String q = "SELECT * FROM appointments, users, customers, contacts WHERE YEAR(curdate()) = Year(Start) AND MONTH(curdate()) = MONTH(Start) " +
                    "AND appointments.Customer_ID = customers.Customer_ID AND appointments.User_ID = users.User_ID AND appointments.Contact_ID = contacts.Contact_ID";
            dbQuery.Query(q);
            //Appointments result;
            ResultSet resultSet = dbQuery.getResultSet();
            //DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy hh:mm:ss a z");

            while (resultSet.next()) {
                int appointmentID = resultSet.getInt("Appointment_ID");
                String title = resultSet.getString("Title");
                String description = resultSet.getString("Description");
                String location = resultSet.getString("Location");
                String type = resultSet.getString("Type");
                //DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy H:mm").withZone(ZoneOffset.UTC);
                //ZonedDateTime start = ZonedDateTime.parse(String.format(resultSet.getString("Start"), dateTimeFormatter));
                //ZonedDateTime end = ZonedDateTime.parse(String.format(resultSet.getString("End"), dateTimeFormatter));
                LocalDateTime start = resultSet.getTimestamp("Start").toLocalDateTime();
                LocalDateTime end = resultSet.getTimestamp("End").toLocalDateTime();
                String customer = resultSet.getString(23);
                String user = resultSet.getString(16);
                String contact = resultSet.getString(33);
                Appointments result = new Appointments(appointmentID, title, description,
                        location, type, start, end, customer, user, contact);
                appointments.add(result);
            }
            JDBC.closeConnection();
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return appointments;
    }

    public static void addAppointment(String title, String desc, String location, String type, String start, String end, int customer, int user, int contact) throws SQLException{

        try {
            Connection connection = JDBC.openConnection();
            String q = "INSERT INTO appointments VALUES(NULL, ?, ?, ?, ?, ?, ?, NOW(), 'user', NOW(), 'user', ?, ?, ?);";
            dbQuery.setPreparedStatement(connection, q);
            PreparedStatement preparedStatement = dbQuery.getPreparedStatement();

            preparedStatement.setString(1, title);
            preparedStatement.setString(2, desc);
            preparedStatement.setString(3, location);
            preparedStatement.setString(4, type);
            preparedStatement.setString(5, start);
            preparedStatement.setString(6, end);
            preparedStatement.setInt(7, customer);
            preparedStatement.setInt(8, user);
            preparedStatement.setInt(9, contact);

            preparedStatement.execute();
            preparedStatement.close();
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }

    public static void editAppointment(int apptID, String title, String desc, String location, String type, String start, String end, int customer, int user, int contact) throws SQLException{

        try {
            Connection connection = JDBC.openConnection();
            String q = "UPDATE appointments SET Title = ?, Description = ?, Location = ?, Type = ?, Start = ?, End = NOW()," +
                    " Last_Update = NOW(), Customer_ID = ?, User_ID = ?, Contact_ID = ? WHERE Appointment_ID = ?;";
            dbQuery.setPreparedStatement(connection, q);
            PreparedStatement preparedStatement = dbQuery.getPreparedStatement();

            preparedStatement.setString(1, title);
            preparedStatement.setString(2, desc);
            preparedStatement.setString(3, location);
            preparedStatement.setString(4, type);
            preparedStatement.setString(5, start);
            preparedStatement.setString(6, end);
            preparedStatement.setInt(7, customer);
            preparedStatement.setInt(8, user);
            preparedStatement.setInt(9, contact);
            preparedStatement.setInt(10, apptID);

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
            String q = "DELETE FROM appointments WHERE Appointment_ID = ?";
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
