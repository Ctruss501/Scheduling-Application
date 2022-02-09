package Controller;

import DAO.JDBC;
import DAO.countryDAO;
import DAO.customersDAO;
import DAO.divisionsDAO;
import Model.Countries;
import Model.Customers;
import Model.Divisions;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Objects;
import java.util.ResourceBundle;

/**
 * This is the controller for editing an existing customer.
 */
public class editCustomer implements Initializable {
    public TextField custIDTextField;
    public TextField nameTextField;
    public TextField phoneTextField;
    public TextField addressTextField;
    public TextField postalTextField;
    public ComboBox<Divisions> divisionCombo;
    public ComboBox<Countries> countryCombo;

    /**
     * Sets the values for the country and division combo boxes. The division combo
     * is dependent and filtered based on the country that is selected.
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        countryCombo.setItems(countryDAO.getCountries());
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

    /**
     * Created a filtered list for divisions. The divisions are based on which matching Country IDs.
     * Lambda expression is used here to get the Country IDs and check for match.
     * @param newValue
     * @return
     */
    public ObservableList<Divisions> filterDivisions(Countries newValue) {

        ObservableList<Divisions> divisions = divisionsDAO.getDivisions();
        return new FilteredList<>(divisions, i -> i.getCountryID() == countryCombo.getSelectionModel().getSelectedItem().getCountryID());
    }

    /**
     * Handles the save button. Gets the values from the fields to save to the database. Alerts are in
     * place to show error messages for any fields that do not have values.
     * @param actionEvent
     * @throws IOException
     */
    public void saveOnAction(ActionEvent actionEvent) throws IOException, SQLException {

        int custID = Integer.parseInt(custIDTextField.getText());

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
        if(countryCombo.getSelectionModel().getSelectedItem() == null){
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

        //try{
            //JDBC.openConnection();
            customersDAO.editCustomer(custID, custName, custAddress, custPostal, custPhone, country.getCountryName(), division);
        //}
        //catch (SQLException e){
            //e.printStackTrace();
        //}

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
     * Handles the cancel button. When pressed, will send back to the customer view/table.
     * @param actionEvent
     * @throws IOException
     */
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

    /**
     * Gets the selected appointment from the customerView and database. Sets the correlating
     * fields with the values from the selected customer.
     * @param selectedCustomer
     */
    public void getCustomer(Customers selectedCustomer) {

        custIDTextField.setText(String.valueOf(selectedCustomer.getCustID()));
        nameTextField.setText(selectedCustomer.getCustName());
        phoneTextField.setText(selectedCustomer.getCustPhoneNum());
        addressTextField.setText(selectedCustomer.getCustAddress());
        postalTextField.setText(selectedCustomer.getCustPostalCode());
        for (int i = 0; i < countryCombo.getItems().size(); i++) {
            Countries country = countryCombo.getItems().get(i);
            if (Objects.equals(country.getCountryName(), selectedCustomer.getCountryID())) {
                countryCombo.setValue(country);
                break;
            }
        }
        for (int i = 0; i < divisionCombo.getItems().size(); i++) {
            Divisions division = divisionCombo.getItems().get(i);
            if (Objects.equals(division.getDivDivision(), selectedCustomer.getDivID())) {
                divisionCombo.setValue(division);
                break;
            }
        }
    }
}


