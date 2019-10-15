package User;

public abstract class Account {
    private double deposit;

    public Account(double deposit) {
        this.deposit = deposit;
    }

    public double getDeposit() {
        return deposit;
    }

    public void setDeposit(double deposit) {
        this.deposit = deposit;
    }
}
