package Controller;

import DAO.appointmentsDAO;
import Model.Appointments;
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
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * This is the controller class for the customerView/Customer table.
 */
public class customerView implements Initializable {
    public TableView<Customers> customerTableView;
    public TableColumn<Customers, Integer> custIDColumn;
    public TableColumn<Customers, String> custNameColumn;
    public TableColumn<Customers, String> custAddressColumn;
    public TableColumn<Customers, Integer> custDivisionColumn;
    public TableColumn<Customers, String> custPostalColumn;
    public TableColumn<Customers, String> custPhoneColumn;
    public TableColumn<Customers, String> custCountryColumn;

    /**
     * Populating the customer table columns with their correlating data form the database.
     * Sorting the table by customer ID.
     * @param url
     * @param resourceBundle
     */
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

        customerTableView.getSortOrder().add(custIDColumn);
        customerTableView.sort();
    }

    /**
     * Handles add button. When pressed sends to the add customer screen.
     * @param actionEvent
     * @throws IOException
     */
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

    /**
     * Handles the edit button. When pressed, if no customer is selected on the table, an error message will
     * display to inform the user that a customer must first be selected. Calls getCustomer method from
     * the editCustomer controller to populate the field values from the selected customer.
     * @param actionEvent
     * @throws IOException
     */
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

    /**
     * Handles the back button. When pressed, will send back to the main form/appointment table.
     * @param actionEvent
     * @throws IOException
     */
    public void backOnAction(ActionEvent actionEvent) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("../view/mainForm.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1300, 530);
        stage.setTitle("Scheduling Application - Main");
        stage.setScene(scene);
        stage.show();
        stage.centerOnScreen();
        stage.setResizable(false);
    }

    /**
     * Handles the delete button. When pressed, will check the appointments in the database to
     * see if there are any appointments that have a match with the selected customer. If there is a
     * match, an error will display to let the user know that the appointments for that customer must
     * be deleted first. Another error is set to display is there is no customer selected when delete button
     * is pressed. If a customer is successfully deleted, an information alert will display with the
     * customer ID and Name, letting the user know of the successful deletion. Once this occurs, the customer
     * table is set with the current list of customer data from the database and the customer table is
     * sorted by customer ID.
     * @param actionEvent
     * @throws SQLException
     */
    public void deleteOnAction(ActionEvent actionEvent) throws SQLException {

        Customers selectedCustomer = customerTableView.getSelectionModel().getSelectedItem();

        if(selectedCustomer != null){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirm");
            alert.setContentText("Do you wish to delete this customer?");

            Optional<ButtonType> result = alert.showAndWait();

            if (result.get() == ButtonType.OK){

                //If the customer that is being deleted has existing appointments,
                //the appointments for that customer must be deleted first.
                for(int i = 0; i < appointmentsDAO.getAppointments().size(); i++) {
                    Appointments appointments = appointmentsDAO.getAppointments().get(i);
                    if(Objects.equals(appointments.getCustomer(), selectedCustomer.getCustName())) {
                        Alert alert1 = new Alert(Alert.AlertType.ERROR);
                        alert1.setTitle("Customer Appointments");
                        alert1.setContentText("This customer currently has existing appointments. Their appointments must be canceled before this customer can be deleted.");
                        alert1.showAndWait();
                        return;
                    }
                }
                customersDAO.deleteCustomer(selectedCustomer);

                Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
                alert2.setTitle("Customer Deleted");
                alert2.setContentText("Customer ID: " + selectedCustomer.getCustID() + ", Name: " + selectedCustomer.getCustName() + " has been deleted.");
                alert2.show();

                customerTableView.setItems(customersDAO.getCustomers());
                customerTableView.getSortOrder().add(custIDColumn);
                customerTableView.sort();
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
