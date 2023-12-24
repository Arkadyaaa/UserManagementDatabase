package accounts;

import javafx.beans.property.*;

public class Account {
    private final IntegerProperty id;
    private final StringProperty username;
    private final StringProperty password;
    private final StringProperty accountType;
    private final StringProperty status;

    public Account(int id, String username, String password, String accountType, String status) {
        this.id = new SimpleIntegerProperty(id);
        this.username = new SimpleStringProperty(username);
        this.password = new SimpleStringProperty(password);
        this.accountType = new SimpleStringProperty(accountType);
        this.status = new SimpleStringProperty(status);
    }

    public void updateAccount(int id, String username, String password, String accountType, String status){
        this.id.set(id);
        this.username.set(username);
        this.password.set(password);
        this.accountType.set(accountType);
        this.status.set(status);
    }

    //Setter methods
    public void setId(int id) {
        this.id.set(id);
    }

    public void setUsername(String username) {
        this.username.set(username);
    }

    public void setPassword(String password) {
        this.password.set(password);
    }

    public void setAccountType(String accountType) {
        this.accountType.set(accountType);
    }

    public void setStatus(String status) {
        this.status.set(status);
    }

    // Getter methods
    public int getId() {
        return id.get();
    }

    public String getUsername() {
        return username.get();
    }

    public String getPassword() {
        return password.get();
    }

    public String getAccountType() {
        return accountType.get();
    }

    public String getStatus() {
        return status.get();
    }

    // Property getter methods
    public IntegerProperty idProperty() {
        return id;
    }

    public StringProperty usernameProperty() {
        return username;
    }

    public StringProperty passwordProperty() {
        return password;
    }

    public StringProperty accountTypeProperty() {
        return accountType;
    }

    public StringProperty statusProperty() {
        return status;
    }
}
