package DAO;

import Model.Appointments;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.time.*;

/**
 * This is the DAO class for querying appointments.
 */
public class appointmentsDAO {

    /**
     * Query for getting all appointments from the database. Getting the customer name, username and contact name
     * so that the names will display in the tables instead of the IDs.
     * @return
     */
    public static ObservableList<Appointments> getAppointments(){

        ObservableList<Appointments> appointments = FXCollections.observableArrayList();
        try {
            JDBC.openConnection();
            String q = "SELECT Appointment_ID, Title, Description, Location, Type, Start, End, appointments.Customer_ID, customers.Customer_Name, appointments.User_ID, users.User_Name, " +
                    "appointments.Contact_ID, contacts.Contact_Name FROM appointments, customers, users, contacts WHERE appointments.Customer_ID = customers.Customer_ID " +
                    "AND appointments.User_ID = users.User_ID AND appointments.Contact_ID = contacts.Contact_ID";
            dbQuery.Query(q);
            ResultSet resultSet = dbQuery.getResultSet();

            while (resultSet.next()) {
                int appointmentID = resultSet.getInt("Appointment_ID");
                String title = resultSet.getString("Title");
                String description = resultSet.getString("Description");
                String location = resultSet.getString("Location");
                String type = resultSet.getString("Type");
                LocalDateTime start = resultSet.getTimestamp("Start").toLocalDateTime();
                LocalDateTime end = resultSet.getTimestamp("End").toLocalDateTime();
                String customer = resultSet.getString("Customer_Name");
                String user = resultSet.getString("User_Name");
                String contact = resultSet.getString("Contact_Name");
                Appointments result = new Appointments(appointmentID, title, description,
                        location, type, start, end, customer, user, contact);
                appointments.add(result);
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return appointments;
    }

    /**
     * Query for getting all appointments for current week from the database.
     * Getting the customer name, username and contact name so that the names will display in the tables
     * instead of the IDs.
     * @return
     */
    public static ObservableList<Appointments> getAppointmentsByWeek(){

        ObservableList<Appointments> appointments = FXCollections.observableArrayList();

        try {
            JDBC.openConnection();
            String q = "SELECT Appointment_ID, Title, Description, Location, Type, Start, End, appointments.Customer_ID, customers.Customer_Name, appointments.User_ID, users.User_Name, " +
                    "appointments.Contact_ID, contacts.Contact_Name FROM appointments, customers, users, contacts WHERE YEARWEEK(Start) = YEARWEEK(NOW()) " +
                    "AND appointments.Customer_ID = customers.Customer_ID AND appointments.User_ID = users.User_ID AND appointments.Contact_ID = contacts.Contact_ID";
            dbQuery.Query(q);
            ResultSet resultSet = dbQuery.getResultSet();

            while (resultSet.next()) {
                int appointmentID = resultSet.getInt("Appointment_ID");
                String title = resultSet.getString("Title");
                String description = resultSet.getString("Description");
                String location = resultSet.getString("Location");
                String type = resultSet.getString("Type");
                LocalDateTime start = resultSet.getTimestamp("Start").toLocalDateTime();
                LocalDateTime end = resultSet.getTimestamp("End").toLocalDateTime();
                String customer = resultSet.getString("Customer_Name");
                String user = resultSet.getString("User_Name");
                String contact = resultSet.getString("Contact_Name");
                Appointments result = new Appointments(appointmentID, title, description,
                        location, type, start, end, customer, user, contact);
                appointments.add(result);
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return appointments;
    }

    /**
     * Query for getting all appointments for current month from the database.
     * Getting the customer name, username and contact name so that the names will display in the tables
     * instead of the IDs.
     * @return
     */
    public static ObservableList<Appointments> getAppointmentsByMonth(){

        ObservableList<Appointments> appointments = FXCollections.observableArrayList();

        try {
            JDBC.openConnection();
            String q = "SELECT Appointment_ID, Title, Description, Location, Type, Start, End, appointments.Customer_ID, customers.Customer_Name, appointments.User_ID, users.User_Name, " +
                    "appointments.Contact_ID, contacts.Contact_Name FROM appointments, customers, users, contacts WHERE YEAR(curdate()) = Year(Start) AND MONTH(curdate()) = MONTH(Start) " +
                    "AND appointments.Customer_ID = customers.Customer_ID AND appointments.User_ID = users.User_ID AND appointments.Contact_ID = contacts.Contact_ID";
            dbQuery.Query(q);
            ResultSet resultSet = dbQuery.getResultSet();

            while (resultSet.next()) {
                int appointmentID = resultSet.getInt("Appointment_ID");
                String title = resultSet.getString("Title");
                String description = resultSet.getString("Description");
                String location = resultSet.getString("Location");
                String type = resultSet.getString("Type");
                LocalDateTime start = resultSet.getTimestamp("Start").toLocalDateTime();
                LocalDateTime end = resultSet.getTimestamp("End").toLocalDateTime();
                String customer = resultSet.getString("Customer_Name");
                String user = resultSet.getString("User_Name");
                String contact = resultSet.getString("Contact_Name");
                Appointments result = new Appointments(appointmentID, title, description,
                        location, type, start, end, customer, user, contact);
                appointments.add(result);
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return appointments;
    }

    /**
     * Query to execute when adding an appointment. Appointment ID is auto incremented from the database.
     * @param title
     * @param desc
     * @param location
     * @param type
     * @param start
     * @param end
     * @param customer
     * @param user
     * @param contact
     * @throws SQLException
     */
    public static void addAppointment(String title, String desc, String location, String type, LocalDateTime start, LocalDateTime end, int customer, int user, int contact) throws SQLException{

        try {
            Connection connection = JDBC.openConnection();
            String q = "INSERT INTO appointments VALUES(NULL, ?, ?, ?, ?, ?, ?, NOW(), 'user', NOW(), 'user', ?, ?, ?);";
            dbQuery.setPreparedStatement(connection, q);
            PreparedStatement preparedStatement = dbQuery.getPreparedStatement();

            preparedStatement.setString(1, title);
            preparedStatement.setString(2, desc);
            preparedStatement.setString(3, location);
            preparedStatement.setString(4, type);
            preparedStatement.setTimestamp(5, Timestamp.valueOf(start));
            preparedStatement.setTimestamp(6, Timestamp.valueOf(end));
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

    /**
     * Query to execute when editing an appointment.
     * @param apptID
     * @param title
     * @param desc
     * @param location
     * @param type
     * @param start
     * @param end
     * @param customer
     * @param user
     * @param contact
     * @throws SQLException
     */
    public static void editAppointment(int apptID, String title, String desc, String location, String type, LocalDateTime start, LocalDateTime end, int customer, int user, int contact) throws SQLException{

        try {
            Connection connection = JDBC.openConnection();
            String q = "UPDATE appointments SET Title = ?, Description = ?, Location = ?, Type = ?, Start = ?, End = ?," +
                    " Customer_ID = ?, User_ID = ?, Contact_ID = ?, Last_Update = NOW() WHERE Appointment_ID = ?;";
            dbQuery.setPreparedStatement(connection, q);
            PreparedStatement preparedStatement = dbQuery.getPreparedStatement();

            preparedStatement.setString(1, title);
            preparedStatement.setString(2, desc);
            preparedStatement.setString(3, location);
            preparedStatement.setString(4, type);
            preparedStatement.setTimestamp(5, Timestamp.valueOf(start));
            preparedStatement.setTimestamp(6, Timestamp.valueOf(end));
            preparedStatement.setInt(7, customer);
            preparedStatement.setInt(8, user);
            preparedStatement.setInt(9, contact);
            preparedStatement.setInt(10, apptID);

            preparedStatement.execute();
            preparedStatement.close();
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }

    /**
     * Query to execute when deleting an appointment.
     * @param appointment
     * @throws SQLException
     */
    public static void deleteAppointment(Appointments appointment) throws SQLException{

        try {
            Connection connection = JDBC.openConnection();
            String q = "DELETE FROM appointments WHERE Appointment_ID = ?";
            dbQuery.setPreparedStatement(connection, q);
            PreparedStatement preparedStatement = dbQuery.getPreparedStatement();

            preparedStatement.setInt(1, appointment.getApptID());

            preparedStatement.execute();
            preparedStatement.close();
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }

    /**
     * Querying the database for the total customer appointments report. Setting just the type and total columns.
     * Counting the total appointments where the month is equal to the selection. Grouping by type.
     * @param monthSelection
     * @return
     */
    public static ObservableList<Appointments> totalCustAppointments(int monthSelection) {

        ObservableList<Appointments> appointments = FXCollections.observableArrayList();
        try {
            JDBC.openConnection();
            String q = "SELECT Count(*) AS Total, Type AS Type FROM appointments WHERE MONTH(Start) = '" + monthSelection + "' GROUP BY Type";
            dbQuery.Query(q);
            ResultSet resultSet = dbQuery.getResultSet();

            while (resultSet.next()) {
                String type = resultSet.getString("Type");
                int total = resultSet.getInt("Total");
                Appointments result = new Appointments(type, total);
                appointments.add(result);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return appointments;
    }

    /**
     * Querying database for contact's appointments report. Getting the specified data from the columns
     * based on the contactID matching the selection of a contact. Grouped by appointmentID for duplicates.
     * @param contactSelection
     * @return
     */
    public static ObservableList<Appointments> contactAppointments(int contactSelection){

        ObservableList<Appointments> appointments = FXCollections.observableArrayList();
        try {
            JDBC.openConnection();
            String q = "SELECT Appointment_ID, Title, Type, Description, Start, End, appointments.Customer_ID, customers.Customer_Name, " +
                    "appointments.Contact_ID, contacts.Contact_Name FROM appointments, customers, contacts WHERE " +
                    "appointments.Customer_ID = customers.Customer_ID AND appointments.Contact_ID = '" + contactSelection + "' GROUP BY Appointment_ID";
            dbQuery.Query(q);
            ResultSet resultSet = dbQuery.getResultSet();

            while (resultSet.next()) {
                int appointmentID = resultSet.getInt("Appointment_ID");
                String title = resultSet.getString("Title");
                String type = resultSet.getString("Type");
                String description = resultSet.getString("Description");
                LocalDateTime start = resultSet.getTimestamp("Start").toLocalDateTime();
                LocalDateTime end = resultSet.getTimestamp("End").toLocalDateTime();
                String customer = resultSet.getString("Customer_Name");
                Appointments result = new Appointments(appointmentID, title, type,
                        description, start, end, customer);
                appointments.add(result);
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return appointments;
    }

    /**
     * Querying the database for additional report past appointments. Gets the values for all the specified columns
     * where the start of the appointment is before now.
     * @return
     */
    public static ObservableList<Appointments> pastAppointments(){

        ObservableList<Appointments> appointments = FXCollections.observableArrayList();

        try {
            JDBC.openConnection();
            String q = "SELECT Appointment_ID, Title, Description, Location, Type, Start, End, appointments.Customer_ID, customers.Customer_Name, appointments.User_ID, users.User_Name, " +
                    "appointments.Contact_ID, contacts.Contact_Name FROM appointments, customers, users, contacts WHERE TIMESTAMP(Start) < TIMESTAMP(NOW()) " +
                    "AND appointments.Customer_ID = customers.Customer_ID AND appointments.User_ID = users.User_ID AND appointments.Contact_ID = contacts.Contact_ID";
            dbQuery.Query(q);
            ResultSet resultSet = dbQuery.getResultSet();

            while (resultSet.next()) {
                int appointmentID = resultSet.getInt("Appointment_ID");
                String title = resultSet.getString("Title");
                String description = resultSet.getString("Description");
                String location = resultSet.getString("Location");
                String type = resultSet.getString("Type");
                LocalDateTime start = resultSet.getTimestamp("Start").toLocalDateTime();
                LocalDateTime end = resultSet.getTimestamp("End").toLocalDateTime();
                String customer = resultSet.getString("Customer_Name");
                String user = resultSet.getString("User_Name");
                String contact = resultSet.getString("Contact_Name");
                Appointments result = new Appointments(appointmentID, title, description,
                        location, type, start, end, customer, user, contact);
                appointments.add(result);
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return appointments;
    }
}
