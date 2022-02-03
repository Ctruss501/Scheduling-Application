package Controller;

import DAO.*;
import Model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.ResourceBundle;

public class editAppointment implements Initializable {
    public TextField apptIDTextField;
    public TextField titleTextField;
    public TextField typeTextField;
    public TextField locationTextField;
    public TextField descTextField;
    public DatePicker startDatePicker;
    public ComboBox<LocalTime> startTimeCombo;
    public ComboBox<LocalTime> endTimeCombo;
    public ComboBox<Customers> custCombo;
    public ComboBox<Contacts> contactCombo;
    public ComboBox<User> userCombo;


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

        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("hh:mm a");

        userCombo.setItems(userDAO.getUsers());
        custCombo.setItems(customersDAO.getCustomers());
        contactCombo.setItems(contactsDAO.getContacts());
        startTimeCombo.setItems(time());
        startTimeCombo.setConverter(new LocalTimeStringConverter(timeFormatter, timeFormatter));
        endTimeCombo.setItems(time());
        endTimeCombo.setConverter(new LocalTimeStringConverter(timeFormatter, timeFormatter));
    }

    public void saveOnAction(ActionEvent actionEvent) throws IOException, SQLException {

        int apptID = Integer.parseInt(apptIDTextField.getText());

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
        if(descTextField.getText().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Description Empty");
            alert.setContentText("The appointment must have a description.");
            alert.showAndWait();
            return;
        }
        LocalDateTime start = LocalDateTime.of(startDatePicker.getValue(), startTimeCombo.getValue());
        LocalDateTime end = LocalDateTime.of(startDatePicker.getValue(), endTimeCombo.getValue());
        if(startDatePicker.getValue() == null || startTimeCombo.getSelectionModel().getSelectedItem() == null || endTimeCombo.getSelectionModel().getSelectedItem() == null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Date and Time Selection");
            alert.setContentText("Please select a proper date and time for the appointment.");
            alert.showAndWait();
            return;
        }
        int customer = custCombo.getValue().getCustID();
        if(custCombo.getSelectionModel().getSelectedItem() == null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Select a Customer");
            alert.setContentText("Please select a customer for the appointment.");
            alert.showAndWait();
            return;
        }
        User user = userCombo.getSelectionModel().getSelectedItem();
        if(userCombo.getSelectionModel().getSelectedItem() == null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Select a User");
            alert.setContentText("Please select a user for the appointment.");
            alert.showAndWait();
            return;
        }
        int contact = contactCombo.getValue().getContactID();
        if(contactCombo.getSelectionModel().getSelectedItem() == null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Select a Contact");
            alert.setContentText("Please select a contact for the appointment.");
            alert.showAndWait();
        }


            appointmentsDAO.editAppointment(apptID, title, type, location, desc, start, end, customer, user.getUserID(), contact);


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

    public void getAppointment(Appointments selectedAppointment){

        apptIDTextField.setText(String.valueOf(selectedAppointment.getApptID()));
        titleTextField.setText(selectedAppointment.getApptTitle());
        typeTextField.setText(selectedAppointment.getApptType());
        locationTextField.setText(selectedAppointment.getApptLocation());
        descTextField.setText(selectedAppointment.getApptDesc());
        startDatePicker.setValue(selectedAppointment.getStart().toLocalDate());
        startTimeCombo.setValue(selectedAppointment.getStart().toLocalTime());
        endTimeCombo.setValue(selectedAppointment.getEnd().toLocalTime());
        for (int i = 0; i < custCombo.getItems().size(); i++) {
            Customers customer = custCombo.getItems().get(i);
            if (Objects.equals(customer.getCustName(), selectedAppointment.getCustomer())) {
                custCombo.setValue(customer);
                break;
            }
        }
        for (int i = 0; i < contactCombo.getItems().size(); i++) {
            Contacts contact = contactCombo.getItems().get(i);
            if (Objects.equals(contact.getContactName(), selectedAppointment.getContact())) {
                contactCombo.setValue(contact);
                break;
            }
        }
        for (int i = 0; i < userCombo.getItems().size(); i++) {
            User user = userCombo.getItems().get(i);
            if (Objects.equals(user.getUsername(), selectedAppointment.getUser())) {
                userCombo.setValue(user);
                break;
            }
        }
    }
}
