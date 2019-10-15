package ATM;

public class DecodingState extends BaseState{

    public DecodingState(ATMType atmType) {
        super(atmType);
    }


    @Override
    public State insertBankCard(String ID) {
        return null;
    }

    @Override
    public State backBankCard() {
        System.out.println("success");
        atmType.changeState(State.NOBANKCARDSTATE);
        return State.NOBANKCARDSTATE;
    }

    @Override
    public State inputPassword() {
        return State.DECODINGSTATE;
    }

    @Override
    public State inputMoney(int money) {
        return State.DECODINGSTATE;
    }

    @Override
    public State takeOutMoney(int money) {
        return State.DECODINGSTATE;
    }

    @Override
    public State ensureTransaction() {
        return State.DECODINGSTATE;
    }
}
