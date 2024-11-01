package haspiev.dev.hw_01;

import java.util.concurrent.atomic.AtomicInteger;

public class Account {
    private static final AtomicInteger count = new AtomicInteger(0);
    private final int id;
    private final int usreId;
    private double moneyAmount;

    public Account(int usreId, double initialAmount) {
        this.id = count.incrementAndGet();
        this.usreId = usreId;
        this.moneyAmount = initialAmount;
    }

    public int getId() {
        return id;
    }

    public int getUsreId() {
        return usreId;
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
                ", usreId=" + usreId +
                ", moneyAmount=" + moneyAmount +
                '}';
    }
}
