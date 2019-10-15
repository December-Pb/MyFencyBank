package ATM;

import User.Account;
import User.AccountFactory;
import User.BankSystemUser;
import User.Customer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CustomerATM implements ATMType{

    private StateType currentType;
    private Map<String, BankSystemUser> bankSystemUsers;
    private BankSystemUser currentBankSystemUser;
    private double loanInterest;
    private double savingInterest;
    private double minSavingWithInterest;

    public CustomerATM() {
        currentType = StateFactory.getStateType(State.NOBANKCARDSTATE, this);
        bankSystemUsers = new HashMap<String, BankSystemUser>();
        loanInterest = 0.0;
        savingInterest = 0.0;
        minSavingWithInterest = 10000;
        Customer c1 = new Customer("U48621793", "jiaqsun@bu.edu", "123456", "Jiaqian", "Sun", "123123123123");
        Customer c2 = new Customer("U48621794", "jiaqsun@bu.edu", "123456", "Jiaqian", "Sun", "123123123123");
        Customer c3 = new Customer("U48621795", "jiaqsun@bu.edu", "123456", "Jiaqian", "Sun", "123123123123");
        Customer c4 = new Customer("U48621796", "jiaqsun@bu.edu", "123456", "Jiaqian", "Sun", "123123123123");
        currentBankSystemUser = null;
        bankSystemUsers.put(c1.getID(), c1);
        bankSystemUsers.put(c2.getID(), c2);
        bankSystemUsers.put(c3.getID(), c3);
        bankSystemUsers.put(c4.getID(), c4);
    }

    @Override
    public int withdrawMoney(Account account, int money) {
        return 0;
    }

    @Override
    public void saveMoney(Account account, int money) {

    }

    @Override
    public int lookThroughBalance(Account account) {
        return 0;
    }

    @Override
    public void printStatement() {

    }

    public void getLoan(double money) {
        if(currentBankSystemUser instanceof  Customer) {
            currentBankSystemUser = (Customer)currentBankSystemUser;
            ((Customer) currentBankSystemUser).setLoan(money);
        }
    }

    @Override
    public List<Account> lookThroughAccount() {
        if(currentBankSystemUser instanceof Customer)
            return ((Customer) currentBankSystemUser).getAccountList();
        return null;
    }

    @Override
    public void createUser(BankSystemUser bankSystemUser) {
        String ID = bankSystemUser.getID();
        bankSystemUsers.put(ID, bankSystemUser);
    }

    @Override
    public void deleteUser() {
        if(currentBankSystemUser instanceof Customer) {
            currentBankSystemUser = (Customer)bankSystemUsers;
            if(((Customer) currentBankSystemUser).getAccountList().size() == 0)
                bankSystemUsers.remove(currentBankSystemUser.getID());
            else
                System.out.println("Cannot delete account");
        }
    }

    @Override
    public void createAccount(String ID, String category, double deposit) {
        BankSystemUser bankSystemUser = bankSystemUsers.get(ID);
        if(bankSystemUser instanceof Customer) {
            ((Customer) bankSystemUser).getAccountList().add(AccountFactory.getAccount(category, deposit));
        }
    }

    @Override
    public boolean deleteAccount(Account account) {
        return false;
    }

    @Override
    public void changeState(State state) {
        currentType = StateFactory.getStateType(state, this);
    }

    @Override
    public State insertBankCard(String ID) {
        if(bankSystemUsers.containsKey(ID))
            currentBankSystemUser = bankSystemUsers.get(ID);
        currentType.insertBankCard(ID);
        return State.HASBANKCARDSTATE;
    }

    @Override
    public State backBankCard() {
        currentType.backBankCard();
        return State.NOBANKCARDSTATE;
    }

    @Override
    public State inputPassword() {
        currentType.inputPassword();
        return State.DECODINGSTATE;
    }

    @Override
    public State inputMoney(int money) {
        currentType.inputMoney(money);
        return State.DECODINGSTATE;
    }

    @Override
    public State takeOutMoney(int money) {
        return currentType.takeOutMoney(money);
    }

    @Override
    public State ensureTransaction() {
        currentType.ensureTransaction();
        return State.HASBANKCARDSTATE;
    }

    public double getLoanInterest() {
        return loanInterest;
    }

    public void setLoanInterest(double loanInterest) {
        this.loanInterest = loanInterest;
    }

    public double getSavingInterest() {
        return savingInterest;
    }

    public void setSavingInterest(double savingInterest) {
        this.savingInterest = savingInterest;
    }

    public double getMinSavingWithInterest() {
        return minSavingWithInterest;
    }

    public void setMinSavingWithInterest(double minSavingWithInterest) {
        this.minSavingWithInterest = minSavingWithInterest;
    }

    public StateType getCurrentType() {
        return currentType;
    }

    public void setCurrentType(StateType currentType) {
        this.currentType = currentType;
    }

    public Map<String, BankSystemUser> getBankSystemUsers() {
        return bankSystemUsers;
    }

    public void setBankSystemUsers(Map<String, BankSystemUser> bankSystemUsers) {
        this.bankSystemUsers = bankSystemUsers;
    }

    public BankSystemUser getCurrentBankSystemUser() {
        return currentBankSystemUser;
    }

    public void setCurrentBankSystemUser(BankSystemUser currentBankSystemUser) {
        this.currentBankSystemUser = currentBankSystemUser;
    }
}
