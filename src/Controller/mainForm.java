package Controller;

import DAO.JDBC;
import DAO.appointmentsDAO;
import Model.Appointments;
import Model.User;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.ZonedDateTime;
import java.util.Date;
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
    public TableColumn<Appointments, ZonedDateTime> startColumn;
    public TableColumn<Appointments, ZonedDateTime> endColumn;
    public TableColumn<Appointments, Integer> custIDColumn;
    public TableColumn<Appointments, Integer> userIDColumn;
    public Button viewCustTable;
    public RadioButton allRadioButton;
    public RadioButton weekRadioButton;
    public RadioButton monthRadioButton;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        //allRadioButton.setSelected(true);

        //try {
        apptTableView.setItems(appointmentsDAO.getAppointments());
        //}
        //catch (Exception e){
        //e.getMessage();
        //}

        apptIDColumn.setCellValueFactory(new PropertyValueFactory<>("apptID"));
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("apptTitle"));
        descColumn.setCellValueFactory(new PropertyValueFactory<>("apptDesc"));
        locationColumn.setCellValueFactory(new PropertyValueFactory<>("apptLocation"));
        contactColumn.setCellValueFactory(new PropertyValueFactory<>("contactID"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("apptType"));
        startColumn.setCellValueFactory(new PropertyValueFactory<>("start"));
        endColumn.setCellValueFactory(new PropertyValueFactory<>("end"));
        custIDColumn.setCellValueFactory(new PropertyValueFactory<>("custID"));
        userIDColumn.setCellValueFactory(new PropertyValueFactory<>("userID"));

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

    public void deleteApptOnAction(ActionEvent actionEvent) {
    }

    public void editApptOnAction(ActionEvent actionEvent) {
    }

    public void allFilterOnAction(ActionEvent actionEvent) throws Exception {
            appointmentsObservableList = appointmentsDAO.getAppointments();
            apptTableView.setItems(appointmentsObservableList);
    }

    public void weekFilterOnAction(ActionEvent actionEvent) throws Exception {
        appointmentsObservableList = appointmentsDAO.getAppointmentsByWeek();
        apptTableView.setItems(appointmentsObservableList);

    }

    public void monthFilterOnAction(ActionEvent actionEvent) throws Exception {
            appointmentsObservableList = appointmentsDAO.getAppointmentsByMonth();
            apptTableView.setItems(appointmentsObservableList);
    }

    public void exitOnAction(ActionEvent actionEvent) {
            System.exit(0);
    }
}
