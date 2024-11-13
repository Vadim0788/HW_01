package haspiev.dev.hw_01;

public class Account {

    private final int id;
    private final int userId;
    private double moneyAmount;

    public Account(int id, int userId, double initialAmount) {
        this.id = id;
        this.userId = userId;
        this.moneyAmount = initialAmount;
    }

    public int getId() {
        return id;
    }

    public int getUserId() {
        return userId;
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
                ", userId=" + userId +
                ", moneyAmount=" + moneyAmount +
                '}';
    }
}
