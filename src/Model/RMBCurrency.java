package Model;

public class RMBCurrency extends Currency {
    public RMBCurrency(String name, double deposit) {
        super(name, deposit);
        super.setTransferRate(1);
    }
}
