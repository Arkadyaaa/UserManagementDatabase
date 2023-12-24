package update_panel;

import java.net.URL;
import java.util.ResourceBundle;

import accounts.AccountManager;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import main.AppController;
import main.AppPanels;
import accounts.Account;

public class UpdateController extends AppPanels implements Initializable{

    @FXML
    private Label titleLabel;

    @FXML
    private TextField username;

    @FXML
    private PasswordField password;

    @FXML
    private PasswordField newPassword;

    @FXML
    public ChoiceBox<String> accountType;

    @FXML
    private Button doneButton;

    @FXML
    private Button removeButton;

    @FXML
    private Label errorLabel;
    
    private AppController appController;
    private AccountManager currentAccounts;

    public void initializeData(AppController appController) {
        this.appController = appController;
        this.currentAccounts = appController.getCurrentAccounts();
    }
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //Add items to ChoiceBox
        accountType.getItems().add("Admin");
        accountType.getItems().add("User");
    }

    public UpdateController(AppController appController) {
        this.appController = appController;
    }

    @FXML
    private void doneButtonClick(MouseEvent event) {
        System.out.println("Login button clicked");

        String enteredUsername = username.getText();
        String enteredNewPassword = newPassword.getText();
        String enteredAccountType = accountType.getSelectionModel().getSelectedItem();

        Account selectedAccount = currentAccounts.selectAccountUsername(currentAccounts, enteredUsername);
        
        // Check if user has missing fields
        if(enteredUsername == "" || enteredAccountType == null){

            showError(errorLabel ,"Input all fields");

        } else if(selectedAccount == null){ // Check if no account is selected

            showError(errorLabel ,"Invalid username");

        }else{

            // Set password to the old password if user didn't input new password
            if(enteredNewPassword == ""){
                enteredNewPassword = selectedAccount.getPassword();
            }
            
            // Update Account
            selectedAccount.updateAccount(selectedAccount.getId(), selectedAccount.getUsername(), enteredNewPassword, enteredAccountType, selectedAccount.getStatus());

            // Save and Close
            saveAndClose(currentAccounts, titleLabel);
        }

    }

    @FXML
    private void removeButtonClick(MouseEvent event){

        // Select account
        String enteredUsername = username.getText();
        Account selectedAccount = currentAccounts.selectAccountUsername(currentAccounts, enteredUsername);
    
        if (selectedAccount != null) {
            // Account found, remove it
            currentAccounts.removeAccount(selectedAccount);
            appController.removeAccountToUserList(selectedAccount);
            refreshAndClose(currentAccounts, titleLabel);
        } else {
            // Account not found, display an error message or handle it appropriately
            showError(errorLabel, "Account not found");
        }
    }
}
