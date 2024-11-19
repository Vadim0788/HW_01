package haspiev.dev.hw_01;

import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.resource.transaction.spi.TransactionStatus;
import org.springframework.stereotype.Component;

import java.util.function.Supplier;

@Component
public class TransactionHelper {
    private final SessionFactory sessionFactory;

    public TransactionHelper(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public <T> T executeInTransaction(Supplier<T> action) {

        var session = sessionFactory.getCurrentSession();
        Transaction transaction = session.getTransaction();
        if (transaction.getStatus().equals(TransactionStatus.ACTIVE)) {
            return action.get();
        }
        try {
            transaction = session.getTransaction();
            transaction.begin();

            var result = action.get();

            session.getTransaction().commit();
            return result;

        }  catch (Exception e) {
            transaction.rollback();
            throw new IllegalArgumentException(e);
        } finally {
            if (session.isOpen()) {
                session.close();
            }
        }
    }
}
