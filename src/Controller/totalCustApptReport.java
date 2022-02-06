package Controller;

import DAO.appointmentsDAO;
import Model.Appointments;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controller for the report that displays the appointment type and the total number
 * of them for the selected month.
 */
public class totalCustApptReport implements Initializable {

    ObservableList<Appointments> appointmentsObservableList;
    public TableView<Appointments> totalApptTableView;
    public TableColumn<Appointments, String> typeColumn;
    public TableColumn<Appointments, Integer> totalColumn;
    public ComboBox<String> monthCombo;
    public int monthSelection;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        monthCombo.getItems().addAll("January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December");
    }

    public void monthOnAction(ActionEvent actionEvent) {

        monthSelection = monthCombo.getSelectionModel().getSelectedIndex() + 1;
        appointmentsObservableList = appointmentsDAO.totalCustAppointments(monthSelection);
        totalApptTableView.setItems(appointmentsObservableList);
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("apptType"));
        totalColumn.setCellValueFactory(new PropertyValueFactory<>("apptID"));
    }

    public void cancelOnAction(ActionEvent actionEvent) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("../view/mainForm.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1300, 530);
        stage.setTitle("Scheduling Application - Main");
        stage.setScene(scene);
        stage.show();
        stage.centerOnScreen();
        stage.setResizable(false);
    }
}
