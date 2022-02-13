package Model;

import DAO.JDBC;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Main class is for the start of the Scheduling Application.
 */
public class Main extends Application {

    public static Stage primaryStage;

    /**
     * Starts the application and opens the Login screen, loginForm.fxml
     * @param primaryStage
     * @throws Exception
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        Parent root = FXMLLoader.load(getClass().getResource("../view/loginForm.fxml"));
        primaryStage.setTitle("Scheduling Application");
        primaryStage.setScene(new Scene(root, 513, 323));
        primaryStage.show();
        Main.primaryStage.setResizable(false);

    }

    /**
     * Opens connection to database at start and closes database connection on closing application.
     * @param args
     */
    public static void main(String[] args) {

        JDBC.openConnection();

        //Used to test system default language set to French.
        //Locale.setDefault(new Locale("fr"));
        launch(args);

        JDBC.closeConnection();
    }
}
