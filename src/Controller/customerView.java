package Controller;

import Model.Customers;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import DAO.customersDAO;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class customerView implements Initializable {
    //ObservableList<Customers> customersObservableList;
    public TableView<Customers> customerTableView;
    public TableColumn<Customers, Integer> custIDColumn;
    public TableColumn<Customers, String> custNameColumn;
    public TableColumn<Customers, String> custAddressColumn;
    public TableColumn<Customers, Integer> custDivisionColumn;
    public TableColumn<Customers, String> custPostalColumn;
    public TableColumn<Customers, String> custPhoneColumn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        customerTableView.setItems(customersDAO.getCustomers());

        custIDColumn.setCellValueFactory(new PropertyValueFactory<>("custID"));
        custNameColumn.setCellValueFactory(new PropertyValueFactory<>("custName"));
        custAddressColumn.setCellValueFactory(new PropertyValueFactory<>("custAddress"));
        custDivisionColumn.setCellValueFactory(new PropertyValueFactory<>("divID"));
        custPostalColumn.setCellValueFactory(new PropertyValueFactory<>("custPostalCode"));
        custPhoneColumn.setCellValueFactory(new PropertyValueFactory<>("custPhoneNum"));
    }

    public void addOnAction(ActionEvent actionEvent) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("../view/addCustomer.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 600, 400);
        stage.setTitle("Scheduling Application - Main");
        stage.setScene(scene);
        stage.show();
        stage.centerOnScreen();
        stage.setResizable(false);
    }

    public void editOnAction(ActionEvent actionEvent) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("../view/editCustomer.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 600, 400);
        stage.setTitle("Scheduling Application - Main");
        stage.setScene(scene);
        stage.show();
        stage.centerOnScreen();
        stage.setResizable(false);
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

    public void deleteOnAction(ActionEvent actionEvent) {
    }
}
