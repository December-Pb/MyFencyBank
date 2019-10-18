package Model;

/**
 * The loan has pawn name, interest and its currency, it also has a unique number
 */
public class Loan {
    private String pawnName;
    private double loanInterest;
    private Currency currency;
    private String loanNumber;
    public Loan(String pawnName, double loanInterest, Currency currency, String loanNumber) {
        this.currency = currency;
        this.pawnName = pawnName;
        this.loanInterest = loanInterest;
        this.loanNumber = loanNumber;
    }

    public String getLoanNumber() {
        return loanNumber;
    }

    public void setLoanNumber(String loanNumber) {
        this.loanNumber = loanNumber;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public String getPawnName() {
        return pawnName;
    }

    public void setPawnName(String pawnName) {
        this.pawnName = pawnName;
    }

    public double getLoanInterest() {
        return loanInterest;
    }

    public void setLoanInterest(double loanInterest) {
        this.loanInterest = loanInterest;
    }
}
