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
public class Main {

    /**
     * This is the main method.
     * @param args
     */
    public static void main(String[] args) {

        JDBC.openConnection();

        JDBC.closeConnection();
    }
}
