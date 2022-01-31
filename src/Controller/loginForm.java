package Controller;

import Model.User;
import javafx.collections.ObservableList;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.ResourceBundle;
import java.sql.*;
import java.io.*;

import DAO.*;

/**
 * This class is for the login page of the scheduling application.
 * Login screen is the first screen shown at the start of application.
 */
public class loginForm implements Initializable {

    public Text loginTitleText;
    public TextField usernameTextField;
    public PasswordField passwordPasswordField;
    public Label userLocationLabel;
    public Button loginButton;
    public Label loginErrorLabel;

    /**
     * Method for login screen.
     * Based on users language settings, the login screen will translate all text
     * to either English or French.
     * Username and Password fields are prompt text. Added listener so that the
     * prompt text will still show in the fields when they are select, at least
     * until the user starts to type in them.
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        ResourceBundle englishBundle = ResourceBundle.getBundle("Utilities/lang", Locale.getDefault());
        ResourceBundle frenchBundle = ResourceBundle.getBundle("Utilities/lang", Locale.getDefault());

        if(Locale.getDefault().getLanguage().equals("en")){
            loginTitleText.setText(englishBundle.getString("loginTitle"));
            usernameTextField.setPromptText(englishBundle.getString("username"));
            passwordPasswordField.setPromptText(englishBundle.getString("password"));
            loginButton.setText(englishBundle.getString("loginButton"));
            loginErrorLabel.setText(englishBundle.getString("error"));
            userLocationLabel.setText(Locale.getDefault().getDisplayCountry());

            usernameTextField.focusedProperty().addListener((observable, oldValue, newValue) -> {
                if(newValue){
                    usernameTextField.setStyle("-fx-prompt-text-fill: derive(-fx-control-inner-background, -30%);");
                }
            });
            passwordPasswordField.focusedProperty().addListener((observable, oldValue, newValue) -> {
                if(newValue){
                    passwordPasswordField.setStyle("-fx-prompt-text-fill: derive(-fx-control-inner-background, -30%);");
                }
            });
        }
        else if(Locale.getDefault().getLanguage().equals("fr")){
            loginTitleText.setText(frenchBundle.getString("loginTitle"));
            usernameTextField.setPromptText(frenchBundle.getString("username"));
            passwordPasswordField.setPromptText(frenchBundle.getString("password"));
            loginButton.setText(frenchBundle.getString("loginButton"));
            loginErrorLabel.setText(frenchBundle.getString("error"));
            userLocationLabel.setText(frenchBundle.getString("country"));

            usernameTextField.focusedProperty().addListener((observable, oldValue, newValue) -> {
                if(newValue){
                    usernameTextField.setStyle("-fx-prompt-text-fill: derive(-fx-control-inner-background, -30%);");
                }
            });
            passwordPasswordField.focusedProperty().addListener((observable, oldValue, newValue) -> {
                if(newValue){
                    passwordPasswordField.setStyle("-fx-prompt-text-fill: derive(-fx-control-inner-background, -30%);");
                }
            });
        }
    }

    public void loginOnAction(ActionEvent actionEvent) throws IOException, SQLException {

       FileWriter fileWriter = new FileWriter("src/login_activity.txt", true);
        PrintWriter printWriter = new PrintWriter(fileWriter);
        ZonedDateTime zonedDateTime = ZonedDateTime.now();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy hh:mm:ss a z");
        JDBC.openConnection();
        String username = usernameTextField.getText();
        String password = passwordPasswordField.getText();
        String q = "SELECT * FROM users WHERE User_Name = '" + username + "'" + "AND Password = '" + password + "'";
        dbQuery.Query(q);
        ResultSet resultSet = dbQuery.getResultSet();

        if (resultSet != null && resultSet.next()) {

            printWriter.println("Login Successful" + "  " +
                    "Username: " + usernameTextField.getText() + "  " +
                    "Password: " + passwordPasswordField.getText() + "  " +
                    "Date/Time: " + zonedDateTime.format(dateTimeFormatter) + "   ");
            printWriter.close();

            Parent root = FXMLLoader.load(getClass().getResource("../view/mainForm.fxml"));
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root, 1300, 530);
            stage.setTitle("Scheduling Application - Main");
            stage.setScene(scene);
            stage.show();
            stage.centerOnScreen();
            stage.setResizable(false);
        }

        else {

            printWriter.println("Login Unsuccessful" + "    " +
                    "Username: " + usernameTextField.getText() + "  " +
                    "Password: " + passwordPasswordField.getText() + "  " +
                    "Date/Time: " + zonedDateTime.format(dateTimeFormatter) + "   ");
            printWriter.close();
            loginErrorLabel.setVisible(true);
        }
        JDBC.closeConnection();
    }
}

