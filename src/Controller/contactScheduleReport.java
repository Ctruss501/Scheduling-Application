package Controller;

import DAO.appointmentsDAO;
import DAO.contactsDAO;
import Model.Appointments;
import Model.Contacts;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;
import javafx.util.converter.LocalDateTimeStringConverter;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

/**
 * Controller for a report that displays the appointments based on a selected contact.
 * Contact Schedule.
 */
public class contactScheduleReport implements Initializable {

    ObservableList<Appointments> appointmentsObservableList;
    public TableView<Appointments> contactSchedTableView;
    public TableColumn<Appointments, Integer> apptIDColumn;
    public TableColumn<Appointments, String> titleColumn;
    public TableColumn<Appointments, String> typeColumn;
    public TableColumn<Appointments, String> descColumn;
    public TableColumn<Appointments, LocalDateTime> startColumn;
    public TableColumn<Appointments, LocalDateTime> endColumn;
    public TableColumn<Appointments, String> custColumn;
    public ComboBox<Contacts> contactCombo;
    public int contactSelection;

    /**
     * Populates the combo box with all contacts.
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        contactCombo.setItems(contactsDAO.getContacts());
    }

    /**
     * Handles the action for the contact combo box. When a contact is selected, the table will
     * populate with all the related appointment data for that contact. Formatted the start and end columns.
     * The start column displays date and time, end column displays just time. Both in 12-hour am and pm
     * instead of 24-hour clock.
     * @param actionEvent
     */
    public void contactOnAction(ActionEvent actionEvent) {

        DateTimeFormatter startDateTimeFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy      hh:mm a");
        DateTimeFormatter endDateTimeFormatter = DateTimeFormatter.ofPattern("hh:mm a");

        Contacts contact = contactCombo.getSelectionModel().getSelectedItem();
        contactSelection = contact.getContactID();
        appointmentsObservableList = appointmentsDAO.contactAppointments(contactSelection);
        contactSchedTableView.setItems(appointmentsObservableList);
        apptIDColumn.setCellValueFactory(new PropertyValueFactory<>("apptID"));
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("apptTitle"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("apptType"));
        descColumn.setCellValueFactory(new PropertyValueFactory<>("apptDesc"));
        startColumn.setCellValueFactory(new PropertyValueFactory<>("start"));
        startColumn.setCellFactory(TextFieldTableCell.forTableColumn(new LocalDateTimeStringConverter(startDateTimeFormatter, startDateTimeFormatter)));
        endColumn.setCellValueFactory(new PropertyValueFactory<>("end"));
        endColumn.setCellFactory(TextFieldTableCell.forTableColumn(new LocalDateTimeStringConverter(endDateTimeFormatter, endDateTimeFormatter)));
        custColumn.setCellValueFactory(new PropertyValueFactory<>("customer"));

        contactSchedTableView.getSortOrder().add(apptIDColumn);
        contactSchedTableView.sort();
    }

    /**
     * Handles cancel button. When pressed, sends back to the main form/appointment table.
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
