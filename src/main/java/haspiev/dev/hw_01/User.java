package haspiev.dev.hw_01;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;


public class User {
    private static final AtomicInteger count = new AtomicInteger(0);
    private final int id;
    private final String login;
    private final List<Account> accountList;


    public User( String login) {

        this.id = count.incrementAndGet();
        this.login = login;
        this.accountList = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public List<Account> getAccountList() {
        return accountList;
    }

    public void addAccount(Account account) {
        this.accountList.add(account);
    }

    public String getLogin() {
        return login;
    }


    @Override
    public String toString() {
        return "User{" +
                "id:" + id +
                ",name'" + login + '\'' +
                ", accountList=" + accountList +
                '}';
    }
}
