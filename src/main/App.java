//Eiji Arkady Achondo
//2Y BSCS

package main;

import java.io.InputStream;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class App extends Application {
    public static void main(String[] args) throws Exception {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        // Application icon
        InputStream stream = getClass().getResourceAsStream("/resources/icon.png");
        if (stream != null) {
            Image icon = new Image(stream);
            primaryStage.getIcons().add(icon);
        } else {
            System.out.println("Resource not found!");
        }

        FXMLLoader loader = new FXMLLoader(getClass().getResource("Main.fxml"));
        Parent root = loader.load();

        Scene scene = new Scene(root, 1000, 600);
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.setTitle("User Accounts");
        primaryStage.show();
    }
}
