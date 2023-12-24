package main;

import accounts.AccountManager;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

public class AppPanels {
    
    public void flashErrorLabel(Label errorLabel) {
        // Create a timeline for the flashing animation
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(0.5), new KeyValue(errorLabel.textFillProperty(), Color.rgb(255, 105, 97))),
                new KeyFrame(Duration.seconds(1.0), new KeyValue(errorLabel.textFillProperty(), Color.WHITE))
        );

        // Play the timeline
        timeline.play();
    }

    public void showError(Label errorLabel, String display){
            // Display error
            errorLabel.setText(display);

            // Flash the label in red
            flashErrorLabel(errorLabel);
    }

    public void saveAndClose(AccountManager currentAccounts, Label titleLabel){
        
        SaveManager.saveToDatabase(currentAccounts);
        SaveManager.readFromDatabase(currentAccounts);

        closeWindow(titleLabel);
    }

    public void refreshAndClose(AccountManager currentAccounts, Label titLabel){
        
        SaveManager.refreshDatabase(currentAccounts);
        closeWindow(titLabel);
    }


    public void closeWindow(Label titleLabel) {
        // Get a reference to the current stage and close it
        Stage stage = (Stage) titleLabel.getScene().getWindow();
        stage.close();
    }
    
}
