package haspiev.dev.hw_01;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountService {

    private final double defaultAmount;

    private final double transferCommission;

    private final TransactionHelper transactionHelper;

    public AccountService(
            @Value("${account.default-amount}") double defaultAmount,
            @Value("${account.transfer-commission}") double transferCommission,
            TransactionHelper transactionHelper) {
        this.transactionHelper = transactionHelper;
        this.defaultAmount = defaultAmount;
        this.transferCommission = transferCommission;
    }

    public Account createAccount(Long id) {
        return transactionHelper.executeInTransaction(session -> {
            User user = session.get(User.class, id);
            if (user == null) {
                throw new IllegalArgumentException("User with id %s does not exist.".formatted(id));
            }
            double initialAmount = user.getAccountList().isEmpty() ? defaultAmount : 0;
            Account account = new Account(user, initialAmount);
            session.persist(account);
            return account;
        });
    }

    public Account createAccountWithoutTransaction(User user) {
        double initialAmount = user.getAccountList().isEmpty() ? defaultAmount : 0;
        return new Account(user, initialAmount);
    }

    public void deposit(Long accountId, double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Cannot deposit not positive amount: amount=%s"
                    .formatted(amount));
        }
        transactionHelper.executeInTransaction(session -> {
            Account account = session.get(Account.class, accountId);
            if (account == null) {
                throw new IllegalArgumentException("No such account %s.".formatted(accountId));
            }
            account.setMoneyAmount(account.getMoneyAmount() + amount);

        });
    }

    public void withdraw(Long accountId, double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Cannot withdraw not positive amount: amount=%s"
                    .formatted(amount));
        }
        transactionHelper.executeInTransaction(session -> {
            Account account = session.get(Account.class, accountId);
            if (account == null) {
                throw new IllegalArgumentException("No such account %s.".formatted(accountId));
            }
            if (account.getMoneyAmount() < amount) {
                throw new IllegalArgumentException("Insufficient funds.");
            }
            account.setMoneyAmount(account.getMoneyAmount() - amount);
        });
    }

    public void transfer(Long sourceAccountId, Long targetAccountId, double amount) {

        if (amount <= 0) {
            throw new IllegalArgumentException("Cannot transfer not positive amount: amount=%s"
                    .formatted(amount));
        }
        transactionHelper.executeInTransaction(session -> {
            Account source = session.get(Account.class, sourceAccountId);
            Account target = session.get(Account.class, targetAccountId);
            if (source == null) {
                throw new IllegalArgumentException("No such account %s.".formatted(sourceAccountId));
            }
            if (target == null) {
                throw new IllegalArgumentException("No such account %s.".formatted(targetAccountId));
            }

            double commission = (!source.getUserId().equals(target.getUserId())) ? amount * (transferCommission / 100) : 0;
            double totalAmount = amount + commission;

            if (source.getMoneyAmount() < totalAmount) {
                throw new IllegalArgumentException("Insufficient funds for transfer with commission.");
            }

            session.merge(source);
            session.merge(target);

            source.setMoneyAmount(source.getMoneyAmount() - totalAmount);
            target.setMoneyAmount(target.getMoneyAmount() + amount);

        });
    }

    public void closeAccount(Long accountId) {
        transactionHelper.executeInTransaction(session -> {
            Account accountToRemove = session.get(Account.class, accountId);
            if (accountToRemove == null) {
                throw new IllegalArgumentException("No such account %s.".formatted(accountId));
            }
            List<Account> accountList = session
                    .createQuery("SELECT a FROM Account a WHERE a.user.id = :userId", Account.class)
                    .setParameter("userId", accountToRemove.getUserId())
                    .list();

            if (accountList.size() == 1) {
                throw new IllegalArgumentException("Cannot close the only account.");
            }

            Account accountToDeposit = accountList.stream()
                    .filter(it -> !it.getId().equals(accountId))
                    .findFirst()
                    .orElseThrow();
            accountToDeposit.setMoneyAmount(accountToDeposit.getMoneyAmount() + accountToRemove.getMoneyAmount());

            session.remove(accountToRemove);
        });
    }

}
