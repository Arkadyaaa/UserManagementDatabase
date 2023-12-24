package main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import accounts.Account;
import accounts.AccountManager;
import javafx.collections.ObservableList;

public class SaveManager {

    private static final String DATABASE_URL = "jdbc:mysql://localhost:3306/UMMDatabase";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "";

    public static void saveToDatabase(AccountManager currentAccounts) {
        try (Connection connection = DriverManager.getConnection(DATABASE_URL, USERNAME, PASSWORD)) {
            String insertQuery = "INSERT INTO accountdata (id, username, password, account_type, status) VALUES (?, ?, ?, ?, ?)" +
                    " ON DUPLICATE KEY UPDATE " +
                    "username = VALUES(username), password = VALUES(password), account_type = VALUES(account_type), status = VALUES(status)";

            try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
                ObservableList<Account> userList = AppController.getUserList();
                currentAccounts.resetAccountIds();

                for (Account account : userList) {
                    preparedStatement.setInt(1, currentAccounts.getNextAccountId());
                    preparedStatement.setString(2, account.getUsername());
                    preparedStatement.setString(3, account.getPassword());
                    preparedStatement.setString(4, account.getAccountType());
                    preparedStatement.setBoolean(5, account.getStatus().equalsIgnoreCase("active")); // Assuming "active" means true
                    preparedStatement.executeUpdate();
                }

                System.out.println("Data saved successfully.");
            }
        } catch (SQLException e) {
            System.err.println("Error saving data to database: " + e.getMessage());
        }
    }

    public static void refreshDatabase(AccountManager currentAccounts) {
        try (Connection connection = DriverManager.getConnection(DATABASE_URL, USERNAME, PASSWORD)) {
            String deleteQuery = "DELETE FROM accountdata";

            try (PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery)) {
                // Execute the DELETE statement to remove all entries from the table
                preparedStatement.executeUpdate();

                saveToDatabase(currentAccounts);
                readFromDatabase(currentAccounts);

                System.out.println("Database refreshed successfully.");
            }
        } catch (SQLException e) {
            System.err.println("Error refreshing database: " + e.getMessage());
        }
    }

    public static void readFromDatabase(AccountManager currentAccounts) {
        try (Connection connection = DriverManager.getConnection(DATABASE_URL, USERNAME, PASSWORD)) {
            String selectQuery = "SELECT id, username, password, account_type, status FROM accountdata";

            try (PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {
                ResultSet resultSet = preparedStatement.executeQuery();

                // Clear existing accounts in both accountManager and userList
                currentAccounts.accounts.clear();
                AppController.getUserList().clear();

                while (resultSet.next()) {
                    int accountId = resultSet.getInt("id");
                    String accountUsername = resultSet.getString("username");
                    String accountPassword = resultSet.getString("password");
                    String accountType = resultSet.getString("account_type");
                    boolean accountStatus = resultSet.getBoolean("status");

                    // Convert boolean status to a string ("active" or "inactive")
                    String statusString = accountStatus ? "active" : "inactive";

                    currentAccounts.setNextAccountId(accountId);

                    Account account = new Account(accountId, accountUsername, accountPassword, accountType, statusString);
                    currentAccounts.addAccount(account);
                    AppController.getUserList().add(account);
                }
                System.out.println("Data read successfully.");
            }
        } catch (SQLException e) {
            System.err.println("Error reading data from database: " + e.getMessage());
        }
    }
}
