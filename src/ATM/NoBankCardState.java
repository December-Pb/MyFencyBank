package ATM;

public class NoBankCardState extends BaseState {
    public NoBankCardState(ATMType atmType) {
        super(atmType);
    }


    @Override
    public State insertBankCard(String ID) {
        return null;
    }

    @Override
    public State backBankCard() {
        return null;
    }

    @Override
    public State inputPassword() {
        return null;
    }

    @Override
    public State inputMoney(int money) {
        return null;
    }

    @Override
    public State takeOutMoney(int money) {
        return null;
    }

    @Override
    public State ensureTransaction() {
        return null;
    }
}