package haspiev.dev.hw_01;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class AccountService {
    @Autowired
    private UserService userService;


    @Autowired
    private double defaultAmount;

    @Autowired
    private double transferCommission;


    public Account createAccount(int userId){
        User user = userService.findUserById(userId);
        Account account = new Account(userId, defaultAmount);
        user.addAccount(account);
        return account;
    }

    public void deposit(int accounId, double amount){
        Account account = findAccountById(accounId);
        account.setMoneyAmount(account.getMoneyAmount() + amount);
    }

    public void withdraw(int accountId, double amount){
        Account account = findAccountById(accountId);
        if (account.getMoneyAmount() < amount) {
            throw new IllegalArgumentException("Insufficient funds.");
        }
        account.setMoneyAmount(account.getMoneyAmount() - amount);
    }

    public void transfer(int sourceAccountId, int targetAccountId, double amount){
        Account source = findAccountById(sourceAccountId);
        Account target = findAccountById(targetAccountId);

        double comission = (source.getUsreId() != target.getUsreId()) ? amount * (transferCommission / 100) : 0;
        double totalAmount = amount + comission;

        if (source.getMoneyAmount() < totalAmount) {
            throw new IllegalArgumentException("Insufficient funds for trasnfer with commission.");
        }

        source.setMoneyAmount(source.getMoneyAmount() - totalAmount);
        target.setMoneyAmount(target.getMoneyAmount() + amount);
    }

    public void closeAccount(int accountId){
        Account account = findAccountById(accountId);
        User user = userService.findUserById(account.getUsreId());

        if (user.getAccountList().size() <= 1) {
            throw new IllegalArgumentException("Cannot close the only account.");
        }

        Account firstAccount = user.getAccountList().get(0);
        Account secondAccount = user.getAccountList().get(1);
        if (firstAccount != account) {
            firstAccount.setMoneyAmount(firstAccount.getMoneyAmount() + account.getMoneyAmount());
        } else {
            secondAccount.setMoneyAmount(secondAccount.getMoneyAmount() + account.getMoneyAmount());
        }
        user.getAccountList().remove(account);
    }

    private Account findAccountById(int accounId) {
        for (User user : userService.getAllUsers()){
            for (Account account : user.getAccountList()) {
                if (account.getId() == accounId) {
                    return account;
                }
            }
        }
        throw new IllegalArgumentException("Account not found.");
    }
}
