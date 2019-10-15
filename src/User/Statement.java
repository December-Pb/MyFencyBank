package User;

public class Statement {
    private Account account;
    private double money;

    public Statement() {
        account = null;
        money = 0.0;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }
}
