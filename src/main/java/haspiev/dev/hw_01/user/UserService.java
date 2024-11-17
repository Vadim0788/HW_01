package haspiev.dev.hw_01.user;

import haspiev.dev.hw_01.account.AccountService;
import haspiev.dev.hw_01.hibernate.TransactionHelper;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.*;

@Service
public class UserService {

    private final TransactionHelper transactionHelper;

    private final AccountService accountService;

    private final SessionFactory sessionFactory;

    public UserService(
            TransactionHelper transactionHelper,
            AccountService accountService,
            SessionFactory sessionFactory
    ) {
        this.transactionHelper = transactionHelper;
        this.sessionFactory = sessionFactory;
        this.accountService = accountService;
    }

    public User createUser(String login) {
        return transactionHelper.executeInTransaction(() -> {
            var session = sessionFactory.getCurrentSession();
            var excistedUser = session.createQuery("FROM User WHERE login = :login", User.class)
                    .setParameter("login", login)
                    .getSingleResultOrNull();
            if (excistedUser != null) {
                throw new IllegalArgumentException("User already exists with login %s"
                        .formatted(login));
            }

            User user = new User(login, new ArrayList<>());
            session.persist(user);

            accountService.createAccount(user);
            return user;
        });
    }

    public Optional<User> findUserByID(Long id) {
        try (Session session = sessionFactory.openSession()) {
            var user = session.get(User.class, id);
            return Optional.of(user);
        }
    }

    public List<User> getAllUsers() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery(
                            "SELECT u FROM User u LEFT JOIN FETCH u.accountList",
                            User.class
                    )
                    .list();
        }

    }
}
