package Model;

/**
 * The currency includes amount of money(deposit), the name of currency, depositInterest and transfer rate(Used to transfer
 * money between different kinds of currency)
 */
public abstract class Currency {
    private double deposit;
    private String currencyName;
    private double depositInterest;
    private double transferRate = 1;
    public Currency(String currencyName, double deposit) {
        this.currencyName = currencyName;
        this.deposit = deposit;
        depositInterest = 0.1;
    }

    public double transferIn(double money) {
        return money / transferRate;
    }

    public double transferOut(double money) {
        return money * transferRate;
    }

    public double getDepositInterest() {
        return depositInterest;
    }

    public void setDepositInterest(double depositInterest) {
        this.depositInterest = depositInterest;
    }

    public double getDeposit() {
        return deposit;
    }

    public void setDeposit(double deposit) {
        this.deposit = deposit;
    }

    public String getCurrencyName() {
        return currencyName;
    }

    public void setCurrencyName(String currencyName) {
        this.currencyName = currencyName;
    }

    public double getTransferRate() {
        return transferRate;
    }

    public void setTransferRate(double transferRate) {
        this.transferRate = transferRate;
    }
}
