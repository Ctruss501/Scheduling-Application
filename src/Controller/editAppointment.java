package Controller;

import DAO.contactsDAO;
import DAO.customersDAO;
import DAO.userDAO;
import Model.Contacts;
import Model.Customers;
import Model.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class editAppointment implements Initializable {
    public TextField apptIDTextField;
    public TextField titleTextField;
    public TextField typeTextField;
    public TextField locationTextField;
    public DatePicker startDatePicker;
    public ComboBox<String> startTimeCombo;
    public ComboBox<String> endTimeCombo;
    public ComboBox<Customers> custCombo;
    public ComboBox<Contacts> contactCombo;
    public ComboBox<User> userCombo;
    public TextField descTextField;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        userCombo.setItems(userDAO.getUsersFromUsername());
        custCombo.setItems(customersDAO.getCustomers());
        contactCombo.setItems(contactsDAO.getContacts());
    }

    public void saveOnAction(ActionEvent actionEvent) {
    }

    public void cancelOnAction(ActionEvent actionEvent) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("../view/mainForm.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1450, 600);
        stage.setTitle("Scheduling Application - Main");
        stage.setScene(scene);
        stage.show();
        stage.centerOnScreen();
        stage.setResizable(false);
    }
}
