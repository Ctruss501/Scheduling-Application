package Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;



import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class loginForm implements Initializable {

    public TextField userIDTextField;
    public PasswordField passwordPasswordField;
    public Label userLocationLabel;
    public Label loginErrorLabel;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void loginOnAction(ActionEvent actionEvent) throws IOException {
        //Parent root = FXMLLoader.load(getClass().getResource("/view/mainScreen.fxml"));
        //Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        //Scene scene = new Scene(root, 850,355);
        //stage.setTitle("Inventory Management System");
        //stage.setScene(scene);
        //stage.show();
    }
}
