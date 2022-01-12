package Controller;

import DAO.JDBC;
import DAO.userDAO;
import Model.Contacts;
import Model.Customers;
import Model.User;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class addAppointment implements Initializable {
    public TextField apptIDTextField;
    public TextField titleTextField;
    public TextField typeTextField;
    public TextField locationTextField;
    public DatePicker startDatePicker;
    public ComboBox<String> startTimeCombo;
    public ComboBox<String> endTimeCombo;
    public ComboBox<Contacts> contactCombo;
    public ComboBox<Customers> custCombo;
    public ComboBox<User> userCombo;
    public TextField descTextField;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        JDBC.openConnection();

        userCombo.setItems(userDAO.getUsersFromUsername());

    }

    public void saveOnAction(ActionEvent actionEvent) {
    }

    public void cancelOnAction(ActionEvent actionEvent) {
    }
}
