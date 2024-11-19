package haspiev.dev.hw_01;

import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserService {

    private final TransactionHelper transactionHelper;

    private final AccountService accountService;

    private final SessionFactory sessionFactory;

    public UserService(TransactionHelper transactionHelper, AccountService accountService, SessionFactory sessionFactory) {
        this.transactionHelper = transactionHelper;
        this.accountService = accountService;
        this.sessionFactory = sessionFactory;
    }

    public User createUser(String login) {
        return transactionHelper.executeInTransaction(() -> {
            var session = sessionFactory.getCurrentSession();
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
        return transactionHelper.executeInTransaction(() -> {
            var session = sessionFactory.getCurrentSession();
            return session
                    .createQuery("SELECT u FROM User u LEFT JOIN FETCH u.accountList", User.class)
                    .list();
        });

    }
}
