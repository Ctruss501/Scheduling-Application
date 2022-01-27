package Controller;

import Model.Customers;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import DAO.customersDAO;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

public class customerView implements Initializable {
    public TableView<Customers> customerTableView;
    public TableColumn<Customers, Integer> custIDColumn;
    public TableColumn<Customers, String> custNameColumn;
    public TableColumn<Customers, String> custAddressColumn;
    public TableColumn<Customers, Integer> custDivisionColumn;
    public TableColumn<Customers, String> custPostalColumn;
    public TableColumn<Customers, String> custPhoneColumn;
    public TableColumn<Customers, String> custCountryColumn;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        customerTableView.setItems(customersDAO.getCustomers());

        custIDColumn.setCellValueFactory(new PropertyValueFactory<>("custID"));
        custNameColumn.setCellValueFactory(new PropertyValueFactory<>("custName"));
        custAddressColumn.setCellValueFactory(new PropertyValueFactory<>("custAddress"));
        custDivisionColumn.setCellValueFactory(new PropertyValueFactory<>("divID"));
        custPostalColumn.setCellValueFactory(new PropertyValueFactory<>("custPostalCode"));
        custCountryColumn.setCellValueFactory(new PropertyValueFactory<>("countryID"));
        custPhoneColumn.setCellValueFactory(new PropertyValueFactory<>("custPhoneNum"));
    }

    public void addOnAction(ActionEvent actionEvent) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("../view/addCustomer.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 600, 400);
        stage.setTitle("Scheduling Application - Add Customer");
        stage.setScene(scene);
        stage.show();
        stage.centerOnScreen();
        stage.setResizable(false);
    }

    public void editOnAction(ActionEvent actionEvent) throws IOException {

        Customers selectedCustomer = customerTableView.getSelectionModel().getSelectedItem();
        if(customerTableView.getSelectionModel().getSelectedItem() == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("No Selection");
            alert.setContentText("Please select the customer you would like to edit.");
            alert.showAndWait();
            return;
        }
        if(selectedCustomer != null) {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("../view/editCustomer.fxml"));
            Parent scene = fxmlLoader.load();
            editCustomer editCustomerController = fxmlLoader.getController();
            editCustomerController.getCustomer(selectedCustomer);
            Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
            stage.setScene(new Scene(scene, 600, 400));
            stage.setTitle("Scheduling Application - Edit Customer");
            stage.show();
            stage.centerOnScreen();
            stage.setResizable(false);
        }
    }

    public void backOnAction(ActionEvent actionEvent) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("../view/mainForm.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1450, 600);
        stage.setTitle("Scheduling Application - Main");
        stage.setScene(scene);
        stage.show();
        stage.centerOnScreen();
        stage.setResizable(false);
    }

    public void deleteOnAction(ActionEvent actionEvent) throws SQLException {

        Customers selectedCustomer = customerTableView.getSelectionModel().getSelectedItem();

        if(selectedCustomer != null){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirm");
            alert.setContentText("Do you wish to delete this customer?");

            Optional<ButtonType> result = alert.showAndWait();

            if (result.get() == ButtonType.OK){
                customersDAO.deleteCustomer(selectedCustomer);

                customerTableView.setItems(customersDAO.getCustomers());
            }
        }
        else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("No Selection");
            alert.setContentText("Please select the customer you would like to delete.");
            alert.showAndWait();
        }
    }
}
