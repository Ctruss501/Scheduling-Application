package Controller;

import DAO.JDBC;
import DAO.customersDAO;
import DAO.divisionsDAO;
import Model.Customers;
import Model.Divisions;
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        divisionCombo.setItems(divisionsDAO.getDivisions());
    }

    public void saveOnAction(ActionEvent actionEvent) throws SQLException {
        JDBC.openConnection();
        int id = 0;
        for(int i = 0; i < customersDAO.getCustomers().size(); i++) {
            if (id <= customersDAO.getCustomers().get(i).getCustID())
                id = customersDAO.getCustomers().get(i).getCustID() + 1;
        }
        try {
            String name = nameTextField.getText();
            String phone = phoneTextField.getText();
            String address = addressTextField.getText();
            String postalCode = postalTextField.getText();
            ComboBox div = (ComboBox) divisionCombo.getCellFactory();

            if(name.isEmpty()){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Customer Name");
                alert.setContentText("The new customer needs a name.");
                alert.showAndWait();
                return;
            }
            JDBC.closeConnection();
            return customersDAO.addCustomer();
        }
        catch (Exception e){
            e.printStackTrace();
        }

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
