package Model;

import Utilities.JDBC;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * This class creates an app that displays messages.
 */
public class Main extends Application {

    public static Stage primaryStage;

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        Parent root = FXMLLoader.load(getClass().getResource("../view/loginForm.fxml"));
        primaryStage.setTitle("Scheduling Application");
        primaryStage.setScene(new Scene(root, 513, 323));
        primaryStage.show();
        Main.primaryStage.setResizable(false);
    }

    public static void main(String[] args) {

        JDBC.openConnection();

        launch(args);

        JDBC.closeConnection();
    }
}
