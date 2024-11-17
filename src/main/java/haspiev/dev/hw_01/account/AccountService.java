package haspiev.dev.hw_01.account;

import haspiev.dev.hw_01.hibernate.TransactionHelper;
import haspiev.dev.hw_01.user.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class AccountService {

    private final AccountProperties accountProperties;

    private final TransactionHelper transactionHelper;

    private final SessionFactory sessionFactory;

    public AccountService(
            AccountProperties accountProperties,
            TransactionHelper transactionHelper,
            SessionFactory sessionFactory
    ) {
        this.accountProperties = accountProperties;
        this.transactionHelper = transactionHelper;
        this.sessionFactory = sessionFactory;
    }

    public Account createAccount(User user) {

        return transactionHelper.executeInTransaction(() -> {
            var session = sessionFactory.getCurrentSession();
            Account newAccount = new Account(user, accountProperties.getDefaultAccountAmount());
            session.persist(newAccount);
            return newAccount;
        });
    }

    private Optional<Account> findAccountById(Long id) {
        var account = sessionFactory.getCurrentSession()
                .get(Account.class, id);
        return Optional.of(account);
    }

    public void depositAccount(Long accountId, int moneyToDeposit) {
        transactionHelper.executeInTransaction(() -> {
            var account = findAccountById(accountId)
                .orElseThrow(() -> new IllegalArgumentException("No such account: %s"
                        .formatted(accountId)));
            if (moneyToDeposit <= 0) {
                throw new IllegalArgumentException("Cannot deposit not positive amount: amount=%s"
                        .formatted(moneyToDeposit));
            }

            account.setMoneyAmount(account.getMoneyAmount() + moneyToDeposit);
            return 0;
        });

    }

    public void withdrawFromAccount(Long accountId, int amountToWithdraw) {
        transactionHelper.executeInTransaction(() -> {
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
            return 0;
        });
    }

    public Account closeAccount(Long accountId) {
        return transactionHelper.executeInTransaction(() ->{

            var accountToRemove = findAccountById(accountId)
                    .orElseThrow(() -> new IllegalArgumentException("No such account: %s"
                            .formatted(accountId)));
            var accountList = accountToRemove.getUser().getAccountList();
            if (accountList.size() == 1) {
                throw new IllegalArgumentException("Cannot close the only one account");
            }
            Account accountToDeposit = accountList.stream()
                    .filter(it -> !Objects.equals(it.getId(), accountId))
                    .findFirst()
                    .orElseThrow();
            accountToDeposit.setMoneyAmount(accountToDeposit.getMoneyAmount() + accountToRemove.getMoneyAmount());
            sessionFactory.getCurrentSession().remove(accountToRemove);
            return accountToRemove;
        });
    }

    public void transfer(Long fromAccountId, Long toAccountId, int amountToTransfer) {
        if (amountToTransfer <= 0) {
            throw new IllegalArgumentException("Cannot transfer not positive amount: amount=%s"
                    .formatted(amountToTransfer));
        }
        transactionHelper.executeInTransaction(() -> {
            var accountFrom = findAccountById(fromAccountId)
                    .orElseThrow(() -> new IllegalArgumentException("No such account: %s"
                            .formatted(fromAccountId)));
            var accountTo = findAccountById(toAccountId)
                    .orElseThrow(() -> new IllegalArgumentException("No such account: %s"
                            .formatted(toAccountId)));

            if (accountFrom.getMoneyAmount() < amountToTransfer) {
                throw new IllegalArgumentException(
                        "Cannot withdraw from account: id=%s, moneyAmount=%s, attemptedTransfer=%s"
                                .formatted(accountFrom.getId(), accountFrom.getMoneyAmount(), amountToTransfer)

                );
            }
            int totalAmountToDeposit = !Objects.equals(accountTo.getUser().getId(), accountFrom.getUser().getId())
                    ? (int) (amountToTransfer * (1 - accountProperties.getTransferCommission()))
                    : amountToTransfer;
            accountFrom.setMoneyAmount(accountFrom.getMoneyAmount() - amountToTransfer);
            accountTo.setMoneyAmount(accountTo.getMoneyAmount() + totalAmountToDeposit);
            return 0;
        });
    }
}
