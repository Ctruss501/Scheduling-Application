package DAO;

import Model.Appointments;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

public class appointmentsDAO {

    public static ObservableList<Appointments> getAppointments() throws SQLException, Exception{
        ObservableList<Appointments> appointments = FXCollections.observableArrayList();
        JDBC.openConnection();
        String q = "SELECT * FROM appointments";
        dbQuery.Query(q);
        ResultSet resultSet = dbQuery.getResultSet();

        while (resultSet.next()){
            int appointmentID = resultSet.getInt("Appointment_ID");
            String title = resultSet.getString("Title");
            String description = resultSet.getString("Description");
            String location = resultSet.getString("Location");
            String type = resultSet.getString("Type");
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy H:mm").withZone(ZoneOffset.UTC);
            Date start = (Date) dateTimeFormatter.parse(resultSet.getString("Start"));
            Date end = (Date) dateTimeFormatter.parse(resultSet.getString("End"));
            int customerID = resultSet.getInt("Customer_ID");
            int userID = resultSet.getInt("User_ID");
            int contactID = resultSet.getInt("Contact_ID");
            Appointments result = new Appointments(appointmentID, title, description,
                    location, type, start, end, customerID, userID, contactID);
            appointments.add(result);
        }
        JDBC.closeConnection();
        return appointments;
    }

    public static Appointments getAppointmentsByWeek(String appointmentId) throws SQLException, Exception{

        JDBC.openConnection();
        String q = "SELECT * FROM appointments WHERE Appointment_ID = '" + appointmentId + "'" + "AND WEEK(Start) = WEEK(now(), 0)";
        dbQuery.Query(q);
        Appointments result;
        ResultSet resultSet = dbQuery.getResultSet();

        while (resultSet.next()){
            int appointmentID = resultSet.getInt("Appointment_ID");
            String title = resultSet.getString("Title");
            String description = resultSet.getString("Description");
            String location = resultSet.getString("Location");
            String type = resultSet.getString("Type");
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy H:mm").withZone(ZoneOffset.UTC);
            Date start = (Date) dateTimeFormatter.parse(resultSet.getString("Start"));
            Date end = (Date) dateTimeFormatter.parse(resultSet.getString("End"));
            int customerID = resultSet.getInt("Customer_ID");
            int userID = resultSet.getInt("User_ID");
            int contactID = resultSet.getInt("Contact_ID");
            result = new Appointments(appointmentID, title, description,
                    location, type, start, end, customerID, userID, contactID);
            return result;
        }
        JDBC.closeConnection();
        return null;
    }
}
