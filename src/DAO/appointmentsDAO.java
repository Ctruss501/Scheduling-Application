package DAO;

import Model.Appointments;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.*;
import java.time.format.DateTimeFormatter;

public class appointmentsDAO {

    public static ObservableList<Appointments> getAppointments() throws SQLException, Exception{

        ObservableList<Appointments> appointments = FXCollections.observableArrayList();
        try {
            JDBC.openConnection();
            String q = "SELECT * FROM appointments";
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
                int customerID = resultSet.getInt("Customer_ID");
                int userID = resultSet.getInt("User_ID");
                int contactID = resultSet.getInt("Contact_ID");
                Appointments result = new Appointments(appointmentID, title, description,
                        location, type, start, end, customerID, userID, contactID);
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

    public static Appointments getAppointmentsByWeek(String appointmentId) throws SQLException, Exception{

        try {
            JDBC.openConnection();
            String q = "SELECT * FROM appointments WHERE Appointment_ID = '" + appointmentId + "'" + "AND YEARWEEK(Week) = YEARWEEK(NOW))";
            dbQuery.Query(q);
            Appointments result;
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
                int customerID = resultSet.getInt("Customer_ID");
                int userID = resultSet.getInt("User_ID");
                int contactID = resultSet.getInt("Contact_ID");
                result = new Appointments(appointmentID, title, description,
                        location, type, start, end, customerID, userID, contactID);
                return result;
            }
            JDBC.closeConnection();
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    public static Appointments getAppointmentsByMonth(String appointmentId) throws SQLException, Exception{

        try {
            JDBC.openConnection();
            String q = "SELECT * FROM appointments WHERE Appointment_ID = '" + appointmentId + "'" + "AND YEAR(curdate()) = Year(Start)" +
                    "AND MONTH(curdate()) = MONTH(Start)";
            dbQuery.Query(q);
            Appointments result;
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

                int customerID = resultSet.getInt("Customer_ID");
                int userID = resultSet.getInt("User_ID");
                int contactID = resultSet.getInt("Contact_ID");
                result = new Appointments(appointmentID, title, description,
                        location, type, start, end, customerID, userID, contactID);
                return result;
            }
            JDBC.closeConnection();
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }
}
