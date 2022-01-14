package Controller;

import DAO.divisionsDAO;
import Model.Divisions;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class editCustomer implements Initializable {
    public TextField custIDTextField;
    public TextField nameTextField;
    public TextField phoneTextField;
    public TextField addressTextField;
    public TextField postalTextField;
    public ComboBox<Divisions> divisionCombo;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        divisionCombo.setItems(divisionsDAO.getDivisions());

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
