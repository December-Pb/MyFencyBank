package ATM;

public interface StateType {
    State insertBankCard(String ID);
    State backBankCard();
    State inputPassword();
    State inputMoney(int money);
    State takeOutMoney(int money);
    State ensureTransaction();
}
