package haspiev.dev.hw_01;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private final String login;
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private final List<Account> accountList;


    public User(String login) {
        this.login = login;
        this.accountList = new ArrayList<>();
    }

    public User() {
        this.login = "default_login";
        this.accountList = new ArrayList<>();
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

    public Long getId() {
        return id;
    }
}
