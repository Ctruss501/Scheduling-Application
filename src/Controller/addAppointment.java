package Controller;

import DAO.*;
import Model.Appointments;
import Model.Contacts;
import Model.Customers;
import Model.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.converter.LocalTimeStringConverter;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.ResourceBundle;

/**
 * This controller class is for adding new appointments.
 */
public class addAppointment implements Initializable {

    public TextField apptIDTextField;
    public TextField titleTextField;
    public TextField locationTextField;
    public DatePicker startDatePicker;
    public ComboBox<String> typeCombo;
    public ComboBox<LocalTime> startTimeCombo;
    public ComboBox<LocalTime> endTimeCombo;
    public ComboBox<Contacts> contactCombo;
    public ComboBox<Customers> custCombo;
    public ComboBox<User> userCombo;
    public TextField descTextField;

    /**
     * Observable list for the start and end combo boxes so that the times available to be chosen
     * are between 8am and 10pm.
     * @return
     */
    public  ObservableList<LocalTime> time(){

        ObservableList<LocalTime> timeOL = FXCollections.observableArrayList();

        //ZoneId for the user's system.
        ZoneId systemZone = ZoneId.systemDefault();
        //ZoneId for est.
        ZoneId est = ZoneId.of("America/New_York");

        //Setting start and end time within business hours of 8a-10p est.
        LocalTime businessStart = LocalTime.of(8, 0);
        LocalTime businessEnd = LocalTime.of(22, 0);

        //Convert local times to zoned date and time est.
        ZonedDateTime estStartZDT = ZonedDateTime.of(LocalDate.now(), businessStart, est);
        ZonedDateTime estEndZDT = ZonedDateTime.of(LocalDate.now(), businessEnd, est);

        //Convert zoned date and times of est to user's local zone.
        ZonedDateTime systemStartZDT = estStartZDT.withZoneSameInstant(systemZone);
        ZonedDateTime systemEndZDT = estEndZDT.withZoneSameInstant(systemZone);

        //Converting the user's system zoned date and time to just local time for populating the combo boxes.
        LocalTime start = systemStartZDT.toLocalTime();
        LocalTime end = systemEndZDT.toLocalTime();


        //LocalTime start = LocalTime.of(8, 0);
        //LocalTime end = LocalTime.of(22,0);
        //while(start.isBefore(end.plusSeconds(1))){
            //timeOL.add(LocalTime.parse(String.valueOf(start)));
            //start = start.plusMinutes(15);
        //}
        while (start.isBefore(end.plusSeconds(1))){
            timeOL.add(LocalTime.parse(String.valueOf(start)));
            start = start.plusMinutes(15);
        }
        return timeOL;
    }

    /**
     * Setting the values in their correlating combo boxes. For start and end combos,
     * converting them to show the time in 12-hour clock am and pm instead of 24-hour clock.
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("hh:mm a");

        typeCombo.getItems().addAll("In-Person", "Virtual", "Customer Welcome", "De-Briefing", "Planning Session");
        userCombo.setItems(userDAO.getUsers());
        custCombo.setItems(customersDAO.getCustomers());
        contactCombo.setItems(contactsDAO.getContacts());
        startTimeCombo.setItems(time());
        startTimeCombo.setConverter(new LocalTimeStringConverter(timeFormatter, timeFormatter));
        endTimeCombo.setItems(time());
        endTimeCombo.setConverter(new LocalTimeStringConverter(timeFormatter, timeFormatter));
    }

    /**
     * Handles the save button. Gets the values from the fields to save to the database. Alerts are in
     * place to show error messages for any fields that do not have values. Also handles time
     * conversion and checks for any discrepancies with chosen times.
     * @param actionEvent
     * @throws IOException
     * @throws SQLException
     */
    public void saveOnAction(ActionEvent actionEvent) throws IOException, SQLException {

        String title = titleTextField.getText();
        if (titleTextField.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Title Empty");
            alert.setContentText("The appointment must have a title.");
            alert.showAndWait();
            return;
        }
        String type = typeCombo.getSelectionModel().getSelectedItem();
        if (typeCombo.getSelectionModel().getSelectedItem() == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Type Empty");
            alert.setContentText("The appointment must have a type.");
            alert.showAndWait();
            return;
        }
        String location = locationTextField.getText();
        if (locationTextField.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Location Empty");
            alert.setContentText("The appointment must have a location.");
            alert.showAndWait();
            return;
        }
        String desc = descTextField.getText();
        if (descTextField.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Description Empty");
            alert.setContentText("The appointment must have a description.");
            alert.showAndWait();
            return;
        }
        if (startDatePicker.getValue() == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Select Appointment Date");
            alert.setContentText("Please select a date for the appointment.");
            alert.showAndWait();
            return;
        }
        if(startDatePicker.getValue().isBefore(LocalDate.now())){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Appointment Date");
            alert.setContentText("Appointment Date can't be before today.");
            alert.showAndWait();
            return;
        }
        if(startTimeCombo.getValue() == null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Select Appointment Start Time");
            alert.setContentText("Please select a start time for the appointment.");
            alert.showAndWait();
            return;
        }
        if(endTimeCombo.getValue() == null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Select Appointment End Time");
            alert.setContentText("Please select an end time for the appointment.");
            alert.showAndWait();
            return;
        }

        //Combining the date picker with the time combos
        LocalDateTime selectedStart = LocalDateTime.of(startDatePicker.getValue(), startTimeCombo.getValue());
        LocalDateTime selectedEnd = LocalDateTime.of(startDatePicker.getValue(), endTimeCombo.getValue());

        //ZoneId for the user's system.
        ZoneId systemZone = ZoneId.systemDefault();
        //ZoneId for est.
        ZoneId est = ZoneId.of("America/New_York");

        LocalTime businessStart = LocalTime.of(8, 0);
        LocalTime businessEnd = LocalTime.of(22, 0);

        //Selected start and end time to system's zone.
        ZonedDateTime systemStartZDT = ZonedDateTime.of(selectedStart, systemZone);
        ZonedDateTime systemEndZDT = ZonedDateTime.of(selectedEnd, systemZone);

        //Selected start and end time with system's zone converted to est.
        ZonedDateTime estStartZDT = systemStartZDT.withZoneSameInstant(est);
        ZonedDateTime estEndZDT = systemEndZDT.withZoneSameInstant(est);

        //Selected start and end time that was converted to est, converting to
        //local date and time for comparison to business hours, which are 8a-10p est.
        LocalTime businessStartEst = estStartZDT.toLocalDateTime().toLocalTime();
        LocalTime businessEndEst = estEndZDT.toLocalDateTime().toLocalTime();

        if(startTimeCombo.getValue().isAfter(endTimeCombo.getValue()) || startTimeCombo.getValue().equals(endTimeCombo.getValue())){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Selected Appointment Time");
            alert.setContentText("Make sure appointment start time is before end time.");
            alert.showAndWait();
            return;
        }
        if(startDatePicker.getValue().equals(LocalDate.now())){
            if(startTimeCombo.getValue().isBefore(LocalTime.now())){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Appointment Date and Time");
                alert.setContentText("Appointments must take place in the future.");
                alert.showAndWait();
                return;
            }
        }
        if(businessStartEst.isBefore(businessStart) || businessEndEst.isAfter(businessEnd)){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Appointment Date and Time");
            alert.setContentText("Appointments must be scheduled within EST business hours: 8:00 am to 10:00 pm.");
            alert.showAndWait();
            return;
        }
        Customers customer = custCombo.getSelectionModel().getSelectedItem();
        if(custCombo.getSelectionModel().getSelectedItem() == null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Select a Customer");
            alert.setContentText("Please select a customer for the appointment.");
            alert.showAndWait();
            return;
        }
        //Checking exiting appointments by the customer id that is chosen for the appointment.
        //If the appointment end or start are within the time range, the appointments overlap
        //and cannot be scheduled.
        for(int i = 0; i < appointmentsDAO.getAppointments().size(); i++){
            Appointments appointments = appointmentsDAO.getAppointments().get(i);
            if(Objects.equals(appointments.getCustomer(), customer.getCustName())){
                //Checking the selected start and end time for adding appointment. If it is within the time range that is 30 minutes
                //prior to the exising start and 30 minutes after the existing end, the appointment cannot be scheduled for
                //this selected time.
                if(selectedStart.isBefore(appointments.getEnd().plusMinutes(30)) && selectedEnd.isAfter(appointments.getStart().minusMinutes(30))){
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Overlap");
                    alert.setContentText("The chosen customer has another appointment around this time.");
                    alert.showAndWait();
                    return;
                }
            }
        }
        User user = userCombo.getSelectionModel().getSelectedItem();
        if(userCombo.getSelectionModel().getSelectedItem() == null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Select a User");
            alert.setContentText("Please select a user for the appointment.");
            alert.showAndWait();
            return;
        }
        Contacts contact = contactCombo.getSelectionModel().getSelectedItem();
        if(contactCombo.getSelectionModel().getSelectedItem() == null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Select a Contact");
            alert.setContentText("Please select a contact for the appointment.");
            alert.showAndWait();
            return;
        }

        appointmentsDAO.addAppointment(title, desc, location, type, selectedStart, selectedEnd, customer.getCustID(), user.getUserID(), contact.getContactID());

        Parent root = FXMLLoader.load(getClass().getResource("../view/mainForm.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1300, 530);
        stage.setTitle("Scheduling Application - Main");
        stage.setScene(scene);
        stage.show();
        stage.centerOnScreen();
        stage.setResizable(false);
    }

    /**
     * Handles the cancel button. When pressed, will send back to the main form/appointment table.
     * @param actionEvent
     * @throws IOException
     */
    public void cancelOnAction(ActionEvent actionEvent) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("../view/mainForm.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1300, 530);
        stage.setTitle("Scheduling Application - Main");
        stage.setScene(scene);
        stage.show();
        stage.centerOnScreen();
        stage.setResizable(false);
    }
}
