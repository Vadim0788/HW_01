package haspiev.dev.hw_01;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class AccountService {

    private final UserService userService;

    private final double defaultAmount;

    private final double transferCommission;

    public AccountService(
            UserService userService,
            @Value("${account.default-amount}") double defaultAmount,
            @Value("${account.transfer-commission}") double transferCommission) {
        this.userService = userService;
        this.defaultAmount = defaultAmount;
        this.transferCommission = transferCommission;
    }


    public Account createAccount(int userId) {
        User user = userService.findUserById(userId);
        Account account;
        if (user.getAccountList().isEmpty()) {
            account = new Account(userId, defaultAmount);
        } else {
            account = new Account(userId, 0);
        }
        user.addAccount(account);
        return account;
    }

    public void deposit(int accountId, double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Cannot deposit not positive amount: amount=%s"
                    .formatted(amount));
        }
        Account account = findAccountById(accountId);
        account.setMoneyAmount(account.getMoneyAmount() + amount);
    }

    public void withdraw(int accountId, double amount) {
        Account account = findAccountById(accountId);
        if (amount <= 0) {
            throw new IllegalArgumentException("Cannot withdraw not positive amount: amount=%s"
                    .formatted(amount));
        }
        if (account.getMoneyAmount() < amount) {
            throw new IllegalArgumentException("Insufficient funds.");
        }
        account.setMoneyAmount(account.getMoneyAmount() - amount);
    }

    public void transfer(int sourceAccountId, int targetAccountId, double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Cannot transfer not positive amount: amount=%s"
                    .formatted(amount));
        }
        Account source = findAccountById(sourceAccountId);
        Account target = findAccountById(targetAccountId);

        double commission = (source.getUserId() != target.getUserId()) ? amount * (transferCommission / 100) : 0;
        double totalAmount = amount + commission;

        if (source.getMoneyAmount() < totalAmount) {
            throw new IllegalArgumentException("Insufficient funds for transfer with commission.");
        }

        source.setMoneyAmount(source.getMoneyAmount() - totalAmount);
        target.setMoneyAmount(target.getMoneyAmount() + amount);
    }

    public void closeAccount(int accountId) {
        Account account = findAccountById(accountId);
        User user = userService.findUserById(account.getUserId());

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

    private Account findAccountById(int accountId) {

        for (User user : userService.getAllUsers()) {
            for (Account account : user.getAccountList()) {
                if (account.getId() == accountId) {
                    return account;
                }
            }
        }

        throw new IllegalArgumentException("Account not found.");
    }
}
