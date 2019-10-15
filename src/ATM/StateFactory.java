package ATM;

public class StateFactory {

    public static StateType getStateType(State state, ATMType atmType) {
        switch(state) {
            case DECODINGSTATE:
                return new DecodingState(atmType);
            case OUTMONEYSTATE:
                return new OutMoneyState(atmType);
            case NOBANKCARDSTATE:
                return new NoBankCardState(atmType);
            case INSUFFICIENTMONEYSTATE:
                return new InsufficientMoneyState(atmType);
            case HASBANKCARDSTATE:
                return new HasBankCardState(atmType);
            default: NOBANKCARDSTATE:
                return new NoBankCardState(atmType);
        }
    }

}
