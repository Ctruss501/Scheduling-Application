package Controller;

import DAO.JDBC;
import DAO.appointmentsDAO;
import DAO.customersDAO;
import DAO.userDAO;
import Model.Appointments;
import Model.Customers;
import Model.User;
import javafx.animation.AnimationTimer;
import javafx.animation.PauseTransition;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.util.converter.LocalDateTimeStringConverter;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;


public class mainForm implements Initializable {

    public ToggleGroup appointmentFilterToggleGroup;
    ObservableList<Appointments> appointmentsObservableList;

    public TableView<Appointments> apptTableView;
    public TableColumn<Appointments, Integer> apptIDColumn;
    public TableColumn<Appointments, String> titleColumn;
    public TableColumn<Appointments, String> descColumn;
    public TableColumn<Appointments, String> locationColumn;
    public TableColumn<Appointments, String> contactColumn;
    public TableColumn<Appointments, String> typeColumn;
    public TableColumn<Appointments, LocalDateTime> startColumn;
    public TableColumn<Appointments, LocalDateTime> endColumn;
    public TableColumn<Appointments, String> custIDColumn;
    public TableColumn<Appointments, Integer> userIDColumn;
    public Button viewCustTable;
    public RadioButton allRadioButton;
    public RadioButton weekRadioButton;
    public RadioButton monthRadioButton;
    public ComboBox<String> reportsCombo;

    /**
     * The initialize method populates the appointment table. DateTimeFormatter was used to
     * make the start and end times more legible and to show the date only once (in the start column).
     * Columns are set sort by the appointment IDs.
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        DateTimeFormatter startDateTimeFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy         hh:mm a");
        DateTimeFormatter endDateTimeFormatter = DateTimeFormatter.ofPattern("hh:mm a");

        apptTableView.setItems(appointmentsDAO.getAppointments());

        apptIDColumn.setCellValueFactory(new PropertyValueFactory<>("apptID"));
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("apptTitle"));
        descColumn.setCellValueFactory(new PropertyValueFactory<>("apptDesc"));
        locationColumn.setCellValueFactory(new PropertyValueFactory<>("apptLocation"));
        contactColumn.setCellValueFactory(new PropertyValueFactory<>("contact"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("apptType"));
        startColumn.setCellValueFactory(new PropertyValueFactory<>("start"));
        startColumn.setCellFactory(TextFieldTableCell.forTableColumn(new LocalDateTimeStringConverter(startDateTimeFormatter, startDateTimeFormatter)));
        endColumn.setCellValueFactory(new PropertyValueFactory<>("end"));
        endColumn.setCellFactory(TextFieldTableCell.forTableColumn(new LocalDateTimeStringConverter(endDateTimeFormatter, endDateTimeFormatter)));
        custIDColumn.setCellValueFactory(new PropertyValueFactory<>("customer"));
        userIDColumn.setCellValueFactory(new PropertyValueFactory<>("user"));

        apptTableView.getSortOrder().add(apptIDColumn);
        apptTableView.sort();

        reportsCombo.getItems().addAll("Total Customer Appointments", "Contact Schedule");
    }


    public void viewCustTableOnAction(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../view/customerView.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 752, 400);
        stage.setTitle("Scheduling Application - Customers");
        stage.setScene(scene);
        stage.show();
        stage.centerOnScreen();
        stage.setResizable(false);
    }

    public void addApptOnAction(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../view/addAppointment.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 740, 500);
        stage.setTitle("Scheduling Application - Add Appointment");
        stage.setScene(scene);
        stage.show();
        stage.centerOnScreen();
        stage.setResizable(false);
    }

    public void editApptOnAction(ActionEvent actionEvent) throws IOException {

        Appointments selectedAppointment = apptTableView.getSelectionModel().getSelectedItem();
        if(apptTableView.getSelectionModel().getSelectedItem() == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("No Selection");
            alert.setContentText("Please select the appointment you would like to edit.");
            alert.showAndWait();
            return;
        }
        if(selectedAppointment != null) {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("../view/editAppointment.fxml"));
            Parent scene = fxmlLoader.load();
            editAppointment editAppointmentController = fxmlLoader.getController();
            editAppointmentController.getAppointment(selectedAppointment);
            Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
            stage.setScene(new Scene(scene, 740, 500));
            stage.setTitle("Scheduling Application - Edit Appointment");
            stage.show();
            stage.centerOnScreen();
            stage.setResizable(false);
        }
    }

    public void deleteApptOnAction(ActionEvent actionEvent) throws SQLException {

        Appointments selectedAppointment = apptTableView.getSelectionModel().getSelectedItem();

        if(selectedAppointment != null){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirm");
            alert.setContentText("Do you wish to delete this appointment?");

            Optional<ButtonType> result = alert.showAndWait();

            if (result.get() == ButtonType.OK){
                appointmentsDAO.deleteAppointment(selectedAppointment);

                Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
                alert1.setTitle("Appointment Canceled");
                alert1.setContentText("Appointment ID: " + selectedAppointment.getApptID() + " of Type: " + selectedAppointment.getApptType() + " has been canceled.");
                alert1.show();

                apptTableView.setItems(appointmentsDAO.getAppointments());
                apptTableView.getSortOrder().add(apptIDColumn);
                apptTableView.sort();
            }
        }
        else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("No Selection");
            alert.setContentText("Please select the appointment you would like to delete.");
            alert.showAndWait();
        }
    }

    public void allFilterOnAction(ActionEvent actionEvent){

        appointmentsObservableList = appointmentsDAO.getAppointments();

        apptTableView.setItems(appointmentsObservableList);
        apptTableView.getSortOrder().add(apptIDColumn);
        apptTableView.sort();
    }

    public void weekFilterOnAction(ActionEvent actionEvent) {

        appointmentsObservableList = appointmentsDAO.getAppointmentsByWeek();

        apptTableView.setItems(appointmentsObservableList);
        apptTableView.getSortOrder().add(apptIDColumn);
        apptTableView.sort();
    }

    public void monthFilterOnAction(ActionEvent actionEvent){

        appointmentsObservableList = appointmentsDAO.getAppointmentsByMonth();

        apptTableView.setItems(appointmentsObservableList);
        apptTableView.getSortOrder().add(apptIDColumn);
        apptTableView.sort();
    }

    public void reportsOnAction(ActionEvent actionEvent) throws IOException {

        reportsCombo.getSelectionModel().getSelectedItem();
        if(Objects.equals(reportsCombo.getSelectionModel().getSelectedItem(), "Total Customer Appointments")){

            Parent root = FXMLLoader.load(getClass().getResource("../view/totalCustApptReport.fxml"));
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root, 400, 329);
            stage.setTitle("Report - Total Customer Appointments");
            stage.setScene(scene);
            stage.show();
            stage.centerOnScreen();
            stage.setResizable(false);
        }
        if(Objects.equals(reportsCombo.getSelectionModel().getSelectedItem(), "Contact Schedule")){

            Parent root = FXMLLoader.load(getClass().getResource("../view/contactScheduleReport.fxml"));
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root, 790, 400);
            stage.setTitle("Report - Contact Schedule");
            stage.setScene(scene);
            stage.show();
            stage.centerOnScreen();
            stage.setResizable(false);
        }

    }

    public void exitOnAction(ActionEvent actionEvent) {
            System.exit(0);
            JDBC.closeConnection();
    }
}
