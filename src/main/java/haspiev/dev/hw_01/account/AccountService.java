package haspiev.dev.hw_01.account;

import haspiev.dev.hw_01.user.User;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class AccountService {

    private final Map<Integer, Account> accountMap;

    private int idCounter;

    private final AccountProperties accountProperties;

    public AccountService(AccountProperties accountProperties) {
        this.accountProperties = accountProperties;
        this.accountMap = new HashMap<>();
        this.idCounter = 0;
    }

    public Account createAccount(User user) {
        idCounter++;
        Account account = new Account(idCounter, user.getId(), accountProperties.getDefaultAccountAmount());
        accountMap.put(account.getId(), account);
        return account;
    }

    public Optional<Account> findAccountById(int id) {
        return Optional.ofNullable(accountMap.get(id));
    }

    public List<Account> getAllUserAccounts(int userId) {
        return accountMap.values()
                .stream()
                .filter(account -> account.getUserId() == userId)
                .toList();
    }

    public void depositAccount(int accountId, int moneyToDeposit) {
        var account = findAccountById(accountId)
                .orElseThrow(() -> new IllegalArgumentException("No such account: %s"
                        .formatted(accountId)));
        if (moneyToDeposit <= 0) {
            throw new IllegalArgumentException("Cannot deposit not positive amount: amount=%s"
                    .formatted(moneyToDeposit));
        }
        account.setMoneyAmount(account.getMoneyAmount() + moneyToDeposit);

    }

    public void withdrawFromAccount(int accountId, int amountToWithdraw) {
        var account = findAccountById(accountId)
                .orElseThrow(() -> new IllegalArgumentException("No such account: %s"
                        .formatted(accountId)));
        if (amountToWithdraw <= 0) {
            throw new IllegalArgumentException("Cannot withdraw not positive amount: amount=%s"
                    .formatted(amountToWithdraw));
        }
        if (account.getMoneyAmount() < amountToWithdraw) {
            throw new IllegalArgumentException(
                    "Cannot withdraw from account: id=%s, moneyAmount=%s, attemptedWithdraw=%s"
                            .formatted(accountId, account.getMoneyAmount(), amountToWithdraw)

            );
        }
        account.setMoneyAmount(account.getMoneyAmount() - amountToWithdraw);
    }

    public Account closeAccount(int accountId) {
        var accountToRemove = findAccountById(accountId)
                .orElseThrow(() -> new IllegalArgumentException("No such account: %s"
                        .formatted(accountId)));
        List<Account> accountList = getAllUserAccounts(accountToRemove.getUserId());
        if (accountList.size() == 1) {
            throw new IllegalArgumentException("Cannot close the only one account");
        }
        Account accountToDeposit = accountList.stream()
                .filter(it -> it.getId() != accountId)
                .findFirst()
                .orElseThrow();
        accountToDeposit.setMoneyAmount(accountToDeposit.getMoneyAmount() + accountToRemove.getMoneyAmount());
        accountMap.remove(accountId);
        return accountToRemove;
    }

    public void transfer(int fromAccountId, int toAccountId, int amountToTransfer) {
        var accountFrom = findAccountById(fromAccountId)
                .orElseThrow(() -> new IllegalArgumentException("No such account: %s"
                        .formatted(fromAccountId)));
        var accountTo = findAccountById(toAccountId)
                .orElseThrow(() -> new IllegalArgumentException("No such account: %s"
                        .formatted(toAccountId)));
        if (amountToTransfer <= 0) {
            throw new IllegalArgumentException("Cannot transfer not positive amount: amount=%s"
                    .formatted(amountToTransfer));
        }
        if (accountFrom.getMoneyAmount() < amountToTransfer) {
            throw new IllegalArgumentException(
                    "Cannot withdraw from account: id=%s, moneyAmount=%s, attemptedTransfer=%s"
                            .formatted(accountFrom.getId(), accountFrom.getMoneyAmount(), amountToTransfer)

            );
        }
        int totalAmountToDeposit = accountTo.getUserId() != accountFrom.getUserId()
                ? (int) (amountToTransfer * (1 - accountProperties.getTransferCommission()))
                : amountToTransfer;
        accountFrom.setMoneyAmount(accountFrom.getMoneyAmount() - amountToTransfer);
        accountTo.setMoneyAmount(accountTo.getMoneyAmount() + totalAmountToDeposit);


    }
}
