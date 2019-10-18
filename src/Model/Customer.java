package Model;

import java.util.ArrayList;
import java.util.List;

public class Customer extends BankSystemUser {
    private List<Account> accountList;
    private List<Statement> statement;
    private String firstName;
    private String lastName;
    private String cardNumber;
    private List<Loan> loanItems;

    public Customer(String ID, String userName, String password, String firstName, String lastName, String cardNumber) {
        super(ID, userName, password);
        this.firstName = firstName;
        this.lastName = lastName;
        this.cardNumber = cardNumber;
        statement = new ArrayList<Statement>();
        loanItems = new ArrayList<>();
        accountList = new ArrayList<Account>();
        Account savingAccount = new SavingAccount("0000000000");
        Account checkingAccount = new CheckingAccount("1111111111");
        accountList.add(savingAccount);
        accountList.add(checkingAccount);
    }

    public List<Loan> getLoanItems() {
        return loanItems;
    }

    public void setLoanItems(List<Loan> loanItems) {
        this.loanItems = loanItems;
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

    public List<Statement> getStatement() {
        return statement;
    }

    public void setStatement(List<Statement> statement) {
        this.statement = statement;
    }
}
