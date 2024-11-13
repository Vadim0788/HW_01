package haspiev.dev.hw_01;

import java.util.ArrayList;
import java.util.List;

public class User {

    private final int id;
    private final String login;
    private final List<Account> accountList;


    public User(int id, String login) {
        this.id = id;
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

    @Override
    public String toString() {
        return "User{" +
                "id:" + id +
                ",name'" + login + '\'' +
                ", accountList=" + accountList +
                '}';
    }
}
