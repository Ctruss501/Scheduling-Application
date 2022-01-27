package Controller;

import DAO.JDBC;
import DAO.countryDAO;
import DAO.customersDAO;
import DAO.divisionsDAO;
import Model.Countries;
import Model.Divisions;
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
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class addCustomer implements Initializable {
    public TextField custIDTextField;
    public TextField nameTextField;
    public TextField phoneTextField;
    public TextField addressTextField;
    public TextField postalTextField;
    public ComboBox<Divisions> divisionCombo;
    public ComboBox<Countries> countryCombo;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        countryCombo.setItems((countryDAO.getCountries()));
        countryCombo.valueProperty().addListener((obs, oldValue, newValue)->{
            if(newValue == null){
                divisionCombo.getItems().clear();
                divisionCombo.setDisable(true);
            }
            else{
                ObservableList<Divisions> divisions = filterDivisions(newValue);
                divisionCombo.getItems().setAll(divisions);
                divisionCombo.setDisable(false);
            }
        });
    }

    public ObservableList<Divisions> filterDivisions(Countries newValue) {

        ObservableList<Divisions> divisions = divisionsDAO.getDivisions();
        return new FilteredList<>(divisions, i -> i.getCountryID() == countryCombo.getSelectionModel().getSelectedItem().getCountryID());
    }

    public void saveOnAction(ActionEvent actionEvent) throws IOException {

        String custName = nameTextField.getText();
        if(nameTextField.getText().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Name Empty");
            alert.setContentText("The customer must have a name.");
            alert.showAndWait();
            return;
        }
        String custPhone = phoneTextField.getText();
        if(phoneTextField.getText().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Phone Empty");
            alert.setContentText("The customer must have a phone number.");
            alert.showAndWait();
            return;
        }
        String custAddress = addressTextField.getText();
        if(addressTextField.getText().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Street Address Empty");
            alert.setContentText("The customer must have a street address.");
            alert.showAndWait();
            return;
        }
        String custPostal = postalTextField.getText();
        if(postalTextField.getText().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Postal Code Empty");
            alert.setContentText("The customer must have a postal code.");
            alert.showAndWait();
            return;
        }
        Countries country = countryCombo.getSelectionModel().getSelectedItem();
        if(countryCombo.getSelectionModel().getSelectedItem() == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Select a Country");
            alert.setContentText("The customer must have a country.");
            alert.showAndWait();
            return;
        }
        int division = divisionCombo.getValue().getDivID();
        if(divisionCombo.getSelectionModel().getSelectedItem() == null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Select a Division");
            alert.setContentText("The customer must have a division.");
            alert.showAndWait();
        }

        try{
            JDBC.openConnection();
            customersDAO.addCustomer(custName, custAddress, custPostal, custPhone, country.getCountryName(), division);
        }
        catch (SQLException e){
            e.printStackTrace();
        }

        Parent root = FXMLLoader.load(getClass().getResource("../view/customerView.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 752, 400);
        stage.setTitle("Scheduling Application - Customers");
        stage.setScene(scene);
        stage.show();
        stage.centerOnScreen();
        stage.setResizable(false);

    }

    public void cancelOnAction(ActionEvent actionEvent) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("../view/customerView.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 752, 400);
        stage.setTitle("Scheduling Application - Customers");
        stage.setScene(scene);
        stage.show();
        stage.centerOnScreen();
        stage.setResizable(false);
    }
}
