package User;

public class AccountFactory {
    public static Account getAccount(String catogary, double deposit) {
        if(catogary.equals("saving account"))
            return new SavingAccount(deposit);
        else
            return new CheckingAccount(deposit);
    }
}
