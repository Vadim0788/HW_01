package haspiev.dev.hw_01.user;

import haspiev.dev.hw_01.account.Account;

import java.util.List;

public class User {
    private final  int id;

    private final String login;

    private final List<Account> accountList;

    public User(int id, String login, List<Account> accountList) {
        this.id = id;
        this.login = login;
        this.accountList = accountList;
    }

    public List<Account> getAccountList() {
        return accountList;
    }

    public String getLogin() {
        return login;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", accountList=" + accountList +
                '}';
    }
}
