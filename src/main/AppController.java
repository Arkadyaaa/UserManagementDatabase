package main;

import java.io.IOException;

import accounts.Account;
import accounts.AccountManager;
import add_panel.AddController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import update_panel.UpdateController;

public class AppController{

    @FXML
    private Button DEACTIVATE;

    @FXML
    private Button ADD;

    @FXML
    private Button EDIT;

    @FXML
    private Button CLOSE;
    
    @FXML
    private TableView<Account> tableView;

    @FXML
    private TableColumn<Account, Integer> idColumn;

    @FXML
    private TableColumn<Account, String> usernameColumn;

    @FXML
    private TableColumn<Account, String> passwordColumn;

    @FXML
    private TableColumn<Account, String> accountTypeColumn;

    @FXML
    private TableColumn<Account, String> statusColumn;

    private AccountManager currentAccounts;
    public AccountManager getCurrentAccounts(){
        return currentAccounts;
    }

    private static ObservableList<Account> userList;
    public static ObservableList<Account> getUserList() {
        return userList;
    }

    public AppController getAppController(){
        return this;
    }

    public Account getSelectedAccount() {
        return tableView.getSelectionModel().getSelectedItem();
    }

    @FXML
    private void initialize(){
        
        SaveManager.readFromDatabase(currentAccounts);
    
        // Bind the columns to the data properties
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        usernameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
        passwordColumn.setCellValueFactory(new PropertyValueFactory<>("password"));
        accountTypeColumn.setCellValueFactory(new PropertyValueFactory<>("accountType"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
    
        // Set the data to the table view
        tableView.setItems(userList);
    }

    @FXML
    private void handleButtonClick(MouseEvent event) {
        Button clickedButton = (Button) event.getSource();
        String buttonText = clickedButton.getText();

        switch(buttonText){
            case "ADD":
                openAddPanel();
                break;
            case "EDIT":
                openUpdatePanel();
                break;
            case "DEACTIVATE":
                Account selectedAccount = getSelectedAccount();
                deactivate(selectedAccount);

                break;
            case "CLOSE":
                System.exit(0);
                break;
            default:
                break;
        }
    }

    public AppController(){

        if (currentAccounts == null){
            currentAccounts = new AccountManager();
        }

        if (userList == null) {
            userList = FXCollections.observableArrayList();
        }

    }

    private void openAddPanel(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/add_panel/Add.fxml"));
            loader.setControllerFactory(param -> new AddController(this));
            Parent root = loader.load();

            AddController addController = loader.getController();
            addController.initializeData(this);

            Stage stage = new Stage();
    
            // Set the scene and show the stage
            stage.setTitle("Account");
            stage.setScene(new Scene(root));
            stage.show();
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void openUpdatePanel(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/update_panel/Update.fxml"));
            loader.setControllerFactory(param -> new UpdateController(this));
            Parent root = loader.load();
                    
            UpdateController updateController = loader.getController();
            updateController.initializeData(this);

            Stage stage = new Stage();
    
            // Set the scene and show the stage
            stage.setTitle("Account");
            stage.setScene(new Scene(root));
            stage.show();
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private void deactivate(Account selectedAccount){
        
        if (selectedAccount != null) {
            selectedAccount.setStatus("Inactive");
        
            SaveManager.saveToDatabase(currentAccounts);
            SaveManager.readFromDatabase(currentAccounts);
        }
    }

    public void addAccountToUserlist(Account account){

        userList.add(account);
        tableView.setItems(userList);
    }

    public void removeAccountToUserList(Account account){

        userList.remove(account);
        tableView.setItems(userList);
    }
}
