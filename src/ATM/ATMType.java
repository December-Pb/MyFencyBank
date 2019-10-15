package ATM;

import User.Account;
import User.BankSystemUser;

import java.util.List;

public interface ATMType extends StateType {
    int withdrawMoney(Account account, int money);
    void saveMoney(Account account, int money);
    int lookThroughBalance(Account account);
    void printStatement();
    List<Account> lookThroughAccount();
    void createUser(BankSystemUser bankSystemUser);
    void deleteUser();
    void createAccount(String ID, String category, double deposit);
    boolean deleteAccount(Account account);
    void changeState(State state);
}
