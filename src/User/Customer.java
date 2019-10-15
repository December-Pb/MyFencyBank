package User;

import java.util.ArrayList;
import java.util.List;

public class Customer extends BankSystemUser{
    private List<Account> accountList;
    private double loan;
    private List<Statement> statement;
    private String firstName;
    private String lastName;
    private String cardNumber;

    public Customer(String ID, String userName, String password, String firstName, String lastName, String cardNumber) {
        super(ID, userName, password);
        this.firstName = firstName;
        this.lastName = lastName;
        accountList = new ArrayList<Account>();
        loan = 0.0;
        statement = new ArrayList<Statement>();
        this.cardNumber = cardNumber;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        lastName = lastName;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public List<Account> getAccountList() {
        return accountList;
    }

    public void setAccountList(List<Account> accountList) {
        this.accountList = accountList;
    }

    public double getLoan() {
        return loan;
    }

    public void setLoan(double loan) {
        this.loan = loan;
    }

    public List<Statement> getStatement() {
        return statement;
    }

    public void setStatement(List<Statement> statement) {
        this.statement = statement;
    }
}
