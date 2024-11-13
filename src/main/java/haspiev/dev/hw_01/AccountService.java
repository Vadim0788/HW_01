package haspiev.dev.hw_01;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class AccountService {

    private final Map<Integer, Account> accountMap;

    private int count;

    private final double defaultAmount;

    private final double transferCommission;

    public AccountService(
            @Value("${account.default-amount}") double defaultAmount,
            @Value("${account.transfer-commission}") double transferCommission) {
        this.count = 0;
        this.accountMap = new HashMap<>();
        this.defaultAmount = defaultAmount;
        this.transferCommission = transferCommission;
    }

    public Account createAccount(User user) {
        Account account;
        double initialAmount = user.getAccountList().isEmpty() ? defaultAmount : 0;

        account = new Account(++count, user.getId(), initialAmount);
        accountMap.put(account.getId(), account);

        return account;
    }

    public void deposit(int accountId, double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Cannot deposit not positive amount: amount=%s"
                    .formatted(amount));
        }
        var account = findAccountById(accountId)
                .orElseThrow(() -> new IllegalArgumentException("No such account %s"
                        .formatted(accountId)));
        account.setMoneyAmount(account.getMoneyAmount() + amount);
    }

    public void withdraw(int accountId, double amount) {
        var account = findAccountById(accountId)
                .orElseThrow(() -> new IllegalArgumentException("No such account %s"
                        .formatted(accountId)));
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
        var source = findAccountById(sourceAccountId)
                .orElseThrow(() -> new IllegalArgumentException("No such account %s"
                        .formatted(sourceAccountId)));
        var target = findAccountById(targetAccountId)
                .orElseThrow(() -> new IllegalArgumentException("No such account %s"
                        .formatted(targetAccountId)));

        double commission = (source.getUserId() != target.getUserId()) ? amount * (transferCommission / 100) : 0;
        double totalAmount = amount + commission;

        if (source.getMoneyAmount() < totalAmount) {
            throw new IllegalArgumentException("Insufficient funds for transfer with commission.");
        }

        source.setMoneyAmount(source.getMoneyAmount() - totalAmount);
        target.setMoneyAmount(target.getMoneyAmount() + amount);
    }

    public void closeAccount(int accountId) {

        var accountToRemove = findAccountById(accountId)
                .orElseThrow(() -> new IllegalArgumentException("No such account: %s"
                        .formatted(accountId)));
        List<Account> accountList = getAllUserAccount(accountId);
        if (accountList.size() == 1) {
            throw new IllegalArgumentException("Cannot close the only account.");
        }

        Account accountToDeposit = accountList.stream()
                .filter(it -> it.getId() != accountId)
                .findFirst()
                .orElseThrow();
        accountToDeposit.setMoneyAmount(accountToDeposit.getMoneyAmount() + accountToRemove.getMoneyAmount());
        accountMap.remove(accountId);

    }

    private Optional<Account> findAccountById(int id) {
        return Optional.ofNullable(accountMap.get(id));
    }

    public List<Account> getAllUserAccount(int userId) {
        return accountMap.values()
                .stream()
                .filter(account -> account.getUserId() == userId)
                .toList();
    }
}
