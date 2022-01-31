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

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.*;
import java.time.chrono.ChronoLocalDateTime;
import java.time.chrono.ChronoZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ResourceBundle;

public class addAppointment implements Initializable {
    public TextField apptIDTextField;
    public TextField titleTextField;
    public TextField typeTextField;
    public TextField locationTextField;
    public DatePicker startDatePicker;
    public ComboBox<LocalTime> startTimeCombo;
    public ComboBox<LocalTime> endTimeCombo;
    public ComboBox<Contacts> contactCombo;
    public ComboBox<Customers> custCombo;
    public ComboBox<User> userCombo;
    public TextField descTextField;

    public static ObservableList<LocalTime> time(){

        ObservableList<LocalTime> timeOL = FXCollections.observableArrayList();

        LocalTime start = LocalTime.of(8, 0);
        LocalTime end = LocalTime.of(22,0);
        while(start.isBefore(end.plusSeconds(1))){
            timeOL.add(LocalTime.parse(String.valueOf(start)));
            start = start.plusMinutes(15);
        }
        return timeOL;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        userCombo.setItems(userDAO.getUsers());
        custCombo.setItems(customersDAO.getCustomers());
        contactCombo.setItems(contactsDAO.getContacts());
        startTimeCombo.setItems(time());
        endTimeCombo.setItems(time());
    }

    public void saveOnAction(ActionEvent actionEvent) throws IOException {

        String title = titleTextField.getText();
        if(titleTextField.getText().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Title Empty");
            alert.setContentText("The appointment must have a title.");
            alert.showAndWait();
            return;
        }
        String type = typeTextField.getText();
        if(typeTextField.getText().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Type Empty");
            alert.setContentText("The appointment must have a type.");
            alert.showAndWait();
            return;
        }
        String location = locationTextField.getText();
        if(locationTextField.getText().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Location Empty");
            alert.setContentText("The appointment must have a location.");
            alert.showAndWait();
            return;
        }
        String desc = descTextField.getText();
        if(descTextField.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Description Empty");
            alert.setContentText("The appointment must have a description.");
            alert.showAndWait();
            return;
        }
        //Combining the date picker with the time combos
        LocalDateTime selectedStart = LocalDateTime.of(startDatePicker.getValue(), startTimeCombo.getValue());
        LocalDateTime selectedEnd = LocalDateTime.of(startDatePicker.getValue(), endTimeCombo.getValue());

        LocalTime businessStart = LocalTime.of(8, 0);
        LocalTime businessEnd = LocalTime.of(22,0);
        //ZoneId for the user's system.
        ZoneId systemZone = ZoneId.systemDefault();
        //ZoneId for est.
        ZoneId est = ZoneId.of("America/New_York");

        //Setting the user's system ZoneId times to Eastern.
        ZonedDateTime estStartZDT = ZonedDateTime.of(LocalDate.now(), businessStart, est);
        ZonedDateTime estEndZDT = ZonedDateTime.of(LocalDate.now(), businessEnd, est);
        //ZonedDateTime estStartZDT = systemStartZDT.withZoneSameInstant(est);
        //ZonedDateTime estEndZDT = systemEndZDT.withZoneSameInstant(est);

        //Start and end times according to user's system ZoneId.
        ZonedDateTime systemStartZDT = estStartZDT.withZoneSameInstant(systemZone);
        ZonedDateTime systemEndZDT = estEndZDT.withZoneSameInstant(systemZone);
        //ZonedDateTime systemStartZDT = ZonedDateTime.of(selectedStart, systemZone);
        //ZonedDateTime systemEndZDT = ZonedDateTime.of(selectedEnd, systemZone);

        //Checking against est before storing in database.
        //LocalDateTime startTimeEst = LocalDateTime.from(estStartZDT.toLocalDateTime().toLocalTime());
        //LocalDateTime endTimeEst = LocalDateTime.from(estEndZDT.toLocalDateTime().toLocalTime());

        if(startDatePicker.getValue() == null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Select Appointment Date");
            alert.setContentText("Please select a date for the appointment.");
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
        if(systemStartZDT.isBefore(estStartZDT) || systemEndZDT.isAfter(estEndZDT)){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Appointment Date and Time");
            alert.setContentText("Appointments must be scheduled within EST business hours: 8:00 am to 10:00 pm.");
            alert.showAndWait();
            return;
        }
        for(Appointments appointments : appointmentsDAO.getAppointments()){
        }
        /*if(businessStart.isAfter(businessEnd) || businessStart.equals(businessEnd)){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Selected Appointment Time");
            alert.setContentText("Make sure appointment start time is before end time.");
            alert.showAndWait();
            return;
        }
        if (startTimeEst.isBefore(ChronoLocalDateTime.from(businessStart)) || endTimeEst.isAfter(ChronoLocalDateTime.from(businessEnd))) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Selected Appointment Time");
            alert.setContentText("Appointment times must be within EST business hours.");
            alert.showAndWait();
            return;
        }*/


        int customer = custCombo.getValue().getCustID();
        if(custCombo.getSelectionModel().getSelectedItem() == null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Select a Customer");
            alert.setContentText("Please select a customer for the appointment.");
            alert.showAndWait();
            return;
        }
        int contact = contactCombo.getValue().getContactID();
        if(contactCombo.getSelectionModel().getSelectedItem() == null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Select a Contact");
            alert.setContentText("Please select a contact for the appointment.");
            alert.showAndWait();
            return;
        }
        int user = userCombo.getValue().getUserID();
        if(userCombo.getSelectionModel().getSelectedItem() == null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Select a User");
            alert.setContentText("Please select a user for the appointment.");
            alert.showAndWait();
        }

        try{
            JDBC.openConnection();
            appointmentsDAO.addAppointment(title, type, location, desc, selectedStart, selectedEnd, customer, contact, user);
        }
        catch (SQLException e){
            e.printStackTrace();
        }

        Parent root = FXMLLoader.load(getClass().getResource("../view/mainForm.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1300, 530);
        stage.setTitle("Scheduling Application - Main");
        stage.setScene(scene);
        stage.show();
        stage.centerOnScreen();
        stage.setResizable(false);
    }

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
