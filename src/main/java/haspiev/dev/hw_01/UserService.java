package haspiev.dev.hw_01;

import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserService {

    private final TransactionHelper transactionHelper;

    private final AccountService accountService;

    public UserService(TransactionHelper transactionHelper, AccountService accountService) {
        this.transactionHelper = transactionHelper;
        this.accountService = accountService;
    }

    public User createUser(String login) {
        return transactionHelper.executeInTransaction(session -> {
            User newUser = new User(login);

            if (session
                    .createQuery("SELECT u FROM User u WHERE u.login = :login", User.class)
                    .setParameter("login", login)
                    .uniqueResultOptional()
                    .isPresent()) {
                throw new IllegalArgumentException("User with login %s already exist."
                        .formatted(login));
            }

            var newAccount = accountService.createAccountWithoutTransaction(newUser);
            newUser.addAccount(newAccount);
            session.persist(newUser);
            return newUser;
        });
    }

    public List<User> getAllUsers() {
        return transactionHelper.executeInTransaction(session -> {
            return session
                    .createQuery("SELECT u FROM User u LEFT JOIN FETCH u.accountList", User.class)
                    .list();
        });

    }
}
