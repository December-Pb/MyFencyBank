package ATM;

public abstract class BaseState implements StateType{

    ATMType atmType;

    public BaseState(ATMType atmType) {
        this.atmType = atmType;
    }

    public ATMType getAtmType() {
        return atmType;
    }

    public void setAtmType(ATMType atmType) {
        this.atmType = atmType;
    }
}
