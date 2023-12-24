package accounts;
import java.util.ArrayList;

public class AccountManager {
    
    public ArrayList<Account> accounts = new ArrayList<>();
    private int currentAccountId = 1;

    public void addAccount(Account account) {
        accounts.add(account);
    }

    public void removeAccount(Account account) {
        accounts.remove(account);
        updateAccountIds();
    }

    //Used for selecting account using username ID
    public Account selectAccountUsername(AccountManager accountManager , String selectedUsername){

        for (Account account : accountManager.accounts) {
            if (account.getUsername().equals(selectedUsername)) {
                return account;
            }
        }

        // Returns if selected username doesn't match any account
        //System.out.println("Account with username " + selectedUsername + " not found.");

        return null;

    }

    public int getNextAccountId(){
        return currentAccountId++;
    }

    public void setNextAccountId(int currentAccountId){
        this.currentAccountId = currentAccountId;
    }

    // Update IDs. Used when an account is removed
    private void updateAccountIds() {
        for (int i = 0; i < accounts.size(); i++) {
            accounts.get(i).setId(i + 1);
            currentAccountId++;
        }
    }

    // Resets account id counter to 1. Used when reading save file
    public void resetAccountIds(){
        currentAccountId = 1;
    }
}