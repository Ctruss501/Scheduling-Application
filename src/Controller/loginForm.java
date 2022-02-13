package Controller;

import Model.Appointments;
import Model.User;
import javafx.animation.PauseTransition;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Objects;
import java.util.ResourceBundle;
import java.sql.*;
import java.io.*;

import DAO.*;
import javafx.util.Duration;


/**
 * This controller class is for the login page of the scheduling application.
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
     * Based on user's language settings, the login screen will translate all text
     * to either English or French.
     *
     * Username and Password fields are prompt text.
     * For these prompt text fields, a Lambda expression is used to make sure the prompt text is
     * still displayed on the fields. This was done so that the user can see what is meant to be typed
     * into those fields even when the field is selected. The prompt text will show until the user
     * start typing.
     *
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
            userLocationLabel.setText(String.valueOf(ZoneId.systemDefault()));

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
            userLocationLabel.setText(String.valueOf(ZoneId.systemDefault()));

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

    /**
     * This method handles to log-in button for the user, along with logging the user's successful and unsuccessful
     * log in attempts to the login_activity.txt file. If the user's username and/or password are incorrect, the label will show, letting the
     * user know to try again.
     *
     * Also in this method, after the stage is set, is the function to display a pop-up message for the user if they
     * do or do not have an upcoming appointment within the next 15 minutes. This is set here so that this message will
     * only display once, on log-in, instead of everytime the user goes back to the main/appointment screen.
     *
     * A lambda expression is used here to set a delay on the pop-up message. Instead of the pop-up message displaying
     * right as the new scene is loading, there is a 1-second delay.
     *
     * @param actionEvent
     * @throws IOException
     * @throws SQLException
     */
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


            boolean found = false;
            for(int j = 0; j < appointmentsDAO.getAppointments().size(); j++) {
                Appointments appointments = appointmentsDAO.getAppointments().get(j);

                for(int i = 0; i < userDAO.getUsers().size(); i++) {
                    User user = userDAO.getUsers().get(i);

                    if(Objects.equals(user.getUsername(), appointments.getUser()) &&
                            (appointments.getStart().isBefore(LocalDateTime.now().plusMinutes(15)) && appointments.getStart().isAfter(LocalDateTime.now().plusSeconds(1)))) {

                            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                            alert.setTitle("Upcoming Appointments");
                            alert.setContentText("Welcome back " + user.getUsername().substring(0, 1).toUpperCase() + user.getUsername().substring(1).toLowerCase() +
                                    ". You have a " + appointments.getApptType() + " appointment within the next 15 minutes.");
                            PauseTransition delay = new PauseTransition(Duration.seconds(1));
                            delay.setOnFinished(e -> alert.show());
                            delay.play();
                            found = true;
                    }
                    break;
                }
            }
            if(!found) {
                Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
                alert1.setTitle("Upcoming Appointments");
                alert1.setContentText("Welcome back " + username.substring(0, 1).toUpperCase() + username.substring(1).toLowerCase() +
                        ". You do not currently have an appointment within the next 15 minutes.");
                PauseTransition delay = new PauseTransition(Duration.seconds(1));
                delay.setOnFinished(e -> alert1.show());
                delay.play();
            }
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

