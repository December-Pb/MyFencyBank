package Model;

public class SavingAccount extends Account {
    private double interest;
    public SavingAccount(String accountNumber) {
        super(accountNumber);
        this.interest = 0;
    }

    public double getInterest() {
        return interest;
    }

    public void setInterest(double interest) {
        this.interest = interest;
    }
}
