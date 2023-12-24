package add_panel;

import java.net.URL;
import java.util.ResourceBundle;

import accounts.Account;
import accounts.AccountManager;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import main.AppController;
import main.AppPanels;

public class AddController extends AppPanels implements Initializable{

    @FXML
    private Label titleLabel;

    @FXML
    private TextField username;

    @FXML
    private PasswordField password;

    @FXML
    private Button loginButton;

    @FXML
    private Label errorLabel;
    
    private AppController appController;
    private AccountManager currentAccounts;

    public void initializeData(AppController appController) {
        this.appController = appController;
        this.currentAccounts = appController.getCurrentAccounts();
    }

    public AddController(AppController appController) {
        this.appController = appController;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    @FXML
    private void handleButtonClick(MouseEvent event) {
        System.out.println("Login button clicked");

        // Get the input values from the text fields
        String enteredUsername = username.getText();
        String enteredPassword = password.getText();

        // Check if user has missing fields
        if(enteredUsername == "" || enteredPassword == ""){
            //display error
            errorLabel.setText("Input all fields");

            // Flash the label in red
            flashErrorLabel(errorLabel);
        } else{
            //Create the account
            int nextId = currentAccounts.getNextAccountId();
            Account account = new Account(nextId, enteredUsername, enteredPassword, "User", "Active");

            //Add account to lists
            currentAccounts.addAccount(account);
            appController.addAccountToUserlist(account);

            //Save and close
            saveAndClose(currentAccounts, titleLabel);
        }

    }

}
