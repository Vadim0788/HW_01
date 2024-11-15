package haspiev.dev.hw_01;

import jakarta.persistence.*;

@Entity
@Table(name = "accounts")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    private double moneyAmount;

    public Account(User user, double initialAmount) {
        this.user = user;
        this.moneyAmount = initialAmount;
    }

    public Account() {

    }

    public Long getUserId() {
        return user.getId();
    }

    public double getMoneyAmount() {
        return moneyAmount;
    }

    public void setMoneyAmount(double moneyAmount) {
        this.moneyAmount = moneyAmount;
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", userId=" + user.getId() +
                ", moneyAmount=" + moneyAmount +
                '}';
    }

    public Long getId() {
        return id;
    }
}
