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

/**
 * This is the controller class for the main form/appointment table.
 */
public class mainForm implements Initializable {

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
    public Button returnButton;

    public ToggleGroup appointmentFilterToggleGroup;
    public RadioButton allRadioButton;
    public RadioButton weekRadioButton;
    public RadioButton monthRadioButton;

    public ComboBox<String> reportsCombo;
    public Label pastApptReptLabel;

    /**
     * Populate the appointment table with appointment data from the database and populates the report
     * combo box. Formatted the start and end columns. The start column displays date and time, end column
     * displays just time. Both in 12-hour am and pm instead of 24-hour clock. Columns are set sort by
     * the appointment IDs.
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

        reportsCombo.getItems().addAll("Total Customer Appointments", "Contact Schedule", "Past Appointments");
    }

    /**
     * Handles the view customers button. When pressed, sends to the customer view/customer table.
     * @param actionEvent
     * @throws IOException
     */
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

    /**
     * Handles the add button. When pressed, sends to the add appointment screen.
     * @param actionEvent
     * @throws IOException
     */
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

    /**
     * Handles the edit button. When pressed, if no appointment is selected on the table, an error message will
     * display to inform the user that an appointment must first be selected. Calls getAppointment method from
     * the editAppointment controller to populate the field values from the selected appointment.
     * @param actionEvent
     * @throws IOException
     */
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

    /**
     * Handles the delete button. An error is set to display is there is no appointment selected when delete button
     * is pressed. If an appointment is successfully deleted, an information alert will display with the
     * appointment ID and Type, letting the user know of the successful cancellation. Once this occurs,
     * the appointment table is set with the current list of appointment data from the database and the
     * appointment table is sorted by appointment ID.
     * @param actionEvent
     * @throws SQLException
     */
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

    /**
     * Handles all radio button. When selected, displays all appointments in database.
     * @param actionEvent
     */
    public void allFilterOnAction(ActionEvent actionEvent){

        appointmentsObservableList = appointmentsDAO.getAppointments();

        apptTableView.setItems(appointmentsObservableList);
        apptTableView.getSortOrder().add(apptIDColumn);
        apptTableView.sort();
    }

    /**
     * Handles week radio button. When selected, filters the table with just appointments
     * for the current week.
     * @param actionEvent
     */
    public void weekFilterOnAction(ActionEvent actionEvent) {

        appointmentsObservableList = appointmentsDAO.getAppointmentsByWeek();

        apptTableView.setItems(appointmentsObservableList);
        apptTableView.getSortOrder().add(apptIDColumn);
        apptTableView.sort();
    }

    /**
     * Handles month radio button. When selected, filters the table with just appointments
     * for the current month.
     * @param actionEvent
     */
    public void monthFilterOnAction(ActionEvent actionEvent){

        appointmentsObservableList = appointmentsDAO.getAppointmentsByMonth();

        apptTableView.setItems(appointmentsObservableList);
        apptTableView.getSortOrder().add(apptIDColumn);
        apptTableView.sort();
    }

    /**
     * Handles the reports combo box. When "Total Customer Appointments" or "Contact Schedule" is selected,
     * will send to appropriate view. When "Past Appointments" (additional report) is selected, the appointment
     * table view will populate with appointments from the database where appointments are in the past. A message
     * is set to display, along with a return button, and any radio button that may have been selected at the time
     * will be de-selected. Appointments may be deleted or selected for edit from this view as well,
     * making it a very convenient method of deleting or updating outdated appointments.
     *
     * A Lambda expression is used here to set an onAction handler for the new return button that is displayed.
     * Once the return button is pressed, it fires the all radio button to reset the table back to its original
     * display. The report combo is also reset so that past appointments may be selected again without needing
     * to leave this view. Also makes the return button not visible once again.
     * @param actionEvent
     * @throws IOException
     */
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
        if(Objects.equals(reportsCombo.getSelectionModel().getSelectedItem(), "Past Appointments")){

            pastApptReptLabel.setText("These appointments are in the past and may now be deleted from the table.");

            appointmentsObservableList = appointmentsDAO.pastAppointments();

            apptTableView.setItems(appointmentsObservableList);
            apptTableView.getSortOrder().add(apptIDColumn);
            apptTableView.sort();

            returnButton.setVisible(true);
            allRadioButton.selectedProperty().set(false);
            weekRadioButton.selectedProperty().set(false);
            monthRadioButton.selectedProperty().set(false);
            returnButton.setOnAction(actionEvent1 -> {
                allRadioButton.fire();
                pastApptReptLabel.setText(null);
                returnButton.setVisible(false);
                reportsCombo.getSelectionModel().clearSelection();
            });
        }
    }

    /**
     * Handles the exit button. Will close the application.
     * @param actionEvent
     */
    public void exitOnAction(ActionEvent actionEvent) {
            System.exit(0);
            JDBC.closeConnection();
    }
}
